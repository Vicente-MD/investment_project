import React, { useEffect } from "react";
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { fetchNews } from '../../features/news/NewsSlice';
import { Container, Typography, Link, Box, Card, CardMedia, CardContent, CircularProgress } from "@mui/material";

function News() {
  const dispatch = useDispatch<AppDispatch>(); // Use typed dispatch
  const { articles, loading, error } = useSelector((state: RootState) => state.news);

  useEffect(() => {
    dispatch(fetchNews());
  }, [dispatch]);

  if (loading) {
    return (
      <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <CircularProgress />
      </Container>
    );
  }

  if (error) {
    return (
      <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography variant="h6" color="error">{error}</Typography>
      </Container>
    );
  }

  return (
    <>
      <Container sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography variant="h3" component="h3" sx={{ mt: '6vh', mb: '3vh', textAlign: 'center', fontWeight: 800 }}>News</Typography>
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%' }}>
          {articles.map((article) => (
            <Link href={article.url} underline="none" target="_blank" color="inherit" key={article.url} sx={{ mb: 2, width: '65%', textDecoration: 'none' }}>
              <Card sx={{ display: 'flex', alignItems: 'center', backgroundColor: 'rgba(234, 234, 234, 0.531)', border: '1px solid rgb(216, 214, 214)', transition: 'transform .2s', '&:hover': { backgroundColor: '#00000021', fontWeight: 600, transform: 'scale(1.1)' } }}>
                <CardMedia component="img" image={article.urlToImage} alt={article.title} sx={{ width: '20vw', height: '25vh' }} />
                <CardContent sx={{ display: 'flex', flexDirection: 'column', ml: 2 }}>
                  <Typography variant="h5" component="div" fontWeight="700">{article.title}</Typography>
                  <Typography variant="body1" sx={{ mt: 1 }}>{article.description}</Typography>
                </CardContent>
              </Card>
            </Link>
          ))}
        </Box>
      </Container>
    </>
  );
}

export default News;
