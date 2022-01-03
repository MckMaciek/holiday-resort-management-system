import API_URL from '../../API_URL.json';

import {
    setUsers,
    setUsersFetching,
    setUsersError
} from "../Actions/ManageUsersOperations";

import { ThunkDispatch } from 'redux-thunk';
import Axios from 'axios';
import { User } from "../../Interfaces/User";


const getUsers = async (jwtToken : string) : Promise<Array<User>> => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const userList = await Axios.get(
            `${API_URL.SERVER_URL}${API_URL.GET_USERS}`, config);

    return userList.data as Array<User>;
}


export const GetAllUsers = (jwtToken : string) => {
    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {

        try{
            dispatch(setUsersFetching(true));
            const userList = await getUsers(jwtToken);

            dispatch(setUsers(userList));
        }
        catch (err){
            dispatch(setUsersError(true));
        }
        finally{
            dispatch(setUsersFetching(false));
        }
    }
}