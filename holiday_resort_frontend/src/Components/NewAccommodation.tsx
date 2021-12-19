import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import { borders } from '@mui/system';

import Axios from 'axios';
import API_URL from "../API_URL.json";

import SelectAccommodations from "../Components/SelectAccommodations";
import SelectAccommodationEvents from './SelectAccommodationEvents';
import {EventRequest} from '../Interfaces/EventRequest';
import {EventInterface} from '../Interfaces/Event';

import {ResortObjectInterface} from "../Interfaces/ResortObject";
import { useEffect, useState } from 'react';
import ButtonGroup from '@mui/material/ButtonGroup';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import { Dispatch, SetStateAction } from "react";

import {AccommodationRequest} from "../Interfaces/AccommodationRequest";
import {NewReservationRequest} from "../Interfaces/NewReservationRequest";


interface ComponentProps {
    isOpen : boolean,
    closeHandler : () => void,
    acceptHandler : () => void,
    modifyReservation : Dispatch<SetStateAction<NewReservationRequest>>,

    resortObjects : Array<ResortObjectInterface>,
    jwtToken : string,
}

const NewAccommodation : React.FC<ComponentProps> = ({
    isOpen,
    closeHandler,
    acceptHandler,
    modifyReservation,

    resortObjects,
    jwtToken,
}) => {

    const handleChangeEvent = (event: any) => {
        const {
          target: { value },
        } = event;

        setEventType(
          typeof value === 'string' ? value.split(',') : value, 
        );
      };

    interface ResortObjectRequest {
        id : string,
        isSent : boolean,
    }

    const RESORT_OBJECT_REQUEST_DEFAULT : ResortObjectRequest = {
        id : '',
        isSent : false,
    }

    const [resortObject, setResortObject] = useState<ResortObjectRequest>(RESORT_OBJECT_REQUEST_DEFAULT);


    const [eventType, setEventType] = useState<string[]>([]);
    const [chosenEvents, setChosenEvents] = useState<Array<EventRequest>>([{id : -1, price : -1}]);
    const [fetchedEvents, setFetchedEvents] = useState<Array<EventInterface>>([]);


    const fetchROEvents = async (resortObjectId : string) : Promise<Array<EventInterface>> => {

        const config = {
            headers: { Authorization: `Bearer ${jwtToken}` },
            'Content-Type': 'application/json',
        };
    
        const resortObjectEvents = await Axios.get(
                `${API_URL.SERVER_URL}${API_URL.GET_RESORT_OBJECT_EVENTS}${resortObjectId}/events`, config);
            
        return resortObjectEvents.data as Array<EventInterface>;
    }

    const showResortObjectDetails = (resortObjId) => {
        const resortObj : ResortObjectInterface[] = resortObjects.filter(rO => rO.id === resortObjId);
        return(
            <div>
                <p>Max People in : {resortObj[0].maxAmountOfPeople} </p>
                <p>Object Type : {resortObj[0].objectType} </p>
                <p>Price per Person : {resortObj[0].pricePerPerson} zł </p>
            </div>
        )
    }

    const resertForm = () => {
        setEventType([]);
        setChosenEvents([]);
        setNewAccommodation(NEW_SET_NEW_ACCOMMODATION_DEFAULT);
        setResortObject(RESORT_OBJECT_REQUEST_DEFAULT);
    }


    useEffect(() => {
        if(jwtToken && jwtToken !== "" && resortObject  && resortObject.id !== ''){
            (async () => {
                setEventType([]);
                setResortObject(resortObj => ({...resortObj, isSent : false}))
                const fetchedEvents =  await fetchROEvents(resortObject.id);
                setFetchedEvents(fetchedEvents);
            })()
        }
    }, [resortObject.id])


    useEffect(() => {

        if(fetchedEvents){
            let choosenEvents_ = findEventIdsFromObjectName();
            setChosenEvents(choosenEvents_);
            setNewAccommodation(accommodation => ({...accommodation, eventRequests : choosenEvents_}));
        }

    }, [eventType])


    const findEventIdsFromObjectName = () => {
        return fetchedEvents.filter(ev => eventType.includes(ev.eventType));
    }  

    const NEW_SET_NEW_ACCOMMODATION_DEFAULT : AccommodationRequest = {
        numberOfPeople : 0,
        resortObjectId : -1,
        eventRequests : [],
    }
    
    const [newAccommodation, setNewAccommodation] = useState<AccommodationRequest>(NEW_SET_NEW_ACCOMMODATION_DEFAULT);

    return(
        <Dialog
            open={isOpen}
            fullWidth
            onClose={closeHandler}
        >
            <DialogTitle> 
                <Typography
                        variant="h5"
                        style={{textAlign : 'center', marginBottom : '1%'}}
                >

                     New Accommodation 
                </Typography>
                <Divider />
            </DialogTitle>
            <DialogContent            
            >
            
            <div style={{display : 'flex', justifyContent : 'center', alignItems : 'center', flexDirection : 'column'}}>
                <SelectAccommodations                      
                    availableResortObjects={resortObjects}
                    choosenResortId={resortObject.id}
                    handleChange={(event) => {
                        setResortObject({id : event.target.value, isSent : false});
                        setNewAccommodation(accommodation => ({...accommodation, resortObjectId : parseInt(event.target.value)}));
                    }}
                />

                {fetchedEvents && resortObject.id !== '' ? (

                    <>
                        <Typography
                            variant="h5"
                            sx={{
                                fontSize : '1rem', 
                                alignSelf : 'flex-start', 
                                marginLeft : '8%', 
                                marginBottom : '4%', 
                                borderStyle: 'groove',
                                paddingLeft : '2%',
                                paddingBottom : '2%',
                                paddingTop : '2%',
                                paddingRight : '46%',
                            }}
                            textAlign={'left'}
                        >
                            {showResortObjectDetails(resortObject.id)}
                        </Typography>

                        <Typography
                            sx={{fontSize : '1rem'}}
                            textAlign='center'
                        >
                            <p> Number of people :{newAccommodation.numberOfPeople} </p>
                        </Typography>

                        <ButtonGroup>
                            <Button
                                aria-label="reduce"
                                onClick={() => {
                                    setNewAccommodation(accommodation => 
                                        ({...accommodation, numberOfPeople : Math.max(accommodation.numberOfPeople - 1, 0) }));
                                }}
                            >
                                <RemoveIcon fontSize="small" />
                            </Button>
                            <Button
                                aria-label="increase"
                                onClick={() => {
                                    setNewAccommodation(accommodation => 
                                        ({...accommodation, numberOfPeople : accommodation.numberOfPeople + 1}));
                                }}
                            >
                                <AddIcon fontSize="small" />
                            </Button>
                        </ButtonGroup>
    
                        <SelectAccommodationEvents
                            eventType={eventType}
                            handleChangeEvent={handleChangeEvent}
                            chosenEvents={chosenEvents}
                            resortObjectEvents={fetchedEvents}
                        />
                    </>
                ) : null}               
            </div>

            </DialogContent>
            
            <DialogActions>
                <Button onClick={() => {
                    resertForm();
                    closeHandler();
                }}> Cancel </Button>
                <Button onClick={() => {
                    modifyReservation(reservation => 
                        ({...reservation, accommodationRequestList : [...reservation.accommodationRequestList, newAccommodation]}));
                    resertForm();
                    acceptHandler();
                }} autoFocus>
                    submit
                </Button>
        </DialogActions>
        </Dialog>
    );

}

export default NewAccommodation;