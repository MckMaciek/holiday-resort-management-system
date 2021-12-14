import {AccommodationInterface} from './Accommodation';

export interface ReservationInterface {
    id : number,
    reservationName : string,
    finalPrice : number,
    reservationDate : Date,
    reservationEndingDate : Date,
    reservationStatus : string,
    reservationRemarksResponse : Array<ReservationRemarksInterface>,
    accommodationResponses : Array<AccommodationInterface>,
}

export interface ReservationRemarksInterface{
    id : number,
    topic : string,
    author : string,
    description : string,
    creationDate : Date,
    modificationDate : Date,
}