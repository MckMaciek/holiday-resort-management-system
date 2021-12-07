import {combineReducers} from "redux";
import LoginReducer from './LoginReducer';
import RegisterReducer from './RegisterReducer';
import EmailReducer from './EmailReducer';
import UserOperationsReducer from './UserOperationsReducer';
import ReservationReducer from './ReservationReducer';

const CombinedReducers = combineReducers({
    LoginReducer : LoginReducer,
    RegisterReducer : RegisterReducer,
    EmailReducer : EmailReducer,
    UserOperationsReducer : UserOperationsReducer,
    ReservationReducer : ReservationReducer,
});


export default CombinedReducers;