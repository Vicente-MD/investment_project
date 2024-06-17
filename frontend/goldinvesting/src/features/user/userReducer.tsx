// reducers/userReducer.ts
import { AnyAction } from 'redux';
import { AUTHENTICATE_USER, REGISTER_USER, UPDATE_USER, USER_ERROR } from '../actions/userActions';

interface UserState {
  user: any | null; // Update type to handle User object
  error: string | null;
  isAuthenticated: boolean; // Add the isAuthenticated property
}

const initialState: UserState = {
  user: null,
  error: null,
  isAuthenticated: false, // Set initial isAuthenticated to false
};

const userReducer = (state = initialState, action: AnyAction): UserState => {
  switch (action.type) {
    case AUTHENTICATE_USER:
      return { ...state, user: action.payload, error: null };
    case REGISTER_USER:
      return { ...state, user: action.payload, error: null };
    case UPDATE_USER:
      return { ...state, user: action.payload, error: null };
    case USER_ERROR:
      return { ...state, error: action.payload };
    default:
      return state;
  }
};

export default userReducer;
