import { ReservationInterface } from "../../Interfaces/Reservation";
import API_URL from '../../API_URL.json';

import {AccommodationRequest} from '../../Interfaces/AccommodationRequest';
import {loginSetTokenExpired} from '../Actions/AuthOperations';
import {
    getResortObjectEvents,
    areAvailableObjectsFetching

} from '../Actions/ResortObjectEventsOperation';

import {ReservationStatus} from "../../Enums/SetReservationStatus";

import {objectModified} from '../Actions/UserOperations';

import {
    getReservation, 
    setFetching, 
    setReservationError,
    setRemoveAccommodationFetching,
    changeReservationStatus

} from "../Actions/ReservationOperations";
import {NewReservationRequest} from '../../Interfaces/NewReservationRequest';

import { ThunkDispatch } from 'redux-thunk';
import Axios from 'axios';
import { EventInterface } from "../../Interfaces/Event";

import {setUserReservationStatus} from "../Actions/ManageUsersOperations";


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


const postReservationApiRequest = async (jwtToken : string, reservationRequest : NewReservationRequest) => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const post = await Axios.post(
        `${API_URL.SERVER_URL}${API_URL.POST_RESERVATION}`, reservationRequest, config);
    
    return post.status;
}

export const postReservation = (jwtToken : string, reservationRequest : NewReservationRequest) => {

    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
            
        try{
            const reservationStatus = await postReservationApiRequest(jwtToken, reservationRequest);
            if(reservationStatus === 200){
                dispatch(objectModified(true));
                console.log("sukces");
                console.log(reservationStatus);
            }

        }
        catch (err){
            console.log(err);
        }
        finally{
            dispatch(objectModified(false));
        }
        
    }
}

const patchReservationApiRequest = async (jwtToken : string, reservationRequest : NewReservationRequest) => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const post = await Axios.patch(
        `${API_URL.SERVER_URL}${API_URL.PATCH_RESERVATION}/${reservationRequest.reservationId}`, reservationRequest, config);
    
    return post.status;
}

export const patchReservation = (jwtToken : string, reservationRequest : NewReservationRequest) => {

    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
            
        try{
            const reservationStatus = await patchReservationApiRequest(jwtToken, reservationRequest);
            if(reservationStatus === 200){
                dispatch(objectModified(true));
            }
            else if(reservationStatus === 411){
                dispatch(loginSetTokenExpired(true));
            }

        }
        catch (err){
            console.log(err);
        }
        finally{
            dispatch(objectModified(false));
        }
    }
}


const deleteReservationRequest = async (jwtToken : string, reservationId : number) => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const deleteStatus = await Axios.delete(
        `${API_URL.SERVER_URL}${API_URL.DELETE_RESERVATION}/${reservationId}`, config);
    
    return deleteStatus.status;
}

export const deleteReservationApi = (jwtToken : string, reservationId : number) => {

    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
            
        try{
            const deleteAccommodationStatus = await deleteReservationRequest(jwtToken, reservationId);
            if(deleteAccommodationStatus === 200){
                dispatch(objectModified(true));
            }
            else if(deleteAccommodationStatus === 411){
                dispatch(loginSetTokenExpired(true));
            }

        }
        catch (err){
        }
        finally{
            dispatch(objectModified(false));
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

export const markReservationStarted =  (jwtToken : string, reservationId : number) => {

    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
          
        try{
            const statusChanged = await markReservationStartedRequest(jwtToken, reservationId);
            console.log(statusChanged)

            if(statusChanged === 200){
                dispatch(objectModified(true));
            }
        }
        catch (err){
        }
        finally{
            dispatch(objectModified(false));
        } 
    }
}

const changeReservationStatusApi = async (jwtToken : string, reservationId : number,  status : ReservationStatus)  => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const reservationChangedStatus = await Axios.get(
        `${API_URL.SERVER_URL}${API_URL.CHANGE_RESERVATION_STATUS}${reservationId}/change-status-adm?status=${status.toString()}`, config);
        console.log(`${API_URL.SERVER_URL}${API_URL.CHANGE_RESERVATION_STATUS}${reservationId}/change-status-adm`);
    
    return reservationChangedStatus.status;
}

export const ChangeReservationStatus =  (jwtToken : string, reservationId : number, userId : number,  status : ReservationStatus) => {

    return async (dispatch : ThunkDispatch<{}, {}, any> ) => {
          
        try{
            const statusChanged = await changeReservationStatusApi(jwtToken, reservationId, status);
            dispatch(setUserReservationStatus(reservationId, userId, status));
            //dispatch(changeReservationStatus(reservationId, status));
            dispatch(objectModified(true));
            
        }
        catch (err){
            //todo
        }
        finally{
            dispatch(objectModified(false));
        } 
    }
}
