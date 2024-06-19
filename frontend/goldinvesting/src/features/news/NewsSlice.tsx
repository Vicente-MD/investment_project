import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';

interface Article {
  url: string;
  urlToImage: string;
  title: string;
  description: string;
}

interface NewsState {
  articles: Article[];
  loading: boolean;
  error: string | null;
}

const initialState: NewsState = {
  articles: [],
  loading: false,
  error: null,
};

export const fetchNews = createAsyncThunk('news/fetchNews', async () => {
  const response = await fetch('https://newsapi.org/v2/everything?q=mercado financeiro&apiKey=572353a32aed49179c9463f5eee0e609');
  const result = await response.json();
  return result.articles.filter((article: Article) => article.title !== '[Removed]');
});

const newsSlice = createSlice({
  name: 'news',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchNews.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchNews.fulfilled, (state, action: PayloadAction<Article[]>) => {
        state.articles = action.payload;
        state.loading = false;
      })
      .addCase(fetchNews.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || 'Failed to fetch news';
      });
  },
});

export default newsSlice.reducer;
