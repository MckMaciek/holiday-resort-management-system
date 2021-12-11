import { EventInterface } from "../../Interfaces/Event";
import {
    GetResortObjectEvents,
    ResortObjectEventsIsFetching,

} from "../../Interfaces/ReduxInterfaces/ResortObjectEventsOperations";

import {ResortObjectEventsOperations} from "../ActionTypes/ResortObjectEventsTypes";



export const getResortObjectEvents = (events : Array<EventInterface>) : GetResortObjectEvents => ({
    type : ResortObjectEventsOperations.RESORT_OBJECT_EVENTS_GET,
    events : events,
})

export const areAvailableObjectsFetching = (isFetching : boolean) : ResortObjectEventsIsFetching => ({
    type : ResortObjectEventsOperations.RESORT_OBJECT_EVENTS_IS_FETCHING,
    isFetching : isFetching,
})
