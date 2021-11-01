import {combineReducers} from "redux";
import LoginReducer from './LoginReducer';
import RegisterReducer from './RegisterReducer';

const CombinedReducers = combineReducers({
    LoginReducer : LoginReducer,
    RegisterReducer : RegisterReducer,
});

export default CombinedReducers;