import * as React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import QuestionMarkIcon from '@mui/icons-material/QuestionMark';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import BungalowIcon from '@mui/icons-material/Bungalow';
import HomeIcon from '@mui/icons-material/Home';

import {ReservationInterface} from '../Interfaces/Reservation';

import DialogConfirm from "./DialogConfirm";

interface IProps {
    reservation : ReservationInterface,
    sendDialog : {isSet : boolean}
    
    sendDialogCloseHandler : () => void,
    sendDialogAcceptHandler : () => void,
}


const ListComponent : React.FC<IProps> = ({
    reservation,
    sendDialog,

    sendDialogCloseHandler,
    sendDialogAcceptHandler,
}) => {


  const setIconType = (objectType : string) => {
      if(objectType === "Dom") return <HomeIcon />
      else if(objectType === "Namiot") return <BungalowIcon/>
      else return <QuestionMarkIcon/>
  }

    return(
        <List
        sx={{
            width : '40vw',
        }}
        >
            {reservation.accommodationResponses.map((accommodation) => (
                <>
                <ListItem>
                <ListItemAvatar>
                    <Avatar>
                      {setIconType(accommodation.resortObject.objectType)}
                    </Avatar>
                </ListItemAvatar>
                <ListItemText
                    primary={`Object Name : ${accommodation.resortObject.objectType}`}
                    secondary={
                      <div>
                        <p> Amount of people : {accommodation.numberOfPeople} </p>
                        <p> Object Name : {accommodation.resortObject.objectName} </p>
                        <p> Price per person : {accommodation.resortObject.pricePerPerson} </p>
                      </div>
                    }
                />
                </ListItem>
                <Accordion>
                <AccordionSummary
                  expandIcon={<ExpandMoreIcon />}
                >
                  <Typography sx={{ width: '33%', flexShrink: 0 }}>
                    Additional services
                  </Typography>

                  <DialogConfirm
                    isOpen={sendDialog.isSet}
                    closeHandler={sendDialogCloseHandler}
                    onAcceptHandler={sendDialogAcceptHandler}
                    dialogTitle={"Are you sure?"}
                    dialogDescription={`By clicking send The reservation ${reservation.reservationName} 
                    will change status from ${reservation.reservationStatus} to in progress and you won't be able to edit it`}
                    disagreeText={"Take me back"}
                    agreeText={"Send"}
                />

                </AccordionSummary>
                <AccordionDetails>
                {accommodation.eventResponseList.length !== 0 ? (
                  accommodation.eventResponseList.map(eventResponse => (
                    <p> {eventResponse.eventType.toLowerCase()} - {eventResponse.price} zł </p>
                    ))
                ) : (
                  <p> Not choosen </p>
                )}
                </AccordionDetails>
              </Accordion>
              </>
            ))}
        </List>
    );

}

export default ListComponent;