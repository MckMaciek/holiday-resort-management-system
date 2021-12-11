import {EventRequest} from './EventRequest';

export interface AccommodationRequest {
    numberOfPeople : number,
    resortObjectId : number,
    eventRequests : Array<EventRequest>,

}