import { ResortObjectInterface } from "../../Interfaces/ResortObject";
import API_URL from '../../API_URL.json';

import {
    getAvailableResortObjects,
    areAvailableObjectsFetching,
    errorAvailableObjects,

} from '../Actions/ResortObjectOperations';

import { ThunkDispatch } from 'redux-thunk';
import Axios from 'axios';


const getAvailableResortObjectsRequest = async (jwtToken : string) : Promise<Array<ResortObjectInterface>> => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const availableResortObjects = await Axios.get(
            `${API_URL.SERVER_URL}${API_URL.GET_AVAILABLE_RESORT_OBJECTS}`, config);

    return availableResortObjects.data as Array<ResortObjectInterface>;
}


export const getAvailableResortObjectsApi = (jwtToken : string) => {
    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {

        try{
            dispatch(areAvailableObjectsFetching(true));
            const availableResortObjects = await getAvailableResortObjectsRequest(jwtToken);
            if(availableResortObjects.length !== 0){
                dispatch(getAvailableResortObjects(availableResortObjects));
            }
        }
        catch (err){
            dispatch(errorAvailableObjects(true));
        }
        finally{
            dispatch(areAvailableObjectsFetching(false));
        }
    }
}