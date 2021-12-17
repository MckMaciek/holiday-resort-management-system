import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';

import Axios from 'axios';
import API_URL from "../API_URL.json";

import SelectAccommodations from "../Components/SelectAccommodations";
import SelectAccommodationEvents from './SelectAccommodationEvents';
import {EventRequest} from '../Interfaces/EventRequest';
import {EventInterface} from '../Interfaces/Event';

import {ResortObjectInterface} from "../Interfaces/ResortObject";
import { useEffect, useState } from 'react';

interface ComponentProps {
    isOpen : boolean,
    closeHandler : () => void,
    acceptHandler : () => void,

    resortObjects : Array<ResortObjectInterface>,
    jwtToken : string,
}

const NewAccommodation : React.FC<ComponentProps> = ({
    isOpen,
    closeHandler,
    acceptHandler,

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
        }

    }, [eventType])


    const findEventIdsFromObjectName = () => {
        return fetchedEvents.filter(ev => eventType.includes(ev.eventType));
    }  

    return(
        <Dialog
            open={isOpen}
            fullWidth
            onClose={closeHandler}
        >
            <DialogTitle> 
                <Typography
                        variant="h5"
                        style={{textAlign : 'center', marginBottom : '2%'}}
                >

                     New Accommodation 
                </Typography>
                <Divider />
            </DialogTitle>
            <DialogContent            
            >
                
            <SelectAccommodations                      
                availableResortObjects={resortObjects}
                choosenResortId={resortObject.id}
                handleChange={(event) => setResortObject({id : event.target.value, isSent : false})}
            />

            {fetchedEvents && resortObject.id !== '' ? (
                <SelectAccommodationEvents
                    eventType={eventType}
                    handleChangeEvent={handleChangeEvent}
                    chosenEvents={chosenEvents}
                    resortObjectEvents={fetchedEvents}
                />
            ) : null}

            </DialogContent>
            
            <DialogActions>
                <Button onClick={closeHandler}> cancel </Button>
                <Button onClick={acceptHandler} autoFocus>
                    submit
                </Button>
        </DialogActions>
        </Dialog>
    );

}

export default NewAccommodation;