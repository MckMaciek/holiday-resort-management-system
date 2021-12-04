import {EmailOperationTypes} from '../Stores/ActionTypes/EmailOperationTypes';

export type EmailActionSentInterface =  {
    type : typeof EmailOperationTypes.EMAIL_SENT,
    isSent : boolean,
}

export type EmailActionSentFetchingInterface = {
    type : typeof EmailOperationTypes.EMAIL_SENT_FETCHING,
    isFetching : boolean,
}


export type EmailGenericAction = 
    EmailActionSentInterface |
    EmailActionSentFetchingInterface;