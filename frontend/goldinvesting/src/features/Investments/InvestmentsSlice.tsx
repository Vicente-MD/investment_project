import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { fetchInvestments, fetchAccordionItems } from '../../Services/api';

interface Investment {
  id: number;
  value: number;
  label: string;
}

interface AccordionItem {
  title: string;
  rows: { id: number; name: string; value: number }[];
}

interface InvestmentsState {
  data: Investment[];
  accordionItems: AccordionItem[];
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  error: string | null;
}

const initialState: InvestmentsState = {
  data: [],
  accordionItems: [],
  status: 'idle',
  error: null,
};

export const fetchInvestmentData = createAsyncThunk(
  'investments/fetchInvestmentData',
  async () => {
    const investments = await fetchInvestments();
    return investments;
  }
);

export const fetchAccordionItemsData = createAsyncThunk(
  'investments/fetchAccordionItemsData',
  async () => {
    const accordionItems = await fetchAccordionItems();
    return accordionItems;
  }
);

const investmentsSlice = createSlice({
  name: 'investments',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchInvestmentData.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchInvestmentData.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.data = action.payload;
      })
      .addCase(fetchInvestmentData.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message ?? 'An error occurred';
      })
      .addCase(fetchAccordionItemsData.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchAccordionItemsData.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.accordionItems = action.payload;
      })
      .addCase(fetchAccordionItemsData.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message ?? 'An error occurred';
      });
  },
});

export default investmentsSlice.reducer;
