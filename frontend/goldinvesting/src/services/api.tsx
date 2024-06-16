// src/services/api.ts
import axios from 'axios';

const API_BASE_URL = 'https://your-backend-api-url.com'; // replace with your backend URL

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
  //const response = await axios.get(`${API_BASE_URL}/accordionItems`);
  //return response.data;

  const mockAccordionItems = [
    {
      title: 'Renda Fixa',
      rows: [
        { id: 1, name: 'Item 1', value: 80 },
        { id: 2, name: 'Item 2', value: 15 },
      ],
    },
    {
      title: 'Ações',
      rows: [
        { id: 3, name: 'Item 3', value: 10 },
        { id: 4, name: 'Item 4', value: 15 },
      ],
    },
    {
      title: 'Tesouro Direto',
      rows: [
        { id: 5, name: 'Item 5', value: 10 },
        { id: 6, name: 'Item 6', value: 15 },
      ],
    },
    {
      title: 'Conta Corrente',
      rows: [
        { id: 7, name: 'Item 5', value: 10 },
        { id: 8, name: 'Item 6', value: 15 },
      ],
    },
  ];

  return mockAccordionItems;

};
