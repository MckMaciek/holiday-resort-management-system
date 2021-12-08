import { ResortObjectInterface } from "../../Interfaces/ResortObject"

import { ResortObjectOperationTypes } from "../ActionTypes/ResortObjectOperationTypes"
import {
    GetAvailableResortObjects,
    AvailableResortObjectsAreFetching,
    AvailableResortObjectsError,

} from '../../Interfaces/ReduxInterfaces/ResortObjectOperations';


export const getAvailableResortObjects = (resortObjectList : Array<ResortObjectInterface>) : GetAvailableResortObjects => ({
    type : ResortObjectOperationTypes.RESORT_OBJECT_GET,
    payload : resortObjectList,
})

export const areAvailableObjectsFetching = (isFetching : boolean) : AvailableResortObjectsAreFetching => ({
    type : ResortObjectOperationTypes.RESORT_OBJECT_IS_FETCHING,
    isFetching : isFetching,
})

export const errorAvailableObjects = (error : boolean) : AvailableResortObjectsError => ({
    type : ResortObjectOperationTypes.RESORT_OBJECT_ERROR,
    error : error,
})