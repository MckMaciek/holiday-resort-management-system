import {combineReducers} from "redux";
import LoginReducer from './LoginReducer';
import RegisterReducer from './RegisterReducer';
import EmailReducer from './EmailReducer';
import UserOperationsReducer from './UserOperationsReducer';
import ReservationReducer from './ReservationReducer';
import ResortObjectsReducer from './ResortObjectsReducer';
import ResortObjectEventsReducer from './ResortObjectEventsReducer';

const CombinedReducers = combineReducers({
    LoginReducer : LoginReducer,
    RegisterReducer : RegisterReducer,
    EmailReducer : EmailReducer,
    UserOperationsReducer : UserOperationsReducer,
    ReservationReducer : ReservationReducer,
    ResortObjectsReducer : ResortObjectsReducer,
    ResortObjectEventsReducer : ResortObjectEventsReducer,
});


export default CombinedReducers;