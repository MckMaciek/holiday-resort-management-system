import { ResortObjectInterface } from "../../Interfaces/ResortObject";

import {ResortObjectGenericAction} from '../../Interfaces/ReduxInterfaces/ResortObjectOperations';
import {ResortObjectOperationTypes} from '../ActionTypes/ResortObjectOperationTypes';

interface INIT_STATE {

    availableResortObjects  : Array<ResortObjectInterface>,

    isFetching : boolean,
    error : boolean,
};

const INIT_STATE_RESORT_OBJ : INIT_STATE = {

    availableResortObjects : [],

    isFetching : false,
    error : false,
}


const ResortObjectsReducer = (state : INIT_STATE = INIT_STATE_RESORT_OBJ, action : ResortObjectGenericAction) : INIT_STATE => {

    switch(action.type){

        case ResortObjectOperationTypes.RESORT_OBJECT_GET : {
            return {...state, availableResortObjects : action.payload}
        }

        case ResortObjectOperationTypes.RESORT_OBJECT_IS_FETCHING : {
            return {...state, isFetching : action.isFetching}
        }

        case ResortObjectOperationTypes.RESORT_OBJECT_ERROR : {
            return {...state, error : action.error}
        }

        default : {
            return state;
        }
    }
}
export default ResortObjectsReducer;