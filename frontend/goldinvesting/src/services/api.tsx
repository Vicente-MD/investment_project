import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const fetchInvestments = async () => {
  //const response = await axios.get(`${API_BASE_URL}/investments`);
  //return response.data;

  const mockData = [
    { id: 0, value: 10, label: 'Renda Fixa' },
    { id: 1, value: 15, label: 'Ações' },
    { id: 2, value: 20, label: 'Tesouro Direto' },
    { id: 3, value: 45, label: 'Conta Corrente' },
  ];

  return mockData;
};

export const fetchAccordionItems = async () => {
  const response = await axios.get(`${API_BASE_URL}/my-investments/all/1`);
  console.log(response.data);
  return response.data;
};

export const fetchInvestmentHistory = async () => {
  const response = await axios.get(`${API_BASE_URL}/my-investments/all/1`);
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

export const createStock = async (data: any) => {
  const response = await axios.post(`${API_BASE_URL}/stocks/1`, data);
  return response.data;
};

export const createFixedIncome = async (data: any) => {
  const response = await axios.post(`${API_BASE_URL}/fixed-incomes/1`, data);
  return response.data;
};

export const createCheckingAccount = async (data: any) => {
  const response = await axios.post(`${API_BASE_URL}/checking-accounts/1`, data);
  return response.data;
};