import { combineReducers } from '@reduxjs/toolkit';
import userReducer from '../Features/user/userSlice';
import newsReducer from '../Features/news/NewsSlice';
import investmentsReducer from '../Features/Investments/InvestmentsSlice';

const rootReducer = combineReducers({
  user: userReducer,
  news: newsReducer,
  investments: investmentsReducer,
});

export default rootReducer;
