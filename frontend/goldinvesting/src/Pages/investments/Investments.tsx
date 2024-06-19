import React, { useEffect, useState } from 'react';
import { fetchData, DataResponse, Quote, News } from '../../services/StockService';
import { Card, CardContent, Container, Grid, Typography } from '@mui/material';
const App: React.FC = () => {
  const [data, setData] = useState<DataResponse | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const getData = async () => {
      try {
        const fetchedData = await fetchData();
        setData(fetchedData);
      } catch (error) {
        setError('An error occurred while fetching the data');
        console.error(error);
      }
    };

    getData();
  }, []);

  return (
    <Container>
      <Typography variant="h2" gutterBottom>
        Mercado de ações
      </Typography>
      {error && <Typography color="error">{error}</Typography>}
      {data ? (
        <Grid container spacing={3}>
          {data.quotes.map((quote: Quote) => (
            <Grid item xs={12} sm={6} md={4} key={quote.symbol}>
              <Card>
                <CardContent>
                  <Typography variant="h5" component="div">
                    {quote.shortname}
                  </Typography>
                  <Typography color="textSecondary">
                    ({quote.symbol}) - {quote.exchange}
                  </Typography>
                  <Typography variant="body2">
                    {quote.longname}
                  </Typography>
                  {quote.sector && (
                    <Typography variant="body2" color="textSecondary">
                      Sector: {quote.sector}
                    </Typography>
                  )}
                  {quote.industry && (
                    <Typography variant="body2" color="textSecondary">
                      Industry: {quote.industry}
                    </Typography>
                  )}
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      ) : (
        <Typography>Loading...</Typography>
      )}
    </Container>
  );
};

export default App;
