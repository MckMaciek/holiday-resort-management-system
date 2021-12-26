import {AuthOperationLoginTypes, AuthOperationRegisterTypes} from '../ActionTypes/AuthOperationTypes';

import {errorInterface} from '../../Interfaces/ErrorHandling';
import {LoginActionInterface, 
    LoginActionFetchingInterface,
    LoginActionIsSetInReducerInterface,
    LoginActionErrorInterface,
    LoginActionIsAuthenticated,
    RegisterActionInterface,
    RegisterActionErrorInterface,
    RegisterActionFetchingInterface,
    RegisterActionIsSetInReducerInterface,
    RegisterActionSetDetails,
    LoginActionTokenExpired
    } from '../../Interfaces/ReduxInterfaces/AuthOperations';
import { LoginResponse } from '../../Interfaces/LoginResponse';
import { RegisterResponse } from '../../Interfaces/RegisterResponse';


/* ACTIONS DISPATCHED WHILE SIGN-IN PROCESS */

export const loginAction  = (payload : LoginResponse) : LoginActionInterface => ({
    type : AuthOperationLoginTypes.LOGIN_ACTION,
    payload : payload,
});

export const loginFetching = (isFetching : boolean) : LoginActionFetchingInterface => ({
    type : AuthOperationLoginTypes.LOGIN_FETCHING,
    isFetching : isFetching,
});

export const loginSetReducer = (isSet : boolean) : LoginActionIsSetInReducerInterface => ({
    type : AuthOperationLoginTypes.LOGIN_SET_IN_REDUCER,
    isSet : isSet,
});

export const loginSetError = (error : errorInterface) : LoginActionErrorInterface => ({
    type : AuthOperationLoginTypes.LOGIN_PROCESS_ERROR,
    error : error,
})

export const loginSetAuthenticated = (isAuth : boolean) : LoginActionIsAuthenticated => ({
    type : AuthOperationLoginTypes.SET_AUTHENTICATED,
    isAuth : isAuth,
})

export const loginSetTokenExpired = (isTokenExpired : boolean) : LoginActionTokenExpired => ({
    type : AuthOperationLoginTypes.TOKEN_EXPIRED,
    isTokenExpired : isTokenExpired,
})

/* ACTIONS DISPATCHED WHILE SIGN-UP PROCESS */

export const registerAction  = (payload : RegisterResponse) : RegisterActionInterface => ({
    type : AuthOperationRegisterTypes.REGISTER_ACTION,
    payload : payload,
});

export const registerFetching  = (isFetching : boolean) : RegisterActionFetchingInterface => ({
    type : AuthOperationRegisterTypes.REGISTER_FETCHING,
    isFetching : isFetching,
});

export const registerSetReducer = (isSet : boolean) : RegisterActionIsSetInReducerInterface => ({
    type : AuthOperationRegisterTypes.REGISTER_SET_IN_REDUCER,
    isSet : isSet,
});

export const registerSetError = (error : errorInterface) : RegisterActionErrorInterface => ({
    type : AuthOperationRegisterTypes.REGISTER_PROCESS_ERROR,
    error : error,
})

export const registerSetDetails = (userDetails : {firstName : string, lastName : string, phoneNumber : string}) : RegisterActionSetDetails => ({
    type : AuthOperationRegisterTypes.REGISTER_SET_DETAILS,
    userDetails : userDetails,
})

