import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const fetchInvestments = async () => {
  //const response = await axios.get(`${API_BASE_URL}/investments`);
  //return response.data;

  const mockData = [
    { id: 0, value: 10, label: 'Renda Fixa' },
    { id: 1, value: 15, label: 'Ações' },
    { id: 2, value: 45, label: 'Conta Corrente' },
  ];

  return mockData;
};

export const fetchAccordionItems = async (userId: string) => {
  const response = await axios.get(`${API_BASE_URL}/my-investments/all/${userId}`);
  return response.data;
};

export const fetchInvestmentHistory = async (userId: string) => {
  const response = await axios.get(`${API_BASE_URL}/my-investments/all/${userId}`);
  return response.data;
};

export const fetchBrokers = async (input: string) => {
  const response = await axios.get(`${API_BASE_URL}/brokers/get-by-text?input=${input}`);
  return response.data;
};

export const fetchStocksSymbols = async (input: string) => {
  const response = await axios.get(`${API_BASE_URL}/stocks-symbols/get-by-text?input=${input}`);
  return response.data;
};

export const createStock = async (userId: string, data: any) => {
  const response = await axios.post(`${API_BASE_URL}/stocks/${userId}`, data);
  return response.data;
};

export const createFixedIncome = async (userId: string, data: any) => {
  const response = await axios.post(`${API_BASE_URL}/fixed-incomes/${userId}`, data);
  return response.data;
};

export const createCheckingAccount = async (userId: string, data: any) => {
  const response = await axios.post(`${API_BASE_URL}/checking-accounts/${userId}`, data);
  return response.data;
};

export const fetchStocks = async (userId: string) => {
  const response = await axios.get(`${API_BASE_URL}/stocks/all/${userId}`);
  return response.data;
};

export const fetchFixedIncomes = async (userId: string) => {
  const response = await axios.get(`${API_BASE_URL}/fixed-incomes/all/${userId}`);
  return response.data;
};

export const fetchCheckingAccounts = async (userId: string) => {
  const response = await axios.get(`${API_BASE_URL}/checking-accounts/all/${userId}`);
  return response.data;
};

export const sellStock = async (stockId: string) => {
  const response = await axios.post(`${API_BASE_URL}/stocks/sell/${stockId}`);
  return response.data;
};

export const deleteFixedIncome = async (fixedIncomeId: string) => {
  const response = await axios.delete(`${API_BASE_URL}/fixed-incomes/${fixedIncomeId}`);
  return response.data;
};

export const concludeCheckingAccount = async (checkingAccountId: string) => {
  const response = await axios.post(`${API_BASE_URL}/checking-accounts/conclude/${checkingAccountId}`);
  return response.data;
};