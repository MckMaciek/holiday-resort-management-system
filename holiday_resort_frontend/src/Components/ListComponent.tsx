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
import {AccommodationInterface} from '../Interfaces/Accommodation';
import {ReservationInterface} from '../Interfaces/Reservation';

import { useTranslation } from "react-i18next";

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

}) : JSX.Element => {

  const { t } = useTranslation();

  const getDetailedPrice = (accommodation : AccommodationInterface) => {

    let numberOfPeople = accommodation.numberOfPeople as number;

    let maxAmountOfPeople = accommodation.resortObject.maxAmountOfPeople as number;
    let pricePerPerson = accommodation.resortObject.pricePerPerson as number;
    let unusedSpacePrice = accommodation.resortObject.unusedSpacePrice as number;

    let lessPeoplePay = maxAmountOfPeople - numberOfPeople;

    let finalPrice = (numberOfPeople * pricePerPerson) + (lessPeoplePay * unusedSpacePrice);
    let bonusEvents = 0;

    if(accommodation && accommodation.eventResponseList.length !== 0){

      bonusEvents = accommodation.eventResponseList
                      .map(event => event.price)
                      .reduce((total, sum) => total + sum)
    }

    return finalPrice + bonusEvents;
  }


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
                    primary={`${t(`listComponent.objectName`)} ${accommodation.resortObject.objectType}`}
                    secondary={
                      <div>
                        <p> {t(`listComponent.amountOfPeople`)} {accommodation.numberOfPeople} </p>
                        <p> {t(`listComponent.objectName`)} {accommodation.resortObject.objectName} </p>
                        <p> {t(`listComponent.pricePerPerson`)} {accommodation.resortObject.pricePerPerson.toFixed(2)} {t(`currency.symbol`)} </p>
                        <p> {t(`listComponent.pricePerUnusedSpace`)} {accommodation.resortObject.unusedSpacePrice.toFixed(2)} {t(`currency.symbol`)} </p>
                        <p> {t(`listComponent.maxAmountOfPeople`)} {accommodation.resortObject.maxAmountOfPeople} </p>
                        <p> <strong> {t(`listComponent.detailedPrice`)} {getDetailedPrice(accommodation).toFixed(2)} {t(`currency.symbol`)} </strong> </p>
                      </div>
                    }
                />
                </ListItem>
                <Accordion>
                <AccordionSummary
                  expandIcon={<ExpandMoreIcon />}
                >
                  <Typography sx={{ width: '33%', flexShrink: 0 }}>
                  {t(`listComponent.additionalServices`)}
                  </Typography>

                  <DialogConfirm
                    isOpen={sendDialog.isSet}
                    closeHandler={sendDialogCloseHandler}
                    onAcceptHandler={sendDialogAcceptHandler}
                    dialogTitle={t(`listComponent.changeReservationStatusDialog.dialogTitle`)}
                    dialogDescription={`${t(`listComponent.changeReservationStatusDialog.dialogStatusDescription.firstPart`)} ${reservation.reservationName} 
                    ${t(`listComponent.changeReservationStatusDialog.dialogStatusDescription.secondPart`)} ${reservation.reservationStatus} ${t(`listComponent.changeReservationStatusDialog.dialogStatusDescription.thirdPart`)}`}
                    disagreeText={t(`listComponent.changeReservationStatusDialog.disagreeText`)}
                    agreeText={t(`listComponent.changeReservationStatusDialog.agreeText`)}
                />

                </AccordionSummary>
                <AccordionDetails>
                {accommodation.eventResponseList.length !== 0 ? (
                  accommodation.eventResponseList.map(eventResponse => (
                    <p> {eventResponse.eventType.toLowerCase()} - {eventResponse.price.toFixed(2)} {t(`currency.symbol`)} </p>
                    ))
                ) : (
                  <p> {t(`listComponent.additionalServicesNotChoosen`)} </p>
                )}
                </AccordionDetails>
              </Accordion>
              </>
            ))}
        </List>
    );

}

export default ListComponent;