import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import CircularProgress from '@mui/material/CircularProgress';
import { pink } from '@mui/material/colors';
import { SelectChangeEvent } from '@mui/material/Select';
import FormControl from '@mui/material/FormControl';


import { AccommodationRequest } from '../Interfaces/AccommodationRequest';
import {EventRequest} from '../Interfaces/EventRequest';

import { ThunkDispatch } from 'redux-thunk';
import { connect } from 'react-redux';

import {ResortObjectInterface} from '../Interfaces/ResortObject';
import {putAccommodationApi} from "../Stores/ApiRequests/ReservationApiRequest";

import {EventInterface} from "../Interfaces/Event";

import SelectAccommodationEvents from "./SelectAccommodationEvents";
import NumericTextField from "./NumericTextField";

import {getEventsForAccommodationId} from "../Stores/ApiRequests/ReservationApiRequest";

import {
    getAvailableResortObjectsApi
} from '../Stores/ApiRequests/ResortObjectApiRequest';
import { useEffect } from 'react';

interface MapDispatcherToProps {
    sendAccommodationPut : (jwtToken : string, accommodationId : number, accommodationRequest : AccommodationRequest) => void,
    getResortObjectEvents : (jwtToken : string, accommodationId : number) => void,
}

interface MapStateToProps {
    isFetching : boolean,
    jwtToken : string,

    resortObjectEvents : Array<EventInterface>,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    sendAccommodationPut : (jwtToken : string, accommodationId : number, accommodationRequest : AccommodationRequest) => dispatch(putAccommodationApi(jwtToken, accommodationId, accommodationRequest)),
    getResortObjectEvents : (jwtToken : string, accommodationId : number) => dispatch(getEventsForAccommodationId(jwtToken, accommodationId)),
});

const mapStateToProps = (state : any, accommodationProps : AccommodationDialogProps) : MapStateToProps => ({
    isFetching : state.ResortObjectsReducer.isFetching,
    jwtToken : state.LoginReducer.jwt,

    resortObjectEvents : state.ResortObjectEventsReducer.events,
});

interface AccommodationDialogProps{
    isOpen : boolean,
    propertyId : number,
    resortObjectId : number,
    closeHandler : () => void,
    onAcceptHandler : () => void,
}

type Props = MapStateToProps & MapDispatcherToProps & AccommodationDialogProps


const AccommodationDialogEdit : React.FC<Props> = ({
    isFetching,
    jwtToken,
    sendAccommodationPut,
    getResortObjectEvents,
    
    isOpen,
    propertyId,
    resortObjectId,
    resortObjectEvents,
    closeHandler,
    onAcceptHandler,
}) => {

    const [choosenResortObjectId, setChoosenResortObjectId] = React.useState('');    
    const handleChange = (event: SelectChangeEvent) => setChoosenResortObjectId(event.target.value as string);
    const [eventType, setEventType] = React.useState<string[]>([]);
    const [chosenEvents, setChosenEvents] = React.useState<Array<EventRequest>>([{
        id : -1,
        price : -1,
    }])
    const [numberOfPeople, setNumberOfPeople] = React.useState<number>(-1);

    const handleChangeEvent = (event: any) => {
        const {
          target: { value },
        } = event;

        setEventType(
          typeof value === 'string' ? value.split(',') : value, 
        );
      };

    useEffect(() => {
        if(jwtToken && jwtToken !== ""){
            getResortObjectEvents(jwtToken, propertyId);
        }

    }, [propertyId])


    useEffect(() => {
        let choosenEvents_ = findEventIdsFromObjectName();
        setChosenEvents(choosenEvents_);

    }, [eventType])


    useEffect(() => {

        setEventType([]);

    }, [choosenResortObjectId])
    

    const findEventIdsFromObjectName = () => {
        return resortObjectEvents.filter(ev => eventType.includes(ev.eventType));
    }  

    return(
        <>
            {isFetching && resortObjectEvents.length !== 0 ? (
                <CircularProgress 
                    size='7vh'
                    sx={{
                        color: pink[800],
                        '&.Mui-checked': {
                            color: pink[600],
                        },
                        }}
                />) : (

                <Dialog 
                open={isOpen} 
                onClose={closeHandler}
                fullWidth
                maxWidth='lg'
                >
                <DialogTitle> Accommodation with id {propertyId}</DialogTitle>
                <DialogContent
                    style={{height:'30vh'}}
                >
                    <DialogContentText
                    sx={{
                        marginBottom : '1%',
                    }}
                    >
                        Edit
                    </DialogContentText>

                    <SelectAccommodationEvents
                        eventType={eventType}
                        handleChangeEvent={handleChangeEvent}
                        chosenEvents={chosenEvents}
                        choosenResortObjectId={resortObjectId}
                        resortObjectEvents={resortObjectEvents}
                    />
    
                    <FormControl
                        sx={{
                            marginTop : '3%',
                        }}
                        >
                        <NumericTextField
                            id = "filled-search"
                            label = "Number of people"
                            type = "search"
                            variant = "filled"
                            onChange={(event) => setNumberOfPeople(parseInt(event.target.value))}
                        />
                    </FormControl> 
                
                </DialogContent>
                <DialogActions>
                    <Button onClick={closeHandler}>Cancel</Button>
                    <Button onClick={() => {

                        let putRequest : AccommodationRequest = {
                            numberOfPeople : numberOfPeople,
                            resortObjectId : resortObjectId,
                            eventRequests : chosenEvents,
                        }
                        
                        {console.log(putRequest)}

                        sendAccommodationPut(jwtToken, propertyId, putRequest);
                        onAcceptHandler();
                    }}>Approve</Button>
                </DialogActions>
            </Dialog> 
            )}
        </>
    );
}

export default connect<MapStateToProps, MapDispatcherToProps, AccommodationDialogProps>(
    mapStateToProps,
    mapDispatchToProps
  )(React.memo(AccommodationDialogEdit))
