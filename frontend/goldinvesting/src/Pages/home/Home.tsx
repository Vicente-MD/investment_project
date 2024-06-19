import React, { useEffect, useState } from 'react';
import {
  AppBar, Toolbar, Typography, Grid, Card, CardContent, Paper,
  CardActions, Button, Container,
  CircularProgress
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, Legend } from 'recharts';
import { useDispatch, useSelector } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { fetchAccordionItemsData } from '../../features/Investments/InvestmentsSlice';
import NewsCarousel from '../../Components/NewsCarousel';
import { fetchAccordionItems } from '../../services/api';
import { toast } from 'react-toastify';

const Home = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { transformedData, status, error } = useSelector((state: RootState) => state.investments);
  const user = useSelector((state: RootState) => state.user.user.data);
  const [investmentData, setInvestmentData] = useState<any[]>([]);

  useEffect(() => {
    dispatch(fetchAccordionItemsData(user.id));
    fetchAccordionItems(user.id)
      .then((data) => {
        const lineData = groupByMonthYear(data);
        console.log(lineData);
        setInvestmentData(lineData);
      })
      .catch((error) => {
        toast.error('Erro ao buscar dados de investimentos');
      });
  }, [dispatch, user.id]);

  const groupByMonthYear = (data: any[]) => {
    return data.reduce((acc: any[], item: any) => {
      const date = `${item.month}/${item.year}`;
      const existingItem = acc.find((i: any) => i.date === date);
      if (existingItem) {
        existingItem.price += Math.round(item.price * 100) / 100;
      } else {
        acc.push({ date, price: Math.round(item.price * 100) / 100});
      }
      return acc;
    }, []);
  };

  const [news, setNews] = useState<JSX.Element[]>([
    <div>News 1</div>,
    <div>News 2</div>,
    <div>News 3</div>,
  ]);

  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  }));

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
    <Container sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column', alignItems: 'center', py: 8 }}>
      <Grid container spacing={4} sx={{ width: '100%' }}>
        <Grid item xs={12}>
          <Paper sx={{ width: '100%', padding: 2, backgroundColor: (theme) => theme.palette.mode === 'dark' ? '#1A2027' : '#fff' }}>
            <LineChart data={investmentData} width={800} height={400}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="date" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="price" name="Valor" stroke="#8884d8" />
            </LineChart>
          </Paper>
        </Grid>
        <Grid item xs={12}>
          <Paper sx={{ padding: 2, backgroundColor: (theme) => theme.palette.mode === 'dark' ? '#1A2027' : '#fff' }}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <Grid container spacing={2}>
                </Grid>
              </Grid>
            </Grid>
            <NewsCarousel />
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
}

export default Home;
