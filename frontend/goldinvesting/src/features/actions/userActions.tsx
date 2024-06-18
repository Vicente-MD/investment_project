import { Dispatch } from 'redux';
import UsuarioService from '../../app/service/usuarioService';

export const AUTHENTICATE_USER = 'AUTHENTICATE_USER';
export const REGISTER_USER = 'REGISTER_USER';
export const UPDATE_USER = 'UPDATE_USER';
export const USER_ERROR = 'USER_ERROR';

enum UserActionTypes {
  AUTHENTICATE_USER = 'AUTHENTICATE_USER',
  REGISTER_USER = 'REGISTER_USER',
  UPDATE_USER = 'UPDATE_USER',
  USER_ERROR = 'USER_ERROR'
}

interface Credenciais {
  email: string;
  password: string;
}

interface Usuario {
  name: string;
  email: string;
  password: string;
}

interface AuthenticateUserAction {
  type: UserActionTypes.AUTHENTICATE_USER;
  payload: any;
}

interface RegisterUserAction {
  type: UserActionTypes.REGISTER_USER;
  payload: any;
}

interface UpdateUserAction {
  type: UserActionTypes.UPDATE_USER;
  payload: any;
}

interface UserErrorAction {
  type: UserActionTypes.USER_ERROR;
  payload: string;
}

type UserActions = AuthenticateUserAction | RegisterUserAction | UpdateUserAction | UserErrorAction;

const usuarioService = new UsuarioService();

export const authenticateUser = (credenciais: Credenciais) => async (dispatch: Dispatch<UserActions>) => {
  try {
    const response = await usuarioService.autenticar(credenciais);
    // Remove headers or any non-serializable data from the payload
    const { data, status } = response;
    dispatch({ type: UserActionTypes.AUTHENTICATE_USER, payload: { data, status } });
  } catch (error) {
    const errorMessage = (error as Error).message || 'An error occurred';
    dispatch({ type: UserActionTypes.USER_ERROR, payload: errorMessage });
  }
};

export const registerUser = (usuario: Usuario) => async (dispatch: Dispatch<UserActions>) => {
  try {
    const response = await usuarioService.cadastrarUsuario(usuario);
    dispatch({ type: UserActionTypes.REGISTER_USER, payload: response });
  } catch (error) {
    const errorMessage = (error as Error).message || 'An error occurred';
    dispatch({ type: UserActionTypes.USER_ERROR, payload: errorMessage });
  }
};

export const updateUser = (usuario: Usuario) => async (dispatch: Dispatch<UserActions>) => {
  try {
    const response = await usuarioService.atualizarUsuario(usuario);
    dispatch({ type: UserActionTypes.UPDATE_USER, payload: response });
  } catch (error) {
    const errorMessage = (error as Error).message || 'An error occurred';
    dispatch({ type: UserActionTypes.USER_ERROR, payload: errorMessage });
  }
};
