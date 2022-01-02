import {AccommodationRequest} from "./AccommodationRequest";
import { ExternalServiceRequest } from "./ExternalServiceRequest";
import { ReservationOwner } from "./ReservationOwner";

export interface NewReservationRequest {
    reservationId : number | null,
    reservationEndingDate : Date,
    reservationStartingDate : Date,
    reservationName : string,
    description : string,
    accommodationRequestList : Array<AccommodationRequest>,
    externalServicesRequests : Array<ExternalServiceRequest>
    reservationRemarksRequestList : Array<any>,
    reservationOwnerRequest : ReservationOwner,
}