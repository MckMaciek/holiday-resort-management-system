import Axios from 'axios';
import { ThunkDispatch } from 'redux-thunk';

import {RegisterActionPayloadInterface} from "../../Interfaces/RegisterActionPayload";
import {RegisterResponse } from "../../Interfaces/RegisterResponse";
import {registerAction,
        registerFetching,
        registerSetError, 
        registerSetReducer} from "../Actions/AuthOperations";
import {registerEmailSent,
        registerEmailFetching,
        
} from "../Actions/EmailOperations";

import API_URL from '../../API_URL.json';

const sendRegisterRequest =  async (registerModel : RegisterActionPayloadInterface) : Promise<RegisterResponse> => {
    const registerRequest = await Axios.post(`${API_URL.SERVER_URL}${API_URL.REGISTER}`, registerModel);

    return registerRequest.request.status as RegisterResponse;
}

const registerApiRequest = (registerModel : RegisterActionPayloadInterface) => {
    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {

        try{
            dispatch(registerFetching(true));

            const registerResponse = await sendRegisterRequest(registerModel);
            dispatch(registerEmailFetching(true));
            
            dispatch(registerEmailSent(true));
            dispatch(registerAction(registerResponse));
            dispatch(registerSetReducer(true));

        }
        catch (err){
            console.log(err);
            dispatch(registerSetReducer(false));
            dispatch(registerSetError({isErrorFlagSet : true}))
            dispatch(registerEmailSent(false));
        }
        finally{
            dispatch(registerFetching(false));
            dispatch(registerEmailFetching(false));
        }

    }
}
export default registerApiRequest;