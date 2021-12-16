import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';
import { TransitionProps } from '@mui/material/transitions';

import {TableContext} from '../MainPageSections/ReservationSection';

import DialogConfirm from "./DialogConfirm";
import {ReservationInterface} from "../Interfaces/Reservation";
import ListComponent from "./ListComponent";
import { Typography } from '@material-ui/core';
import Box from '@mui/material/Box';


const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
      children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>,
  ) {
    return <Slide direction="up" ref={ref} {...props} />;
  });

interface IProps {
    open : boolean,
    submit : string,
    cancel : string,
    reservation : ReservationInterface
    handleClose : () => void,
    handleAccept : () => void,
}

const SummaryDialog : React.FC<IProps> = ({
    open,
    submit,
    cancel,
    handleClose,
    reservation,
    handleAccept,
}) => {

    
  interface SEND_DIALOG_INTERFACE {
    isSet : boolean,
  }

  const SEND_DIALOG_CONFIRMATION_DEFAULT : SEND_DIALOG_INTERFACE = {
    isSet : false,
  }
  const [sendDialog, setSendDialog] = React.useState<SEND_DIALOG_INTERFACE>(SEND_DIALOG_CONFIRMATION_DEFAULT);

  const TableContextImp = React.useContext(TableContext);

    return(
        <Dialog
            open={open}
            TransitionComponent={Transition}
            keepMounted
            maxWidth='lg'
            onClose={handleClose}
            aria-describedby="alert-dialog-slide-description"
        >
            <DialogTitle>SUMMARY OF - {reservation.reservationName}</DialogTitle>
            <DialogContent
                style={{height:'70vh', marginTop:'1%'}}
            >
            <DialogContentText id="alert-dialog-slide-description">

            <Typography
                    variant="h5"
                    component="div"
                    style={{marginBottom : '1vh'}}
                >
                    <strong> Reservation Status </strong> : {reservation.reservationStatus}
                </Typography>

                <Typography
                    variant="h5"
                    component="div"
                >
                    <strong> Reservation Starts </strong> : {reservation.reservationDate}
                </Typography>

                <Typography
                    variant="h5"
                    component="div"
                >
                    <strong> Reservation Ends </strong> : {reservation.reservationEndingDate}
                </Typography>
                <Typography
                    variant="h5"
                    style={{marginTop : '2%'}}
                >
                    Total of days : <strong>{ Math.ceil((new Date(reservation.reservationEndingDate).getTime() - new Date(reservation.reservationDate).getTime())
                    / (1000 * 3600 * 24))} </strong>
                </Typography>

                <ListComponent
                reservation={reservation}
                sendDialog={sendDialog}
                sendDialogCloseHandler={() => setSendDialog(SEND_DIALOG_CONFIRMATION_DEFAULT)}
                sendDialogAcceptHandler={ () => {

                    TableContextImp?.setReservationStarted_(TableContextImp.jwtToken_, reservation.id);
                    setSendDialog(SEND_DIALOG_CONFIRMATION_DEFAULT);
                }}
                >
                </ListComponent>
            </DialogContentText>
            </DialogContent>
            <DialogActions>
            <Button onClick={handleClose}> {cancel} </Button>
            <Button 
            disabled={reservation.reservationStatus !== "DRAFT"}
            onClick={() => setSendDialog({isSet : true})}
            > 
                {submit} 
            </Button>
            </DialogActions>
        </Dialog>
    );

}
export default SummaryDialog;