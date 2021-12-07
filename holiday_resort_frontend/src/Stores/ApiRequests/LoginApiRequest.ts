import { LoginActionPayloadInterface } from "../../Interfaces/LoginActionPayload";
import {
    loginFetching, 
    loginAction, 
    loginSetReducer, 
    loginSetError, 
    loginSetAuthenticated} from "../Actions/AuthOperations";
import {LoginResponse} from "../../Interfaces/LoginResponse";
import {loginAttemptFailed} from '../Actions/UserOperations';

import API_URL from '../../API_URL.json';

import Axios from 'axios';
import { ThunkDispatch } from 'redux-thunk';

const sendLoginRequest =  async (loginModel : LoginActionPayloadInterface) : Promise<LoginResponse> => {
    const loginRequest = await Axios.post(`${API_URL.SERVER_URL}${API_URL.LOGIN}`, loginModel);

    return loginRequest.data as LoginResponse;
}

const validateLoginResponse = (loginResponse : LoginResponse) : boolean => {
        return (
            !!loginResponse  && 
            !!loginResponse.email  &&
            !!loginResponse.userId  &&
            !!loginResponse.jwt  &&
            !!loginResponse.username &&
            !!loginResponse.roles
        )
}

const loginApiRequest = (loginModel : LoginActionPayloadInterface ) => {
    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
        try{
            dispatch(loginFetching(true));
            const loginResponse = await sendLoginRequest(loginModel);
            const isValidResponse = validateLoginResponse(loginResponse);

            if(isValidResponse){
                dispatch(loginAction(loginResponse));
                dispatch(loginSetAuthenticated(true));
                dispatch(loginSetReducer(true));
                dispatch(loginAttemptFailed(false));
            }
            else throw new Error();
        }
        catch (err){

            console.log(err);
            dispatch(loginAttemptFailed(true));
            dispatch(loginSetAuthenticated(false));
            dispatch(loginSetReducer(false));
            dispatch(loginSetError({isErrorFlagSet : true}))
        }
        finally{
            dispatch(loginFetching(false));
        }

    }
}
export default loginApiRequest;


const setLocalStorageVar = (loginResonse : LoginResponse) : void => {
    setJwtToken(loginResonse.jwt);
    setUserId(loginResonse.userId);
    setUserRoles(loginResonse.roles);
    setUsername(loginResonse.username);
}

const setJwtToken = (jwtToken : string) : void => {
    localStorage.setItem('token', jwtToken);
}
const setUserId = (userId : string) : void => {
    localStorage.setItem('userId', userId)
}
const setUsername = (username : string) : void => {
    localStorage.setItem('username', username);
}
const setUserRoles = (roles : Array<string>) : void => {
    localStorage.setItem('role', JSON.stringify(roles));
}