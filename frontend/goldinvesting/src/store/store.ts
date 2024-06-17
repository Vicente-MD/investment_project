import { configureStore } from '@reduxjs/toolkit';
import userReducer from '../features/user/userReducer';
import newsReducer from '../features/news/NewsSlice';
import investmentsReducer from '../features/Investments/InvestmentsSlice';

export const store = configureStore({
  reducer: {
    user: userReducer,
    news: newsReducer,
    investments: investmentsReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
