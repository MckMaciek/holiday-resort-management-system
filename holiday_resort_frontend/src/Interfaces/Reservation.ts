import {AccommodationInterface} from './Accommodation';

export interface ReservationInterface {
    id : number | null,
    finalPrice : number,
    reservationDate : Date,
    reservationStatus : string,
    reservationRemarksResponse : Array<ReservationRemarksInterface>,
    accommodationResponses : Array<AccommodationInterface>,
}

export interface ReservationRemarksInterface{
    id : number,
    topic : string,
    description : string,
    creationDate : Date,
    modificationDate : Date,
}