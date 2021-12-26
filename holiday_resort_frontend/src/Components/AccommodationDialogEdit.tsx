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
import ButtonGroup from '@mui/material/ButtonGroup';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import {ResortObjectInterface} from '../Interfaces/ResortObject';
import {putAccommodationApi} from "../Stores/ApiRequests/ReservationApiRequest";

import {EventInterface} from "../Interfaces/Event";

import SelectAccommodationEvents from "./SelectAccommodationEvents";
import NumericTextField from "./NumericTextField";

import {getEventsForAccommodationId} from "../Stores/ApiRequests/ReservationApiRequest";

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
    propertyName : string,
    maxAmountOfPeople : number,
    closeHandler : () => void,
    onAcceptHandler : () => void,
}

type Props = MapStateToProps & MapDispatcherToProps & AccommodationDialogProps


const AccommodationDialogEdit : React.FC<Props> = ({
    isFetching,
    jwtToken,
    sendAccommodationPut,
    getResortObjectEvents,
    maxAmountOfPeople,
    
    isOpen,
    propertyId,
    resortObjectId,
    resortObjectEvents,
    propertyName,
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
    const [numberOfPeople, setNumberOfPeople] = React.useState<number>(0);

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

        if(resortObjectEvents){
            let choosenEvents_ = findEventIdsFromObjectName();
            setChosenEvents(choosenEvents_);
        }

    }, [eventType])


    useEffect(() => {

        setEventType([]);

    }, [choosenResortObjectId])
    

    const findEventIdsFromObjectName = () => {
        return resortObjectEvents.filter(ev => eventType.includes(ev.eventType));
    }  

    return(
        <>
            {isFetching && (resortObjectEvents === undefined || resortObjectEvents.length === 0) ? (
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
                maxWidth='md'
                >
                <DialogTitle> You are currently editing {propertyName}</DialogTitle>
                <DialogContent
                    style={{height:'20vh', marginTop:'3%'}}
                >
                    <SelectAccommodationEvents
                        eventType={eventType}
                        handleChangeEvent={handleChangeEvent}
                        chosenEvents={chosenEvents}
                        resortObjectEvents={resortObjectEvents}
                    />
    
                    <FormControl
                        sx={{
                            marginTop : '1%',
                            marginLeft : '2.5%',
                        }}
                        >
                        <p
                            style={{marginTop : '3%'}}
                        > 
                            Max People : {maxAmountOfPeople} in {propertyName}
                        </p>

                        <ButtonGroup
                        >  
                        
                            <Button
                                aria-label="reduce"
                                onClick={() => {
                                    setNumberOfPeople(amountOfPeople => Math.max(amountOfPeople - 1, 0))
                                }}
                            >
                                <RemoveIcon fontSize="small" />
                            </Button>
                            <Button
                                aria-label="increase"
                                onClick={() => {
                                    setNumberOfPeople(amountOfPeople => ((amountOfPeople + 1) % (maxAmountOfPeople + 1)))
                                }}
                            >
                                <AddIcon fontSize="small" />
                            </Button>

                            <span
                                style={{marginLeft : '20%'}}
                            >   
                                {numberOfPeople}  People
                            </span>

                        </ButtonGroup>

                    </FormControl> 
                
                </DialogContent>
                <DialogActions>
                    <Button onClick={closeHandler}>Cancel</Button>
                    <Button 
                    
                    disabled={!!!numberOfPeople || numberOfPeople === -1}
                    onClick={() => {
                        let putRequest : AccommodationRequest = {
                            numberOfPeople : numberOfPeople,
                            resortObjectId : resortObjectId,
                            eventRequests : chosenEvents,
                        }
                        
                        sendAccommodationPut(jwtToken, propertyId, putRequest);
                        closeHandler();
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
