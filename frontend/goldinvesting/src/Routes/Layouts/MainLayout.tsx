import React from 'react';
import { CssBaseline } from '@mui/material';
import { Outlet } from 'react-router-dom';
import Navbar from '../../Components/NavBar';

function MainLayout() {
  return (
    <div style={{ display: 'flex' }}>
      <CssBaseline />
      <Navbar />
      <Outlet />
    </div>
  );
}

export default MainLayout;
