import React, { useEffect } from 'react';
import { Button, Container, Grid, Paper, Stack, CircularProgress } from '@mui/material';
import { PieChart } from '@mui/x-charts/PieChart';
import CustomAccordion from '../../Components/AccordionComponent';
import { yellow } from '@mui/material/colors';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { fetchInvestmentData, fetchAccordionItemsData } from '../../features/Investments/InvestmentsSlice';
import CustomModal from '../../Components/CustomModal';

const theme = createTheme({
  palette: {
    primary: yellow,
  },
});

interface AccordionItem {
  year: number;
  month: number;
  price: number;
  dividend: number;
  investmentType: string;
}

interface TransformedDataItem {
  id: number;
  value: number;
  label: string;
}

interface TransformedData {
  data: TransformedDataItem[];
}

const getLastValues = (items: AccordionItem[]): AccordionItem[] => {
  const lastValues: { [key: string]: AccordionItem } = {};

  items.forEach(item => {
    const key = item.investmentType;
    if (!lastValues[key] || 
        item.year > lastValues[key].year || 
        (item.year === lastValues[key].year && item.month > lastValues[key].month)) {
      lastValues[key] = item;
    }
  });

  return Object.values(lastValues);
};

const investmentLabels: { [key: string]: string } = {
  'FIXED_INCOME': 'Renda Fixa',
  'STOCK': 'Ações',
  'CURRENT_ACCOUNT': 'Conta Corrente'
};

const labelIds: { [key: string]: number } = {
  'Renda Fixa': 1,
  'Ações': 2,
  'Conta Corrente': 3
};

const transformData = (items: AccordionItem[]): TransformedData => {
  const lastValues = getLastValues(items);

  return {
    data: lastValues.map((item) => {
      const label = investmentLabels[item.investmentType] || item.investmentType;
      return {
        id: labelIds[label],
        value: item.price,
        label
      };
    })
  };
};

const AddInvestment: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { data, accordionItems, status, error } = useSelector((state: RootState) => state.investments);
  const user = useSelector((state: RootState) => state.user.user.data);

  useEffect(() => {
    dispatch(fetchInvestmentData());
    dispatch(fetchAccordionItemsData(user.id));
  }, [dispatch, user.id]);

  const [open, setOpen] = React.useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    dispatch(fetchAccordionItemsData(user.id)); // Recarregar accordionItems ao fechar o modal
  };

  // Ensure accordionItems have valid structure
  const validatedAccordionItems: AccordionItem[] = accordionItems.map((item) => ({
    year: typeof item.year === 'string' ? parseInt(item.year) : item.year,
    month: typeof item.month === 'string' ? parseInt(item.month) : item.month,
    price: item.price,
    dividend: item.dividend,
    investmentType: item.investmentType
  }));

  // Transform accordionItems before rendering
  const transformedData = transformData(validatedAccordionItems);

  if (status === 'loading') {
    return (
      <Container>
        <CircularProgress />
      </Container>
    );
  }

  if (status === 'failed') {
    return (
      <Container>
        <div>Error: {error}</div>
      </Container>
    );
  }

  return (
    <Container>
      <Grid sx={{ flexGrow: 1 }} container pt={8}>
        <Grid item xs={12}>
          <Grid container direction="column" justifyContent="center" alignItems="stretch" spacing={8}>
            <Grid item>
              <Paper sx={{ height: 320, backgroundColor: (theme) => theme.palette.mode === 'dark' ? '#1A2027' : '#fff' }}>
                <PieChart
                  series={[
                    {
                      data: transformedData.data,
                      highlightScope: { faded: 'global', highlighted: 'item' },
                      faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' },
                    },
                  ]}
                  height={200}
                />
              </Paper>
            </Grid>
            <Stack direction="row" justifyContent="right" spacing={8} sx={{ marginTop: 4 }}>
              <Button
                variant="contained"
                onClick={handleOpen}
                sx={{
                  color: theme.palette.getContrastText(yellow[500]),
                  backgroundColor: yellow[500],
                  '&:hover': {
                    backgroundColor: yellow[700],
                  },
                }}
              >
                Adicionar investimento
              </Button>
              <CustomModal open={open} onClose={handleClose} />
            </Stack>
            <Grid item>
              <Paper sx={{ minHeight: 240, backgroundColor: (theme) => theme.palette.mode === 'dark' ? '#1A2027' : '#fff' }}>
                <CustomAccordion items={accordionItems} />
              </Paper>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </Container>
  );
};

export default AddInvestment;
