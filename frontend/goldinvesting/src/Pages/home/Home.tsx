import React, { Component, useEffect, useState } from 'react';
import {
  AppBar, Toolbar, Typography, Grid, Card, CardContent, CardMedia, Box,Container,
  Paper,
  CardActions,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { LineChart} from '@mui/x-charts';
import Chart from '../../Components/Chart';
import NewsCarousel from '../../Components/NewsCarousel';
const Home = () => {

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

  const data = [
    { name: 'Jan', uv: 4000, pv: 2400, amt: 2400 },
    { name: 'Feb', uv: 3000, pv: 1398, amt: 2210 },
    { name: 'Mar', uv: 2000, pv: 9800, amt: 2290 },
    { name: 'Apr', uv: 2780, pv: 3908, amt: 2000 },
    { name: 'May', uv: 1890, pv: 4800, amt: 2181 },
    { name: 'Jun', uv: 2390, pv: 3800, amt: 2500 },
    { name: 'Jul', uv: 3490, pv: 4300, amt: 2100 },
  ];

  useEffect(() => {
    // Fetch data or perform any side effects here
  }, []); // Empty dependency array means this effect runs only once on component mount

    return (
      <Container>
        <Grid sx={{ flexGrow: 1 }} container pt={8}>
          <Grid item xs={12}>
            <Grid container direction="column" justifyContent="center" alignItems="stretch"  spacing={8}>
                <Grid item>
                  <Paper sx={{height: 320,backgroundColor: (theme) =>theme.palette.mode === 'dark' ? '#1A2027' : '#fff', }}>.
                  </Paper>
                </Grid>
                <Grid item >
                  <Paper sx={{height: 240,backgroundColor: (theme) =>theme.palette.mode === 'dark' ? '#1A2027' : '#fff', }}>.
                    <NewsCarousel/>
                  </Paper>
                </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Container>
    );
  }

export default Home;
