import {AccommodationInterface} from './Accommodation';
import {ExternalService} from './ExternalService';
import {ReservationOwner} from './ReservationOwner';

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
    reservationOwnerRequest : ReservationOwner,
}

export interface ReservationRemarksInterface{
    id : number,
    topic : string,
    author : string,
    description : string,
    creationDate : Date,
    modificationDate : Date,
}