import {AuthOperationLoginTypes, AuthOperationRegisterTypes} from '../Stores/ActionTypes/AuthOperationTypes';
import { RegisterActionPayloadInterface } from './RegisterActionPayload';

import {errorInterface} from './ErrorHandling';
import { LoginResponse } from './LoginResponse';
import { RegisterResponse } from './RegisterResponse';

/* INTERFACES FOR ACTIONS DISPATCHED WHILE SIGN-IN PROCESS  */
export type LoginActionInterface =  {
    type : typeof AuthOperationLoginTypes.LOGIN_ACTION,
    payload : LoginResponse,
}

export type LoginActionFetchingInterface =  {
    type : typeof AuthOperationLoginTypes.LOGIN_FETCHING,
    isFetching : boolean,
}

export type LoginActionIsSetInReducerInterface =  {
    type : typeof AuthOperationLoginTypes.LOGIN_SET_IN_REDUCER,
    isSet : boolean,
}

export type LoginActionErrorInterface = {
    type : typeof AuthOperationLoginTypes.LOGIN_PROCESS_ERROR,
    error : errorInterface,
}

export type LoginActionIsAuthenticated = {
    type : typeof AuthOperationLoginTypes.SET_AUTHENTICATED,
    isAuth : boolean,
}

export type LoginGenericAction = 
    LoginActionErrorInterface | 
    LoginActionIsSetInReducerInterface | 
    LoginActionFetchingInterface | 
    LoginActionIsAuthenticated |
    LoginActionInterface;

/* INTERFACES FOR ACTIONS DISPATCHED WHILE SIGN-UP PROCESS  */
export type RegisterActionInterface = {
    type : typeof AuthOperationRegisterTypes.REGISTER_ACTION,
    payload : RegisterResponse,
}

export type RegisterActionFetchingInterface =  {
    type : typeof AuthOperationRegisterTypes.REGISTER_FETCHING,
    isFetching : boolean,
}

export type RegisterActionIsSetInReducerInterface =  {
    type : typeof AuthOperationRegisterTypes.REGISTER_SET_IN_REDUCER,
    isSet : boolean,
}

export type RegisterActionErrorInterface = {
    type : typeof AuthOperationRegisterTypes.REGISTER_PROCESS_ERROR,
    error : errorInterface,
}

export type RegisterGenericAction = 
    RegisterActionInterface | 
    RegisterActionFetchingInterface | 
    RegisterActionIsSetInReducerInterface | 
    RegisterActionErrorInterface;
