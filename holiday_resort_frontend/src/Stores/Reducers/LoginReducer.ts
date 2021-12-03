import {AuthOperationLoginTypes} from '../ActionTypes/AuthOperationTypes';
import {LoginGenericAction} from "../../Interfaces/AuthOperations";
import {errorInterface} from '../../Interfaces/ErrorHandling';

interface INIT_STATE_LOGIN_INTERFACE {

    jwt : string | null,
    userId : string | null,
    username : string | null,
    email : string | null,
    roles : Array<string> | null,

    isAuthenticated : boolean,

    isLoginInReducer : boolean,
    isLoginFetching : boolean,
    error : errorInterface | null
};

const INIT_STATE_LOGIN : INIT_STATE_LOGIN_INTERFACE = {

    jwt : null,
    userId : null,
    username : null,
    email : null,
    roles : null,

    isAuthenticated : false,

    isLoginInReducer : false,
    isLoginFetching : false,
    error : null
}


const LoginReducer = (state : INIT_STATE_LOGIN_INTERFACE = INIT_STATE_LOGIN, action : LoginGenericAction ) : INIT_STATE_LOGIN_INTERFACE => {

    switch(action.type){
        case AuthOperationLoginTypes.LOGIN_ACTION : {
            const {jwt, userId, username, email, roles} = action.payload;
            return {...state, jwt : jwt, userId : userId, username : username, email : email, roles : roles}
        }
        case AuthOperationLoginTypes.SET_AUTHENTICATED : {
            console.log(action);
            return {...state, isAuthenticated : action.isAuth}
        }
        case AuthOperationLoginTypes.LOGIN_FETCHING : {
            return {...state, isLoginFetching : action.isFetching}
        }
        case AuthOperationLoginTypes.LOGIN_PROCESS_ERROR : {
            return {...state, error : action.error}
        }
        case AuthOperationLoginTypes.LOGIN_SET_IN_REDUCER : {
            return {...state, isLoginInReducer : action.isSet}
        }
        default : {
            return state;
        }
    }
}
export default LoginReducer;