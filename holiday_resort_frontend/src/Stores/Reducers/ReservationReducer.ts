import {ReservationInterface} from "../../Interfaces/Reservation";
import {ReservationGenericAction} from "../../Interfaces/ReduxInterfaces/ReservationOperations";

import {ReservationOperationTypes} from '../ActionTypes/ReservationOperationTypes';

interface INIT_STATE {

    reservation  : Array<ReservationInterface>,
    isFetching : boolean,
    error : boolean,

    deleteFetching : boolean,
};

const INIT_STATE_RESERVATION : INIT_STATE = {

    reservation : [],
    isFetching : false,
    error : false,

    deleteFetching : false,
}


const ReservationReducer = (state : INIT_STATE = INIT_STATE_RESERVATION, action : ReservationGenericAction) : INIT_STATE => {

    switch(action.type){

        case ReservationOperationTypes.RESERVATION_FETCH : {
            return {...state, reservation : action.payload}
        }

        case ReservationOperationTypes.RESERVATION_IS_FETCHING : {
            return {...state, isFetching : action.isFetching}
        }

        case ReservationOperationTypes.RESERVATION_FETCH_ERROR : {
            return {...state, error : action.error}
        }

        case ReservationOperationTypes.RESERVATION_CHANGE_STATUS : {

            console.log("WZEDLEMTEZ")
            let reservationUpdated = state.reservation.filter(reservation => reservation.id === action.reservationChanged.id);
            let index = state.reservation.indexOf(reservationUpdated[0]);
            
            return {...state, reservation : [
                ...state.reservation.slice(0, index),
                {
                    ...state.reservation[index],
                    reservationStatus : action.reservationChanged.status,

                }, ...state.reservation.slice(++index)]
            }
        }

        case ReservationOperationTypes.ACCOMMODATION_DELETE_FETCHING : {
            return {...state, deleteFetching : action.isFetching}
        }

        default : {
            return state;
        }
    }
}
export default ReservationReducer;