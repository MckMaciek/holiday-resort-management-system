import {User} from "../../Interfaces/User";
import {ManageUsersGenericAction} from "../../Interfaces/ReduxInterfaces/ManageUserOperations";
import {ManageUserOperations} from "../ActionTypes/ManageUsersOperations";

import {ReservationInterface} from "../../Interfaces/Reservation"

interface INIT_STATE {

    users : Array<User>,
    isFetching : boolean,
    error : boolean,
};

const INIT_STATE_USERS : INIT_STATE = {

    users : [],
    isFetching : false,
    error : false,
}

const ManageUsersReducer = (state : INIT_STATE = INIT_STATE_USERS, action : ManageUsersGenericAction) : INIT_STATE => {

    console.log(action)
    switch(action.type){

        case ManageUserOperations.GET_USERS : {
            return {...state, users : action.users}
        }

        case ManageUserOperations.USERS_ERROR : {
            return {...state, error : action.error}
        }

        case ManageUserOperations.USERS_FETCHING : {
            return {...state, isFetching : action.isFetching}
        }

        case ManageUserOperations.RESERVATION_CHANGE_STATUS_ADM : {

            
            let userId = action.reservationChanged.userId;
            let reservationId = action.reservationChanged.reservationId;
            let status = action.reservationChanged.status;
            
            let userUpdated = state.users.filter(user => user.id === userId)[0];
            let reservationUpdated = userUpdated.reservationResponses.filter(reservation => reservation.id === reservationId)[0];
            
            let userIndex = state.users.indexOf(userUpdated);
            let reservationIndex = userUpdated.reservationResponses.indexOf(reservationUpdated);
            
            let userReservationList = [
                ...userUpdated.reservationResponses.slice(0, reservationIndex),
                {
                    ...reservationUpdated,
                    reservationStatus : status,
                    
                }, ...userUpdated.reservationResponses.slice( ++reservationIndex)];
                
            console.log(userUpdated.reservationResponses);
            console.log(userReservationList);

            return {...state, users : [
                ...state.users.slice(0, userIndex),
                {
                    ...state.users[userIndex],
                    reservationResponses : userReservationList

                }, ...state.users.slice( ++userIndex)]}
        }

        default : {
            return state;
        }
    }
}
export default ManageUsersReducer;