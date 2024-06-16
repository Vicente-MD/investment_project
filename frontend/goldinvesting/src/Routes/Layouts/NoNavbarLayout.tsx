import React from 'react';
import { CssBaseline } from '@mui/material';
import { Outlet } from 'react-router-dom';

function NoNavbarLayout() {
  return (
    <div style={{ display: 'flex' }}>
      <CssBaseline />
      <Outlet />
    </div>
  );
}

export default NoNavbarLayout;
