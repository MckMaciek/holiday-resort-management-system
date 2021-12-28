import { ResortObjectOperationTypes } from "../../Stores/ActionTypes/ResortObjectOperationTypes";
import { ResortObjectInterface } from "../ResortObject";

export type GetAvailableResortObjects = {
    type : typeof ResortObjectOperationTypes.RESORT_OBJECT_GET,
    payload : Array<ResortObjectInterface>,
}

export type AvailableResortObjectsAreFetching = {
    type : typeof ResortObjectOperationTypes.RESORT_OBJECT_IS_FETCHING,
    isFetching : boolean,
}

export type AvailableResortObjectsError = {
    type : typeof ResortObjectOperationTypes.RESORT_OBJECT_ERROR,
    error : boolean,
}

export type AvailableResortObjectSetReserved = {
    type : typeof ResortObjectOperationTypes.RESORT_OBJECT_SET_RESERVED,
    resortObjectId : number,
    isReserved : boolean,
}


export type ResortObjectGenericAction = 
    GetAvailableResortObjects | AvailableResortObjectsAreFetching | AvailableResortObjectsError | AvailableResortObjectSetReserved;