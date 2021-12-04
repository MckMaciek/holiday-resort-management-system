import {UserActionLoginAttemptFailed} from '../../Interfaces/UserOperations';
import {UserOperationTypes} from '../ActionTypes/UserOperationsTypes';


export const loginAttemptFailed = (isFailed : boolean) : UserActionLoginAttemptFailed  => ({
    type : UserOperationTypes.LOGIN_ATTEMPT_FAILED,
    isFailed : isFailed,
})