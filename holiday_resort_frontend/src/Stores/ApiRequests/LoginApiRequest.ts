import { LoginActionPayloadInterface } from "../../Interfaces/LoginActionPayload";
import {loginFetching, loginAction, loginSetReducer, loginSetError} from "../Actions/AuthOperations";
import {LoginResponse} from "../../Interfaces/LoginResponse";

import Axios from 'axios';
import { ThunkDispatch } from 'redux-thunk';

const sendLoginRequest =  async (loginModel : LoginActionPayloadInterface) : Promise<LoginResponse> => {
    const loginRequest = await Axios.post("http://localhost:8080/api/auth/sign-in", loginModel);

    return loginRequest.data as LoginResponse;
}


const loginApiRequest = (loginModel : LoginActionPayloadInterface ) => {
    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
        try{
            dispatch(loginFetching(true));
            const loginResonse = await sendLoginRequest(loginModel);
            console.log(loginResonse);
            setLocalStorageVar(loginResonse);

            dispatch(loginAction(loginResonse));
            dispatch(loginSetReducer(true));

        }
        catch (err){
            console.log("ERROR-WHILE-LOGIN");
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