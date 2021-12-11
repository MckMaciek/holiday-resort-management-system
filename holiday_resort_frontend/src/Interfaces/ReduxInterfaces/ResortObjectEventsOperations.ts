import {ResortObjectEventsOperations} from "../../Stores/ActionTypes/ResortObjectEventsTypes";
import { EventInterface } from "../Event";


export type GetResortObjectEvents = {
    type : typeof ResortObjectEventsOperations.RESORT_OBJECT_EVENTS_GET,
    events : Array<EventInterface>,
}

export type ResortObjectEventsIsFetching = {
    type : typeof ResortObjectEventsOperations.RESORT_OBJECT_EVENTS_IS_FETCHING,
    isFetching : boolean,
}


export type ResortObjectEventsGenericAction = 
GetResortObjectEvents | ResortObjectEventsIsFetching;