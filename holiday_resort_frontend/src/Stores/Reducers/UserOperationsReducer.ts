import {UserOperationGenericAction} from '../../Interfaces/ReduxInterfaces/UserOperations';
import {UserOperationTypes} from '../ActionTypes/UserOperationsTypes';

interface INIT_STATE_USER_OPERATIONS {

    isLoginFailed : boolean,
    objectModified : boolean,
};

const INIT_STATE : INIT_STATE_USER_OPERATIONS = {

    isLoginFailed : false,
    objectModified: false,
}

const UserOperationsReducer = 
(state : INIT_STATE_USER_OPERATIONS = INIT_STATE, action : UserOperationGenericAction ) : INIT_STATE_USER_OPERATIONS => {

    switch(action.type){
        case UserOperationTypes.LOGIN_ATTEMPT_FAILED : {
            return {...state, isLoginFailed : action.isFailed}
        }

        case UserOperationTypes.OBJECT_MODIFIED : {
            return {...state, objectModified : action.isModified}
        }

        default : {
            return state;
        }
    }
}
export default UserOperationsReducer;