import { AnyAction } from 'redux';
import { AUTHENTICATE_USER, REGISTER_USER, UPDATE_USER, USER_ERROR, LOGOUT_USER } from '../actions/userActions';

interface UserState {
  user: any | null;
  error: string | null;
  isAuthenticated: boolean;
}

const initialState: UserState = {
  user: null,
  error: null,
  isAuthenticated: false,
};

const userReducer = (state = initialState, action: AnyAction): UserState => {
  switch (action.type) {
    case AUTHENTICATE_USER:
      return { ...state, user: action.payload, isAuthenticated: true, error: null };
    case REGISTER_USER:
      return { ...state, user: action.payload, isAuthenticated: true, error: null };
    case UPDATE_USER:
      return { ...state, user: action.payload, error: null };
    case USER_ERROR:
      return { ...state, error: action.payload };
    case LOGOUT_USER:
      return initialState; // Reset the state to initial state on logout
    default:
      return state;
  }
};

export default userReducer;
