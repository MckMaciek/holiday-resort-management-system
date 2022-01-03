import {User} from "../../Interfaces/User";
import {ManageUserOperations} from "../ActionTypes/ManageUsersOperations";
import {
    GetUsers,
    SetUsersFetching,
    SetUsersError
} from "../../Interfaces/ReduxInterfaces/ManageUserOperations";

export const setUsers = (payload : Array<User>) : GetUsers => ({
    type : ManageUserOperations.GET_USERS,
    users : payload,
})

export const setUsersFetching = (isFetching : boolean) : SetUsersFetching => ({
    type : ManageUserOperations.USERS_FETCHING,
    isFetching : isFetching
})

export const setUsersError = (error : boolean) : SetUsersError => ({
    type : ManageUserOperations.USERS_ERROR,
    error : error,
})