import {EmailOperationTypes
} from '../ActionTypes/EmailOperationTypes';
import 
{EmailActionSentInterface,
EmailActionSentFetchingInterface

} from '../../Interfaces/ReduxInterfaces/EmailOperations';

export const registerEmailSent = (isSent : boolean) : EmailActionSentInterface => ({
    type : EmailOperationTypes.EMAIL_SENT,
    isSent : isSent,
})

export const registerEmailFetching = (isFetching : boolean) : EmailActionSentFetchingInterface => ({
    type : EmailOperationTypes.EMAIL_SENT_FETCHING,
    isFetching : isFetching,
})