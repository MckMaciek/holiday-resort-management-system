import {AccommodationInterface} from './Accommodation';
import {ExternalService} from './ExternalService';

export interface ReservationInterface {
    id : number,
    reservationName : string,
    finalPrice : number,
    reservationDate : Date,
    reservationEndingDate : Date,
    reservationStatus : string,
    reservationRemarksResponse : Array<ReservationRemarksInterface>,
    accommodationResponses : Array<AccommodationInterface>,
    externalServiceResponses : Array<ExternalService>,
}

export interface ReservationRemarksInterface{
    id : number,
    topic : string,
    author : string,
    description : string,
    creationDate : Date,
    modificationDate : Date,
}