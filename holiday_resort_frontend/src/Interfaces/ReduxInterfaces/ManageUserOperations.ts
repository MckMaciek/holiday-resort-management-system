import { ManageUserOperations } from "../../Stores/ActionTypes/ManageUsersOperations";
import { User } from "../User";

export type GetUsers = {
    type : typeof ManageUserOperations.GET_USERS,
    users : Array<User>
}

export type SetUsersFetching = {
    type : typeof ManageUserOperations.USERS_FETCHING,
    isFetching : boolean
}

export type SetUsersError = {
    type : typeof ManageUserOperations.USERS_ERROR,
    error : boolean
}

export type ManageUsersGenericAction = 
    GetUsers |
    SetUsersFetching |
    SetUsersError;