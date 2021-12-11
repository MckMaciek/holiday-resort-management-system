import {EventInterface} from "../../Interfaces/Event";
import {ResortObjectEventsGenericAction} from "../../Interfaces/ReduxInterfaces/ResortObjectEventsOperations";

import {ResortObjectEventsOperations} from "../ActionTypes/ResortObjectEventsTypes";

interface INIT_STATE {

    events : Array<EventInterface>,
    isFetching : boolean,
};

const INIT_STATE_EVENTS : INIT_STATE = {

    events : [],
    isFetching : false,
}


const ResortObjectEventsReducer = (state : INIT_STATE = INIT_STATE_EVENTS, action : ResortObjectEventsGenericAction ) : INIT_STATE => {

    switch(action.type){

        case ResortObjectEventsOperations.RESORT_OBJECT_EVENTS_GET : {
            return {...state, events : action.events}
        }

        case ResortObjectEventsOperations.RESORT_OBJECT_EVENTS_IS_FETCHING : {
            return {...state, isFetching : action.isFetching}
        }

        default : {
            return state;
        }
    }
}
export default ResortObjectEventsReducer;