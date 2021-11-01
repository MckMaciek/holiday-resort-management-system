import {AuthOperationLoginTypes, AuthOperationRegisterTypes} from '../ActionTypes/AuthOperationTypes';

import {errorInterface} from '../../Interfaces/ErrorHandling';
import {LoginActionInterface, 
    LoginActionFetchingInterface,
    LoginActionIsSetInReducerInterface,
    LoginActionErrorInterface,
    RegisterActionInterface,
    RegisterActionErrorInterface,
    RegisterActionFetchingInterface,
    RegisterActionIsSetInReducerInterface,
    } from '../../Interfaces/AuthOperations';
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

