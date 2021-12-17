import { LoginActionPayloadInterface } from "../../Interfaces/ReduxInterfaces/LoginActionPayload";
import {
    loginFetching, 
    loginAction, 
    loginSetReducer, 
    loginSetError, 
    loginSetAuthenticated} from "../Actions/AuthOperations";

import {registerSetDetails} from "../Actions/AuthOperations";
import {LoginResponse} from "../../Interfaces/LoginResponse";
import { UserInfoResponse } from "../../Interfaces/UserInfoResponse";
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

export const loginApiRequest = (loginModel : LoginActionPayloadInterface ) => {
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

const sendUserInfoRequest =  async (jwtToken : string) : Promise<UserInfoResponse> => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const userInfoResponse = await Axios.get(`${API_URL.SERVER_URL}${API_URL.GET_USER_INFO}`, config);
    return userInfoResponse.data as UserInfoResponse;
}


export const getUserInfo = (jwtToken : string) => {
    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {

        try{
            const userInfo = await sendUserInfoRequest(jwtToken);
            dispatch(registerSetDetails(userInfo));

        }catch(err){
            console.log(err);
        }
        finally{


        }

    }
}
