import {AuthOperationLoginTypes, AuthOperationRegisterTypes} from '../Stores/ActionTypes/AuthOperationTypes';
import {errorInterface} from './ErrorHandling';

/* INTERFACES FOR ACTIONS DISPATCHED WHILE SIGN-IN PROCESS  */
export type LoginActionInterface =  {
    type : typeof AuthOperationLoginTypes.LOGIN_ACTION,
    payload : boolean,
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




/* INTERFACES FOR ACTIONS DISPATCHED WHILE SIGN-UP PROCESS  */
export type RegisterActionInterface = {
    type : typeof AuthOperationRegisterTypes.REGISTER_ACTION,
    payload : string,
}