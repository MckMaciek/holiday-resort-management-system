import {UserOperationTypes} from "../Stores/ActionTypes/UserOperationsTypes";


export type UserActionLoginAttemptFailed = {
    type : typeof UserOperationTypes.LOGIN_ATTEMPT_FAILED,
    isFailed : boolean,
}


export type UserOperationGenericAction = 
UserActionLoginAttemptFailed;