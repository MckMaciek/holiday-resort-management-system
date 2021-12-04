import {EmailGenericAction} from '../../Interfaces/EmailOperations';
import {EmailOperationTypes} from '../ActionTypes/EmailOperationTypes';

interface INIT_STATE_EMAIL_INTERFACE {

    isSent : boolean,
    isFetching : boolean,
};

const INIT_STATE_EMAIL : INIT_STATE_EMAIL_INTERFACE = {

    isSent : false,
    isFetching : false,
}


const EmailReducer = (state : INIT_STATE_EMAIL_INTERFACE = INIT_STATE_EMAIL, action : EmailGenericAction ) : INIT_STATE_EMAIL_INTERFACE => {

    switch(action.type){
        case EmailOperationTypes.EMAIL_SENT : {
            return {...state, isSent : action.isSent}
        }
        case EmailOperationTypes.EMAIL_SENT_FETCHING : {
            return {...state, isFetching : action.isFetching}
        }
        default : {
            return state;
        }
    }
}
export default EmailReducer;