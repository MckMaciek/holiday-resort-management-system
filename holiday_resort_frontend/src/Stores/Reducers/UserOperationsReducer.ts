import {UserOperationGenericAction} from '../../Interfaces/UserOperations';
import {UserOperationTypes} from '../ActionTypes/UserOperationsTypes';


interface INIT_STATE_USER_OPERATIONS {
    isLoginFailed : boolean,
};

const INIT_STATE : INIT_STATE_USER_OPERATIONS = {
    isLoginFailed : false,
}

const UserOperationsReducer = 
(state : INIT_STATE_USER_OPERATIONS = INIT_STATE, action : UserOperationGenericAction ) : INIT_STATE_USER_OPERATIONS => {

    switch(action.type){
        case UserOperationTypes.LOGIN_ATTEMPT_FAILED : {
            return {...state, isLoginFailed : action.isFailed}
        }

        default : {
            return state;
        }
    }
}
export default UserOperationsReducer;