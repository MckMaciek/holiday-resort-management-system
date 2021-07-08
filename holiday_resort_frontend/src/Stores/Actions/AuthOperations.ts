import makeAction from '../ActionMaker/ActionMaker';
import {AuthOperationLoginTypes, AuthOperationRegisterTypes} from '../ActionTypes/AuthOperationTypes';
import {errorInterface} from '../../Interfaces/ErrorHandling';
import {LoginActionInterface, 
    LoginActionFetchingInterface,
    LoginActionIsSetInReducerInterface,
    LoginActionErrorInterface
    } from '../../Interfaces/AuthOperations';


/* ACTIONS DISPATCHED WHILE SIGN-IN PROCESS */
export const loginAction  = (payload : any) : LoginActionInterface => ({
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

export const loginSetError = (erorr : errorInterface) : LoginActionErrorInterface => ({
    type : AuthOperationLoginTypes.LOGIN_PROCESS_ERROR,
    error : erorr,
})


/* ACTIONS DISPATCHED WHILE SIGN-UP PROCESS */
export const registerAction : any = makeAction(AuthOperationRegisterTypes.REGISTER_ACTION)('payload');
export const registerFetching : any = makeAction(AuthOperationRegisterTypes.REGISTER_FETCHING)('isFetching');
export const registerSetReducer : any = makeAction(AuthOperationRegisterTypes.REGISTER_SET_IN_REDUCER)('isSet');
export const registerFail : any = makeAction(AuthOperationRegisterTypes.REGISTER_PROCESS_ERROR)('payload');
