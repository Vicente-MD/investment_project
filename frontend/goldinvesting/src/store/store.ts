// store.ts
import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // defaults to localStorage for web
import userReducer from '../features/user/userSlice';
import newsReducer from '../features/news/NewsSlice';
import investmentsReducer from '../features/Investments/InvestmentsSlice';

// Combine your reducers
const rootReducer = combineReducers({
  user: userReducer,
  news: newsReducer,
  investments: investmentsReducer,
});

// Persist config
const persistConfig = {
  key: 'root',
  storage,
};

// Apply persistence to the rootReducer
const persistedReducer = persistReducer(persistConfig, rootReducer);

// Configure store with persisted reducer
export const store = configureStore({
  reducer: persistedReducer,
});

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
