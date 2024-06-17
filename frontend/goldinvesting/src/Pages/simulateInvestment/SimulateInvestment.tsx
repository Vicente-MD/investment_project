import React, { useState } from 'react';
import {
  Container,
  TextField,
  Typography,
  Box,
  Grid,
  Paper,
  createTheme
} from '@mui/material';
import CustomColorButton from '../../Components/CustomColorButton';
import { PieChart } from '@mui/x-charts/PieChart';

// Investment calculation functions
function calculateAnnualRate(monthlyRate: number): number {
  return (Math.pow(1 + monthlyRate / 100, 12) - 1) * 100;
}

function calculateMonthlyRate(annualRate: number): number {
  return (Math.pow(1 + annualRate / 100, 1 / 12) - 1) * 100;
}

function calculateFutureValue(initialInvestment: number, monthlyRate: number, months: number): number {
  return initialInvestment * Math.pow(1 + monthlyRate / 100, months);
}

function calculateFutureValueWithContributions(monthlyContribution: number, monthlyRate: number, months: number): number {
  return monthlyContribution * (((Math.pow(1 + monthlyRate / 100, months) - 1) / (monthlyRate / 100)) * (1 + monthlyRate / 100));
}

const InvestmentCalculator: React.FC = () => {
  const [formValues, setFormValues] = useState({
    valorinvestido: '',
    taxainflacao: '',
    aporteperiodico: '',
    tempo: '',
    taxajuros: ''
  });

  const [result, setResult] = useState<{
    futureValue?: number;
    grossProfit?: number;
    totalInvested?: number;
  }>({});

  const [modalError, setModalError] = useState(false);
  const [modalSimulateInvestment, setModalSimulateInvestment] = useState(false);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormValues({
      ...formValues,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const initialInvestment = parseFloat(formValues.valorinvestido);
    const monthlyInflationRate = parseFloat(formValues.taxainflacao);
    const monthlyContribution = parseFloat(formValues.aporteperiodico);
    const investmentPeriod = parseFloat(formValues.tempo);
    let monthlyInterestRate = parseFloat(formValues.taxajuros);

    if (isNaN(initialInvestment) || isNaN(investmentPeriod) || isNaN(monthlyInterestRate)) {
      setModalError(true);
      return;
    }

    const annualInterestRate = calculateAnnualRate(monthlyInterestRate);
    let adjustedMonthlyInterestRate = monthlyInterestRate;

    if (!isNaN(monthlyInflationRate)) {
      const annualInflationRate = calculateAnnualRate(monthlyInflationRate);
      const realAnnualInterestRate = ((1 + annualInterestRate / 100) / (1 + annualInflationRate / 100) - 1) * 100;
      adjustedMonthlyInterestRate = calculateMonthlyRate(realAnnualInterestRate);
    }

    let futureValue: number;
    if (!isNaN(monthlyContribution)) {
      const futureValueOfContributions = calculateFutureValueWithContributions(monthlyContribution, adjustedMonthlyInterestRate, investmentPeriod);
      futureValue = futureValueOfContributions + initialInvestment;
      setResult({
        futureValue,
        totalInvested: initialInvestment + monthlyContribution * investmentPeriod,
        grossProfit: futureValue - (initialInvestment + monthlyContribution * investmentPeriod)
      });
    } else {
      futureValue = calculateFutureValue(initialInvestment, adjustedMonthlyInterestRate, investmentPeriod);
      setResult({
        futureValue,
        totalInvested: initialInvestment,
        grossProfit: futureValue - initialInvestment
      });
    }

    setModalSimulateInvestment(true);
  };

  const theme = createTheme();

  return (
    <Container maxWidth="sm" sx={{ marginTop: 8 }}>
      <Paper elevation={3} sx={{ padding: 4 }}>
        <Typography variant="h4" gutterBottom>Investment Calculator</Typography>
        <form onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                fullWidth
                name="valorinvestido"
                label="Initial Investment"
                value={formValues.valorinvestido}
                onChange={handleInputChange}
                required
                type="number"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                name="taxainflacao"
                label="Monthly Inflation Rate"
                value={formValues.taxainflacao}
                onChange={handleInputChange}
                type="number"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                name="aporteperiodico"
                label="Monthly Contribution"
                value={formValues.aporteperiodico}
                onChange={handleInputChange}
                type="number"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                name="tempo"
                label="Investment Period (months)"
                value={formValues.tempo}
                onChange={handleInputChange}
                required
                type="number"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                name="taxajuros"
                label="Monthly Interest Rate"
                value={formValues.taxajuros}
                onChange={handleInputChange}
                required
                type="number"
              />
            </Grid>
            <Grid item xs={12}>
              <CustomColorButton type="submit">Calculate</CustomColorButton>
            </Grid>
          </Grid>
        </form>
        {modalError && (
          <Typography variant="body1" color="error" sx={{ mt: 2 }}>
            Please fill in all required fields with valid numbers.
          </Typography>
        )}
        {modalSimulateInvestment && (
          <Box sx={{ mt: 4 }}>
            <Typography variant="h6">Results:</Typography>
            <Typography variant="body1">Future Value: {result.futureValue?.toFixed(2)}</Typography>
            <Typography variant="body1">Total Invested: {result.totalInvested?.toFixed(2)}</Typography>
            <Typography variant="body1">Gross Profit: {result.grossProfit?.toFixed(2)}</Typography>
            <PieChart
              series={[
                {
                  data: [
                    { id: 'Future Value', value: result.futureValue || 0, label: 'Future Value' },
                    { id: 'Total Invested', value: result.totalInvested || 0, label: 'Total Invested' },
                    { id: 'Gross Profit', value: result.grossProfit || 0, label: 'Gross Profit' }
                  ],
                  highlightScope: { faded: 'global', highlighted: 'item' },
                  faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' }
                }
              ]}
              height={200}
            />
          </Box>
        )}
      </Paper>
    </Container>
  );
};

export default InvestmentCalculator;
