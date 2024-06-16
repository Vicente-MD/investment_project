// src/Components/NewsCarousel.tsx
import React, { useEffect } from 'react';
import Carousel from 'react-material-ui-carousel';
import { Paper, Box, Typography, CircularProgress } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
import { RootState, AppDispatch } from '../store/store';
import { fetchNews } from '../features/news/NewsSlice';

const NewsCarousel: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { articles, loading, error } = useSelector((state: RootState) => state.news);

  useEffect(() => {
    dispatch(fetchNews());
  }, [dispatch]);

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Typography variant="h6" color="error" sx={{ textAlign: 'center', mt: 4 }}>
        {error}
      </Typography>
    );
  }


  const limitedArticles = articles.slice(0, 3);
  return (
    <Carousel>
      {limitedArticles.map((article, index) => (
        <Paper 
          key={index} 
          sx={{ 
            padding: 2, 
            textAlign: 'center',
            backgroundColor: 'black',
            color: 'white',
          }}
        >
          <a href={article.url} target="_blank" rel="noopener noreferrer" style={{ textDecoration: 'none', color: 'inherit' }}>
            <Box sx={{ width: '100%', height: 200, overflow: 'hidden', marginBottom: 2 }}>
              <img
                src={article.urlToImage}
                alt={article.title}
                style={{ width: '100%', height: '100%', objectFit: 'cover' }}
              />
            </Box>
            <Typography variant="h6" sx={{ fontWeight: 'bold' }}>
              {article.title}
            </Typography>
          </a>
        </Paper>
      ))}
    </Carousel>
  );
};

export default NewsCarousel;
