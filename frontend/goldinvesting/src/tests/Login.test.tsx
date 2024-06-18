// Login.test.tsx
import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import { MemoryRouter } from 'react-router-dom';
import { createStore } from 'redux';
import Login from '../Pages/login/Login';
import rootReducer from '../store/rootReducer';
import LocalStorageService from '../app/services/localStorageService';

jest.mock('../app/services/localStorageService');

const store = createStore(rootReducer);

describe('Login Component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders login form', () => {
    render(
      <Provider store={store}>
        <MemoryRouter>
          <Login />
        </MemoryRouter>
      </Provider>
    );

    expect(screen.getByLabelText(/E-mail/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Senha/i)).toBeInTheDocument();
    expect(screen.getByText(/Login/i)).toBeInTheDocument();
  });

  test('shows error message when fields are empty', () => {
    render(
      <Provider store={store}>
        <MemoryRouter>
          <Login />
        </MemoryRouter>
      </Provider>
    );

    fireEvent.click(screen.getByText(/Entrar/i));

    expect(screen.getByText(/Preencha todos os campos/i)).toBeInTheDocument();
  });

  test('shows error message with invalid credentials', () => {
    render(
      <Provider store={store}>
        <MemoryRouter>
          <Login />
        </MemoryRouter>
      </Provider>
    );

    fireEvent.change(screen.getByLabelText(/E-mail/i), { target: { value: 'wrong' } });
    fireEvent.change(screen.getByLabelText(/Senha/i), { target: { value: 'wrong' } });
    fireEvent.click(screen.getByText(/Entrar/i));

    expect(screen.getByText(/Invalid username or password/i)).toBeInTheDocument();
  });

  test('calls login with correct credentials', () => {
    const fakeUser = 'user';
    const fakePassword = 'password';

    render(
      <Provider store={store}>
        <MemoryRouter>
          <Login />
        </MemoryRouter>
      </Provider>
    );

    fireEvent.change(screen.getByLabelText(/E-mail/i), { target: { value: fakeUser } });
    fireEvent.change(screen.getByLabelText(/Senha/i), { target: { value: fakePassword } });
    fireEvent.click(screen.getByText(/Entrar/i));

    expect(LocalStorageService.addItem).toHaveBeenCalledWith('isAuthenticated', true);
    expect(LocalStorageService.addItem).toHaveBeenCalledWith('user', fakeUser);
  });
});
