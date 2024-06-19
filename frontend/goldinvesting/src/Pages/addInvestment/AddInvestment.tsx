import React, { useEffect } from 'react';
import { Button, Container, Grid, Paper, Stack, CircularProgress } from '@mui/material';
import { PieChart } from '@mui/x-charts/PieChart';
import CustomAccordion from '../../Components/AccordionComponent';
import { yellow } from '@mui/material/colors';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { fetchAccordionItemsData } from '../../features/Investments/InvestmentsSlice';
import CustomModal from '../../Components/CustomModal';

const theme = createTheme({
  palette: {
    primary: yellow,
  },
});

const AddInvestment: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { accordionItems, transformedData, status, error } = useSelector((state: RootState) => state.investments);
  const user = useSelector((state: RootState) => state.user.user.data);

  useEffect(() => {
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
                      data: transformedData,
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
