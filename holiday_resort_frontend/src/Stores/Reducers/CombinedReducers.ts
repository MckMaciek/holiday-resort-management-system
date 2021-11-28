import {combineReducers} from "redux";
import LoginReducer from './LoginReducer';
import RegisterReducer from './RegisterReducer';
import Maybe from '../../Maybe/Maybe';

const CombinedReducers = combineReducers({
    LoginReducer : LoginReducer,
    RegisterReducer : RegisterReducer,
});

const filterNumAbove5 = (numArray : any) =>  numArray.filter(num  => num >= 5);
const filterNumAbove10 = (numArray : any) => numArray.filter(num2 => num2 * 2);
const mapToTwiceSize = (numArray : any) => numArray.map(num2 => num2 * 2);

let array = Maybe([1,2,3,4, null,6,7, null ,9,10]).map(filterNumAbove5).map(mapToTwiceSize).map(filterNumAbove10);



export default CombinedReducers;