import {AccommodationRequest} from "./AccommodationRequest";
import { ExternalServiceRequest } from "./ExternalServiceRequest";

export interface NewReservationRequest {
    reservationEndingDate : number,
    reservationStartingDate : number,
    reservationName : string,
    accommodationRequestList : Array<AccommodationRequest>,
    externalServicesRequests : Array<ExternalServiceRequest>
    reservationRemarksRequestList : Array<any>,
}