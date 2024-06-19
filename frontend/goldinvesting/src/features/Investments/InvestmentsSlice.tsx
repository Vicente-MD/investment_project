import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { fetchAccordionItems } from '../../Services/api';

interface Investment {
  id: number;
  value: number;
  label: string;
}

interface AccordionItem {
  year: string | number;
  month: string | number;
  price: number;
  dividend: number;
  investmentType: string;
}

interface InvestmentsState {
  transformedData: Investment[];
  accordionItems: AccordionItem[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
}

const initialState: InvestmentsState = {
  transformedData: [],
  accordionItems: [],
  status: 'idle',
  error: null,
};

const getLastValues = (items: AccordionItem[]): AccordionItem[] => {
  const lastValues: { [key: string]: AccordionItem } = {};

  items.forEach(item => {
    const key = item.investmentType;
    if (!lastValues[key] ||
      item.year > lastValues[key].year ||
      (item.year === lastValues[key].year && item.month > lastValues[key].month)) {
      lastValues[key] = item;
    }
  });

  return Object.values(lastValues);
};

const investmentLabels: { [key: string]: string } = {
  'FIXED_INCOME': 'Renda Fixa',
  'STOCK': 'Ações',
  'CURRENT_ACCOUNT': 'Conta Corrente'
};

const labelIds: { [key: string]: number } = {
  'Renda Fixa': 1,
  'Ações': 2,
  'Conta Corrente': 3
};

const transformData = (items: AccordionItem[]): Investment[] => {
  const lastValues = getLastValues(items);

  return lastValues.map((item) => {
    const label = investmentLabels[item.investmentType] || item.investmentType;
    return {
      id: labelIds[label],
      value: item.price,
      label
    };
  });
};

export const fetchAccordionItemsData = createAsyncThunk(
  'investments/fetchAccordionItemsData',
  async (userId: string) => {
    const accordionItems = await fetchAccordionItems(userId);
    return accordionItems;
  }
);

const investmentsSlice = createSlice({
  name: 'investments',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchAccordionItemsData.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchAccordionItemsData.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.accordionItems = action.payload;
        state.transformedData = transformData(action.payload);
      })
      .addCase(fetchAccordionItemsData.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message ?? 'An error occurred';
      });
  },
});

export default investmentsSlice.reducer;
