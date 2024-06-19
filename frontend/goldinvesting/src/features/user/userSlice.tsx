// userSlice.ts
import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import UsuarioService from '../../app/service/usuarioService';

interface UserState {
  user: any | null;
  error: string | null;
  isAuthenticated: boolean;
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
}

const initialState: UserState = {
  user: null,
  error: null,
  isAuthenticated: false,
  status: 'idle',
};

const usuarioService = new UsuarioService();

export const authenticateUser = createAsyncThunk(
  'user/authenticateUser',
  async (credenciais: { email: string, password: string }, { rejectWithValue }) => {
    try {
      const response = await usuarioService.autenticar(credenciais);
      const { data, status } = response;
      return { data, status };
    } catch (error) {
      return rejectWithValue((error as Error).message || 'An error occurred');
    }
  }
);

export const registerUser = createAsyncThunk(
  'user/registerUser',
  async (usuario: { name: string; email: string; password: string }, { rejectWithValue }) => {
    try {
      const response = await usuarioService.cadastrarUsuario(usuario);
      return response;
    } catch (error) {
      return rejectWithValue((error as Error).message || 'An error occurred');
    }
  }
);

export const updateUser = createAsyncThunk(
  'user/updateUser',
  async (usuario: { name: string; email: string; password: string }, { rejectWithValue }) => {
    try {
      const response = await usuarioService.atualizarUsuario(usuario);
      return response;
    } catch (error) {
      return rejectWithValue((error as Error).message || 'An error occurred');
    }
  }
);

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    userError(state, action: PayloadAction<string>) {
      state.error = action.payload;
    },
    logoutUser(state) {
      state.user = null;
      state.error = null;
      state.isAuthenticated = false;
    },
  },
  extraReducers: (builder) => {
    builder
      // authenticateUser
      .addCase(authenticateUser.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(authenticateUser.fulfilled, (state, action) => {
        state.user = action.payload;
        state.isAuthenticated = true;
        state.error = null;
        state.status = 'succeeded';
      })
      .addCase(authenticateUser.rejected, (state, action) => {
        state.error = action.payload as string;
        state.status = 'failed';
      })
      // registerUser
      .addCase(registerUser.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(registerUser.fulfilled, (state, action) => {
        state.user = action.payload;
        state.isAuthenticated = true;
        state.error = null;
        state.status = 'succeeded';
      })
      .addCase(registerUser.rejected, (state, action) => {
        state.error = action.payload as string;
        state.status = 'failed';
      })
      // updateUser
      .addCase(updateUser.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(updateUser.fulfilled, (state, action) => {
        state.user = action.payload;
        state.error = null;
        state.status = 'succeeded';
      })
      .addCase(updateUser.rejected, (state, action) => {
        state.error = action.payload as string;
        state.status = 'failed';
      });
  },
});

export const { userError, logoutUser } = userSlice.actions;

export default userSlice.reducer;
