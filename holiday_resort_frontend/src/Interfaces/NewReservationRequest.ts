import {AccommodationRequest} from "./AccommodationRequest";
import { ExternalServiceRequest } from "./ExternalServiceRequest";
import { ReservationOwner } from "./ReservationOwner";

export interface NewReservationRequest {
    reservationId : number | null,
    reservationEndingDate : number,
    reservationStartingDate : number,
    reservationName : string,
    accommodationRequestList : Array<AccommodationRequest>,
    externalServicesRequests : Array<ExternalServiceRequest>
    reservationRemarksRequestList : Array<any>,
    reservationOwnerRequest : ReservationOwner,
}