import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import { configureStore } from '@reduxjs/toolkit';
import { MemoryRouter } from 'react-router-dom';
import Login from '../Pages/login/Login';
import userReducer, { UserState } from '../Features/user/userSlice';

// Mock initial state for the store
const initialState: { user: UserState } = {
  user: {
    isAuthenticated: false,
    error: null,
    user: undefined,
    status: 'idle'
  },
};

// Create a mock store with the initial state
const mockStore = configureStore({
  reducer: {
    user: userReducer,
  },
  preloadedState: initialState,
});

describe('Login Component', () => {
  test('renders login form', () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <Login />
        </MemoryRouter>
      </Provider>
    );

    // Check if Login text is in the document
    expect(screen.getByText(/Login/i)).toBeInTheDocument();
  });

  test('checks for input fields and buttons', () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <Login />
        </MemoryRouter>
      </Provider>
    );

    // Check if email and password fields are rendered
    expect(screen.getByLabelText(/E-mail/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Senha/i)).toBeInTheDocument();
    expect(screen.getByText(/Entrar/i)).toBeInTheDocument();
  });

  test('shows error message when fields are empty and login button is clicked', async () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <Login />
        </MemoryRouter>
      </Provider>
    );

    fireEvent.click(screen.getByText(/Entrar/i));
    
    await waitFor(() => {
      expect(screen.getByText(/Preencha todos os campos/i)).toBeInTheDocument();
    });
  });

  test('toggles password visibility', () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <Login />
        </MemoryRouter>
      </Provider>
    );

    const passwordField = screen.getByLabelText(/Senha/i);
    const toggleButton = screen.getByLabelText(/toggle password visibility/i);

    // Initial state should be password type
    expect(passwordField).toHaveAttribute('type', 'password');

    // Click toggle button to show password
    fireEvent.click(toggleButton);
    expect(passwordField).toHaveAttribute('type', 'text');

    // Click toggle button again to hide password
    fireEvent.click(toggleButton);
    expect(passwordField).toHaveAttribute('type', 'password');
  });

  test('shows loading indicator when logging in', () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <Login />
        </MemoryRouter>
      </Provider>
    );

    // Fill in the form
    fireEvent.change(screen.getByLabelText(/E-mail/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByLabelText(/Senha/i), { target: { value: 'password' } });

    // Click login button
    fireEvent.click(screen.getByText(/Entrar/i));

    // Check if loading indicator appears
    expect(screen.getByRole('progressbar')).toBeInTheDocument();
  });
});
