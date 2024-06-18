import React, { useEffect, useState } from 'react';
import {
  AppBar, Toolbar, Typography, Grid, Card, CardContent, CardMedia, Box, Container,
  Paper,
  CardActions,
  Button
} from '@mui/material';
import { styled } from '@mui/material/styles';
import Chart from '../../Components/Chart';
import NewsCarousel from '../../Components/NewsCarousel';
import { PieChart } from '@mui/x-charts/PieChart';
import { useDispatch, useSelector } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';

const Home = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { data, accordionItems, status, error } = useSelector((state: RootState) => state.investments);
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

  useEffect(() => {
    // Fetch data or perform any side effects here
  }, []); // Empty dependency array means this effect runs only once on component mount

  return (
    <Container sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column', alignItems: 'center', py: 8 }}>
      <Grid container spacing={4} sx={{ width: '100%' }}>
        <Grid item xs={12}>
          <Paper sx={{ width: '100%', padding: 2, backgroundColor: (theme) => theme.palette.mode === 'dark' ? '#1A2027' : '#fff' }}>
            <PieChart
              series={[
                {
                  data,
                  highlightScope: { faded: 'global', highlighted: 'item' },
                  faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' },
                },
              ]}
              height={400}
              margin={{ top: 100, bottom: 100, left: 100, right: 100 }} // Set margins as needed
              slotProps={{
                legend: {
                  direction: 'column', // Arranges the legend items in a column
                  position: { vertical: 'middle', horizontal: 'right' }, // Positions the legend to the right middle
                  padding: -10, // Adjust padding as needed
                },
              }}
            />
          </Paper>
        </Grid>
        <Grid item xs={12}>
          <Paper sx={{ padding: 2, backgroundColor: (theme) => theme.palette.mode === 'dark' ? '#1A2027' : '#fff' }}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <Grid container spacing={2}>
                  {Array.from({ length: 4 }).map((_, index) => (
                    <Grid item xs={12} sm={6} key={index}>
                      <Card >
                        <CardContent>
                          <Typography gutterBottom variant="h5" component="div">
                            Card {index + 1}
                          </Typography>
                          <Typography variant="body2" color="text.secondary">
                            This is a media card. You can use this section to describe the content.
                          </Typography>
                        </CardContent>
                        <CardActions>
                          <Button size="small">Share</Button>
                          <Button size="small">Learn More</Button>
                        </CardActions>
                      </Card>
                    </Grid>
                  ))}
                </Grid>
              </Grid>
              <Grid item xs={12} sm={6}>
                <PieChart
                  series={[
                    {
                      data,
                      highlightScope: { faded: 'global', highlighted: 'item' },
                      faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' },
                    },
                  ]}
                  height={400}
                  margin={{ top: 100, bottom: 100, left: 100, right: 100 }} // Set margins as needed
                  slotProps={{
                    legend: {
                      direction: 'column', // Arranges the legend items in a column
                      position: { vertical: 'middle', horizontal: 'right' }, // Positions the legend to the right middle
                      padding: -10, // Adjust padding as needed
                    },
                  }}
                />
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
