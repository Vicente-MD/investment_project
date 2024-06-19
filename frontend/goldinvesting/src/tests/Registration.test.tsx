import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { configureStore } from '@reduxjs/toolkit';
import { MemoryRouter } from 'react-router-dom';
import SignUp from '../Pages/registration/Registration';
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

describe('SignUp Component', () => {
  test('renders registration form', () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <SignUp />
        </MemoryRouter>
      </Provider>
    );

    // Check if "Criar conta" header text is in the document
    const headers = screen.getAllByText(/Criar conta/i);
    expect(headers[0]).toBeInTheDocument();
  });

  test('checks for input fields and buttons', () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <SignUp />
        </MemoryRouter>
      </Provider>
    );

    // Check if all input fields are rendered
    expect(screen.getByLabelText(/Nome/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Email/i)).toBeInTheDocument();
    
    // Use getAllByLabelText to select the specific "Senha" fields
    const passwordFields = screen.getAllByLabelText(/Senha/i);
    expect(passwordFields[0]).toBeInTheDocument();
    expect(screen.getByLabelText(/Confirme a senha/i)).toBeInTheDocument();

    // Check if the "Criar conta" button is in the document
    const buttons = screen.getAllByText(/Criar conta/i);
    expect(buttons[1]).toBeInTheDocument();
  });

  test('shows loading indicator when submitting form', () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <SignUp />
        </MemoryRouter>
      </Provider>
    );

    // Fill in the form
    fireEvent.change(screen.getByLabelText(/Nome/i), { target: { value: 'Test User' } });
    fireEvent.change(screen.getByLabelText(/Email/i), { target: { value: 'test@example.com' } });

    // Use getAllByLabelText to select the correct "Senha" field by index
    const passwordFields = screen.getAllByLabelText(/Senha/i);
    fireEvent.change(passwordFields[0], { target: { value: 'password' } });
    fireEvent.change(screen.getByLabelText(/Confirme a senha/i), { target: { value: 'password' } });

    // Click submit button
    const buttons = screen.getAllByText(/Criar conta/i);
    fireEvent.click(buttons[1]);

    // Check if loading indicator appears
    expect(screen.getByRole('progressbar')).toBeInTheDocument();
  });
});
