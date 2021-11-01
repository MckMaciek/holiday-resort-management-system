import Axios from 'axios';
import { ThunkDispatch } from 'redux-thunk';

import {RegisterActionPayloadInterface} from "../../Interfaces/RegisterActionPayload";
import {RegisterResponse } from "../../Interfaces/RegisterResponse";
import {registerAction,
        registerFetching,
        registerSetError, 
        registerSetReducer} from "../Actions/AuthOperations";


const sendRegisterRequest =  async (registerModel : RegisterActionPayloadInterface) : Promise<RegisterResponse> => {
    const registerRequest = await Axios.post("http://localhost:8080/api/auth/sign-up", registerModel);

    return registerRequest.request.status as RegisterResponse;
}

const registerApiRequest = (registerModel : RegisterActionPayloadInterface) => {
    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {

        try{
            dispatch(registerFetching(true));
            const registerResponse = await sendRegisterRequest(registerModel);
            console.log(registerResponse);

            dispatch(registerAction(registerResponse));
            dispatch(registerSetReducer(true));
        }
        catch (err){
            console.log("ERROR-WHILE-REGISTER");
            dispatch(registerSetReducer(false));
            dispatch(registerSetError({isErrorFlagSet : true}))
        }
        finally{
            dispatch(registerFetching(false));
        }

    }
}
export default registerApiRequest;