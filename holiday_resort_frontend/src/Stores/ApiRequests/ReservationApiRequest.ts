import { ReservationInterface } from "../../Interfaces/Reservation";
import API_URL from '../../API_URL.json';

import {AccommodationRequest} from '../../Interfaces/AccommodationRequest';

import {
    getResortObjectEvents,
    areAvailableObjectsFetching

} from '../Actions/ResortObjectEventsOperation';

import {objectModified} from '../Actions/UserOperations';

import {
    getReservation, 
    setFetching, 
    setReservationError,
    setRemoveAccommodationFetching,

} from "../Actions/ReservationOperations";

import { ThunkDispatch } from 'redux-thunk';
import Axios from 'axios';
import { EventInterface } from "../../Interfaces/Event";


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
        }
        catch (err){
            console.log(err);
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
            const deleteAccommodationStatus = await deleteAccommodationRequest(jwtToken, accommodationId);
            if(deleteAccommodationStatus === 200){
                dispatch(objectModified(true));
                console.log("registerResponse 200");
            }

            console.log(deleteAccommodationStatus);
        }
        catch (err){
        }
        finally{
            dispatch(objectModified(false));
            setRemoveAccommodationFetching(false);
        }
        
    }
}

const putAccommodationRequest = async (jwtToken : string, accommodationId : number, accommodationRequest : AccommodationRequest) => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const putStatus = await Axios.post(
        `${API_URL.SERVER_URL}${API_URL.PUT_ACCOMMODATION}${accommodationId}`, accommodationRequest, config);
    
    return putStatus.status;
}


export const putAccommodationApi = (jwtToken : string, accommodationId : number, accommodationRequest : AccommodationRequest) => {

    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
            
        try{
            const accommodationChanged = await putAccommodationRequest(jwtToken, accommodationId, accommodationRequest);
            if(accommodationChanged === 200){
                dispatch(objectModified(true));
            }
            
            console.log(accommodationChanged);
        }
        catch (err){
        }
        finally{
            dispatch(objectModified(false));
        }
        
    }
}

const getEventsWithAccommodationIdRequest = async (jwtToken : string, accommodationId : number) : Promise<Array<EventInterface>> => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const resortObjectEvents = await Axios.get(
        `${API_URL.SERVER_URL}${API_URL.GET_USER_EVENTS}${accommodationId}/events`, config);
    
    return resortObjectEvents.data;
}


export const getEventsForAccommodationId = (jwtToken : string, accommodationId : number) => {

    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
          
        try{
            const eventsFetched = await getEventsWithAccommodationIdRequest(jwtToken, accommodationId);

            dispatch(areAvailableObjectsFetching(true));

            if(eventsFetched.length !== 0){
                dispatch(getResortObjectEvents(eventsFetched));
            }
        }
        catch (err){
        }
        finally{
            dispatch(areAvailableObjectsFetching(false));
        }
        
    }
}

const markReservationStartedRequest = async (jwtToken : string, reservationId : number)  => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const reservationChangedStatus = await Axios.get(
        `${API_URL.SERVER_URL}${API_URL.SET_RESERVATION_STARTED}${reservationId}/change-status`, config);
    
    return reservationChangedStatus.status;
}

export const markReservationStarted = async (jwtToken : string, reservationId : number) => {

    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
          
        try{
            const statusChanged = await markReservationStartedRequest(jwtToken, reservationId);
            console.log(statusChanged)

            if(statusChanged === 200){
                dispatch(objectModified(true));
            }
        }
        catch (err){
            //todo
        }
        finally{
            dispatch(objectModified(false));
        } 
    }
}
