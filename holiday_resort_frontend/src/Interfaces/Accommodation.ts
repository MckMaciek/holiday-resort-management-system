import {ResortObjectInterface} from './ResortObject';
import {EventInterface} from "./Event";

export interface AccommodationInterface{
    id : number,
    numberOfPeople : number,
    eventResponseList : Array<EventInterface>,

    resortObject : ResortObjectInterface,
}