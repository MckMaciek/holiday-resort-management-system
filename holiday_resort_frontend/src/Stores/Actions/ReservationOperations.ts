import { ReservationInterface } from '../../Interfaces/Reservation';
import {ReservationOperationTypes} from '../ActionTypes/ReservationOperationTypes';
import {ReservationStatus} from "../../Enums/SetReservationStatus";

import {
    FetchReservationInterface,
    IsFetchingReservationInterface,
    ErrorWhileFetchingReservation,
    DeleteAccommodation,
    DeleteAccommodationFetching,
    ReservationStatusChange
}
from '../../Interfaces/ReduxInterfaces/ReservationOperations';

export const getReservation = (payload : Array<ReservationInterface>) : FetchReservationInterface => ({
    type : ReservationOperationTypes.RESERVATION_FETCH,
    payload : payload,
})

export const setFetching = (isFetching : boolean) : IsFetchingReservationInterface => ({
    type : ReservationOperationTypes.RESERVATION_IS_FETCHING,
    isFetching : isFetching
})

export const setReservationError = (error : boolean) : ErrorWhileFetchingReservation => ({
    type : ReservationOperationTypes.RESERVATION_FETCH_ERROR,
    error : error,
})

export const changeReservationStatus = (reservationId : number, status : ReservationStatus) : ReservationStatusChange => ({
    type : ReservationOperationTypes.RESERVATION_CHANGE_STATUS,
    reservationChanged : {
        id : reservationId,
        status : status
    }
})

// ACCOMMODATION PART

export const setRemoveAccommodationFetching = (isFetching : boolean) : DeleteAccommodationFetching => ({
    type : ReservationOperationTypes.ACCOMMODATION_DELETE_FETCHING,
    isFetching : isFetching,
})