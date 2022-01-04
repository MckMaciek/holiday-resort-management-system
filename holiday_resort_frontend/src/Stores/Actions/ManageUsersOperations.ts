import {User} from "../../Interfaces/User";
import {ManageUserOperations} from "../ActionTypes/ManageUsersOperations";
import {
    GetUsers,
    SetUsersFetching,
    SetUsersError,
    ManageUsersStatusChange
} from "../../Interfaces/ReduxInterfaces/ManageUserOperations";

import {ReservationStatus} from "../../Enums/SetReservationStatus";

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

export const setUserReservationStatus = (reservationId : number, userId : number, status : ReservationStatus) : ManageUsersStatusChange => ({
    type : ManageUserOperations.RESERVATION_CHANGE_STATUS_ADM,
    reservationChanged : {
        userId : userId,
        reservationId : reservationId,
        status : status,
    }
})