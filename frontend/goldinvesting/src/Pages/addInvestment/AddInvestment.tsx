// src/Pages/addInvestment/AddInvestment.tsx
import React, { useEffect } from 'react';
import { Button, Container, Grid, Paper, Stack } from '@mui/material';
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

const AddInvestment: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { data, accordionItems, status, error } = useSelector((state: RootState) => state.investments);

  useEffect(() => {
    dispatch(fetchInvestmentData());
    dispatch(fetchAccordionItemsData());
  }, [dispatch]);
  
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  if (status === 'loading') {
    return <div>Loading...</div>;
  }

  if (status === 'failed') {
    return <div>Error: {error}</div>;
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
                      data,
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
