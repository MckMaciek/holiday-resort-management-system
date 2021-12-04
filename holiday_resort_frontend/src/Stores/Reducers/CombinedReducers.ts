import {combineReducers} from "redux";
import LoginReducer from './LoginReducer';
import RegisterReducer from './RegisterReducer';
import EmailReducer from './EmailReducer';
import UserOperationsReducer from './UserOperationsReducer';

const CombinedReducers = combineReducers({
    LoginReducer : LoginReducer,
    RegisterReducer : RegisterReducer,
    EmailReducer : EmailReducer,
    UserOperationsReducer : UserOperationsReducer,
});


export default CombinedReducers;