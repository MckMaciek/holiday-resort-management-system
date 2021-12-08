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
            console.log(action.payload);
            return {...state, reservation : action.payload}
        }

        case ReservationOperationTypes.RESERVATION_IS_FETCHING : {
            return {...state, isFetching : action.isFetching}
        }

        case ReservationOperationTypes.RESERVATION_FETCH_ERROR : {
            return {...state, error : action.error}
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