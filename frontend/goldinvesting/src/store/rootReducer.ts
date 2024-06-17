// src/store/rootReducer.ts
import { combineReducers } from '@reduxjs/toolkit';
import authReducer from '../features/auth/AuthSlice';

const rootReducer = combineReducers({
  auth: authReducer,
});

export default rootReducer;
