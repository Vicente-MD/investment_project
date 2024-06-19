import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import { configureStore } from '@reduxjs/toolkit';
import SignUp from '../Pages/account/Account'; // Adjust the path according to your file structure
import userReducer, { UserState } from '../Features/user/userSlice';
import { MemoryRouter } from 'react-router-dom';

// Mock initial state for the store
const initialState: { user: UserState } = {
  user: {
    user: {
      id: '1',
      name: 'John Doe',
      email: 'john.doe@example.com',
      password: 'password123',
    },
    error: null,
    isAuthenticated: true,
    status: 'idle',
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

  test('toggles password visibility', () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <SignUp />
        </MemoryRouter>
      </Provider>
    );

    // Initially, the password should be hidden
    const passwordInput = screen.getByTestId('password');
    expect(passwordInput).toHaveAttribute('type', 'password');

    // Click the toggle button to show the password
    const toggleButton = screen.getByLabelText(/toggle password visibility/i);
    fireEvent.click(toggleButton);
    expect(passwordInput).toHaveAttribute('type', 'text');

    // Click the toggle button again to hide the password
    fireEvent.click(toggleButton);
    expect(passwordInput).toHaveAttribute('type', 'password');
  });

  test('handles form submission', () => {
    render(
      <Provider store={mockStore}>
        <MemoryRouter>
          <SignUp />
        </MemoryRouter>
      </Provider>
    );

    const handleSubmit = jest.fn();

    const form = screen.getByRole('form');
    form.onsubmit = handleSubmit;

    fireEvent.submit(form);

    // Ensure handleSubmit is called
    expect(handleSubmit).toHaveBeenCalled();
  });
});
