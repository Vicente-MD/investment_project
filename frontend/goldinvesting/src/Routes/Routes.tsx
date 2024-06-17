import { createBrowserRouter, RouteObject } from 'react-router-dom';
import MainLayout from './Layouts/MainLayout';
import NoNavbarLayout from './Layouts/NoNavbarLayout';
import ProtectedRoute from './ProtectedRoute'; // Update path if necessary
import Home from '../Pages/home/Home';
import Login from '../Pages/login/Login';
import AddInvestment from '../Pages/addInvestment/AddInvestment';
import Stocks from '../Pages/stocks/Stocks';
import News from '../Pages/news/News';
import Registration from '../Pages/registration/Registration';
import SimulateInvestment from '../Pages/simulateInvestment/SimulateInvestment';
import Investments from '../Pages/investments/Investments';
import Account from '../Pages/account/Account';

const routes: RouteObject[] = [
  {
    path: '/',
    element: <NoNavbarLayout />,
    children: [
      { path: '', element: <Login /> },
      { path: 'registration', element: <Registration /> },
    ],
  },
  {
    path: '/',
    element: <ProtectedRoute />, // Use ProtectedRoute here
    children: [
      {
        element: <MainLayout />,
        children: [
          { path: 'home', element: <Home /> },
          { path: 'news', element: <News /> },
          { path: 'account', element: <Account /> },
          { path: 'addinvestments', element: <AddInvestment /> },
          { path: 'stocks', element: <Stocks /> },
          { path: 'simulate', element: <SimulateInvestment /> },
          { path: 'investments', element: <Investments /> },
        ],
      },
    ],
  },
];

export const router = createBrowserRouter(routes);
