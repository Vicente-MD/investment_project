import { configureStore } from '@reduxjs/toolkit';
import authReducer from '../features/auth/AuthSlice';
import newsReducer from '../features/news/NewsSlice';
import InvestmentsReducer from '../features/Investments/InvestmentsSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    news: newsReducer,
    investments: InvestmentsReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
