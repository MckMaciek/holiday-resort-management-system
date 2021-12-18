import {AccommodationRequest} from "./AccommodationRequest";

export interface NewReservationRequest {
    reservationEndingDate : Date,
    reservationStartingDate : Date,
    reservationName : string,
    accommodationRequestList : Array<AccommodationRequest>,
    reservationRemarksRequestList : Array<any>,
}