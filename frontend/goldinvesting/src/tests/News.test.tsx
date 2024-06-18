// InvestmentCalculator.test.tsx
import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import InvestmentCalculator from '../Pages/simulateInvestment/SimulateInvestment';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import rootReducer from '../store/rootReducer';


// Mocking the PieChart component
jest.mock('@mui/x-charts/PieChart', () => ({
    PieChart: jest.fn().mockImplementation(({ children }) => <div>{children}</div>),
  }));
  
  const store = createStore(rootReducer);
  
  describe('InvestmentCalculator Component', () => {
    test('renders the form fields correctly', () => {
      render(
        <Provider store={store}>
          <InvestmentCalculator />
        </Provider>
      );
  
      expect(screen.getByLabelText(/Initial Investment/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/Monthly Inflation Rate/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/Monthly Contribution/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/Investment Period \(months\)/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/Monthly Interest Rate/i)).toBeInTheDocument();
    });
  
    test('shows error message when required fields are empty', () => {
      render(
        <Provider store={store}>
          <InvestmentCalculator />
        </Provider>
      );
  
      fireEvent.click(screen.getByText(/Calculate/i));
  
      expect(screen.getByText(/Please fill in all required fields with valid numbers./i)).toBeInTheDocument();
    });
  
    test('calculates and displays results when form is submitted with valid inputs', () => {
      render(
        <Provider store={store}>
          <InvestmentCalculator />
        </Provider>
      );
  
      fireEvent.change(screen.getByLabelText(/Initial Investment/i), { target: { value: '1000' } });
      fireEvent.change(screen.getByLabelText(/Monthly Inflation Rate/i), { target: { value: '0.5' } });
      fireEvent.change(screen.getByLabelText(/Monthly Contribution/i), { target: { value: '100' } });
      fireEvent.change(screen.getByLabelText(/Investment Period \(months\)/i), { target: { value: '12' } });
      fireEvent.change(screen.getByLabelText(/Monthly Interest Rate/i), { target: { value: '1' } });
  
      fireEvent.click(screen.getByText(/Calculate/i));
  
      expect(screen.getByText(/Results:/i)).toBeInTheDocument();
      expect(screen.getByText(/Future Value:/i)).toBeInTheDocument();
      expect(screen.getByText(/Total Invested:/i)).toBeInTheDocument();
      expect(screen.getByText(/Gross Profit:/i)).toBeInTheDocument();
    });
  });