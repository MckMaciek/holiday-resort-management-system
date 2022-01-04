import {ReservationOperationTypes} from '../../Stores/ActionTypes/ReservationOperationTypes';
import { ReservationInterface } from '../Reservation';
import {ReservationStatus} from "../../Enums/SetReservationStatus";

export type FetchReservationInterface =  {
    type : typeof ReservationOperationTypes.RESERVATION_FETCH,
    payload : Array<ReservationInterface>,
}

export type IsFetchingReservationInterface = {
    type : typeof ReservationOperationTypes.RESERVATION_IS_FETCHING,
    isFetching : boolean,
}

export type ErrorWhileFetchingReservation = {
    type : typeof ReservationOperationTypes.RESERVATION_FETCH_ERROR,
    error : boolean,
}

export type DeleteAccommodation = {
    type : typeof ReservationOperationTypes.ACCOMMODATION_DELETE,
    accommodationId : number,
}

export type DeleteAccommodationFetching = {
    type : typeof ReservationOperationTypes.ACCOMMODATION_DELETE_FETCHING,
    isFetching : boolean,
}

export type ReservationStatusChange = {
    type : typeof ReservationOperationTypes.RESERVATION_CHANGE_STATUS,
    reservationChanged : {
        id : number,
        status : ReservationStatus
    }
}


export type ReservationGenericAction = 
    FetchReservationInterface | IsFetchingReservationInterface | ErrorWhileFetchingReservation
    | DeleteAccommodation | DeleteAccommodationFetching | ReservationStatusChange;