import { ReservationInterface } from "../../Interfaces/Reservation";
import API_URL from '../../API_URL.json';

import {
    getReservation, 
    setFetching, 
    setReservationError,
    setRemoveAccommodationFetching,

} from "../Actions/ReservationOperations";

import { ThunkDispatch } from 'redux-thunk';
import Axios from 'axios';


const getUserReservationsRequest = async (jwtToken : string) : Promise<Array<ReservationInterface>> => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const reservationList = await Axios.get(
            `${API_URL.SERVER_URL}${API_URL.USER_RESERVATIONS}`, config);

    return reservationList.data as Array<ReservationInterface>;
}


export const getReservations = (jwtToken : string) => {
    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {

        try{
            dispatch(setFetching(true));
            const registerResponse = await getUserReservationsRequest(jwtToken);
            dispatch(getReservation(registerResponse));
            dispatch(setReservationError(false));
            console.log(registerResponse);
        }
        catch (err){
            dispatch(setReservationError(true));
        }
        finally{
            dispatch(setFetching(false));
        }
    }
}


const deleteAccommodationRequest = async (jwtToken : string, accommodationId : number) => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const deleteStatus = await Axios.delete(
        `${API_URL.SERVER_URL}${API_URL.ACCOMMODATION_REMOVE}${accommodationId}`, config);
    
    return deleteStatus.status;
}

export const deleteAccommodationApi = (jwtToken : string, accommodationId : number) => {

    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
            
        try{
            dispatch(setRemoveAccommodationFetching(true));
            const registerResponse = await deleteAccommodationRequest(jwtToken, accommodationId);

            console.log(registerResponse);
        }
        catch (err){
        }
        finally{
            setRemoveAccommodationFetching(false);
        }
        
    }
}
