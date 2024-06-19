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

    expect(screen.getByLabelText(/Investimento Inicial/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Taxa de Inflação Mensal/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Aporte Mensal/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Período de Investimento \(meses\)/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Taxa de Juros Mensal/i)).toBeInTheDocument();
  });

  test('shows error message when required fields are empty', () => {
    render(
      <Provider store={store}>
        <InvestmentCalculator />
      </Provider>
    );

    fireEvent.click(screen.getByText(/Calcular/i));

    expect(screen.getByText(/Por favor, preencha todos os campos obrigatórios com números válidos./i)).toBeInTheDocument();
  });

  test('calculates and displays results when form is submitted with valid inputs', () => {
    render(
      <Provider store={store}>
        <InvestmentCalculator />
      </Provider>
    );

    fireEvent.change(screen.getByLabelText(/Investimento Inicial/i), { target: { value: '1000' } });
    fireEvent.change(screen.getByLabelText(/Taxa de Inflação Mensal/i), { target: { value: '0.5' } });
    fireEvent.change(screen.getByLabelText(/Aporte Mensal/i), { target: { value: '100' } });
    fireEvent.change(screen.getByLabelText(/Período de Investimento \(meses\)/i), { target: { value: '12' } });
    fireEvent.change(screen.getByLabelText(/Taxa de Juros Mensal/i), { target: { value: '1' } });

    fireEvent.click(screen.getByText(/Calcular/i));

    expect(screen.getByText(/Resultados:/i)).toBeInTheDocument();
    expect(screen.getByText(/Valor Futuro:/i)).toBeInTheDocument();
    expect(screen.getByText(/Total Investido:/i)).toBeInTheDocument();
    expect(screen.getByText(/Lucro Bruto:/i)).toBeInTheDocument();
  });
});
