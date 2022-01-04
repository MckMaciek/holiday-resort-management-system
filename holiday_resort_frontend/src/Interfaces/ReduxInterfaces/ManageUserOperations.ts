import { ManageUserOperations } from "../../Stores/ActionTypes/ManageUsersOperations";
import { User } from "../User";

import {ReservationStatus} from "../../Enums/SetReservationStatus";

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

export type ManageUsersStatusChange = {
    type : typeof ManageUserOperations.RESERVATION_CHANGE_STATUS_ADM,
    reservationChanged : {
        userId : number,
        reservationId : number,
        status : ReservationStatus
    }
}

export type ManageUsersGenericAction = 
    GetUsers |
    SetUsersFetching |
    SetUsersError | 
    ManageUsersStatusChange;