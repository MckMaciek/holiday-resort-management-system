import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@mui/material/ButtonGroup';
import SendIcon from '@mui/icons-material/Send';

import * as React from 'react';
import {ReservationStatus} from "../Enums/SetReservationStatus";

import SummaryDialog from "../Components/SummaryDialog";
import {ReservationInterface} from "../Interfaces/Reservation";

import {AdminOperationsContext} from "../MainPageSections/AdminSection";

interface DialogProps {
    isOpen : boolean,
    userId : number,
    userName : string,
    reservationList : Array<ReservationInterface>,
    closeHandler : () => void,
    acceptHandler : () => void,
}

const AdminReservationView : React.FC<DialogProps> = ({
    isOpen,
    userId,
    userName,
    reservationList,
    closeHandler,
    acceptHandler,
}) => {

    interface OpenSummary {
        reservationId : number,
        isOpen : boolean,
    }

    const OPEN_SUMMARY_DEFAULT = {
        reservationId : -1,
        isOpen : false,
    }

    const [openSummary, setOpenSummary] = React.useState<OpenSummary>(OPEN_SUMMARY_DEFAULT);

    interface SetReservationStatus {
        reservationId : number,
        status : ReservationStatus,
        toSent : boolean,
    }

    const RESERVATION_STATUS_CHANGE_DEFAULT : SetReservationStatus = {
        reservationId : -1,
        status : ReservationStatus.NONE,
        toSent : false,
    }

    const [reservationStatusChange, setReservationStatusChange] = React.useState<SetReservationStatus>(RESERVATION_STATUS_CHANGE_DEFAULT);
    const AdminOperationsContextImp = React.useContext(AdminOperationsContext);


    React.useEffect(() => {
        if(reservationStatusChange && AdminOperationsContextImp && reservationStatusChange.toSent){
            AdminOperationsContextImp.changeReservationStatus_(AdminOperationsContextImp.jwtToken_, reservationStatusChange.reservationId, userId, reservationStatusChange.status);
        }

    }, [reservationStatusChange])


    return (
        <Dialog
            fullWidth
            open={isOpen}
            onClose={closeHandler}
        >
            <DialogTitle style={{textAlign : 'center'}}> Reservation List for User Id {userId} ({userName}) </DialogTitle>
            <Divider style={{width:'100%', marginTop : '0.3%' ,marginBottom : '0.3%'}} />  
            <DialogContent style ={{marginTop : '2.5%'}}>
                <DialogContentText>
                <Typography
                        variant="h5"
                        style={{marginBottom : '1%'}}
                    >

                    Reservations :
                </Typography>
                {reservationList.length !== 0 ? reservationList.map(reservation => (
                    <>
                    <Divider style={{width:'100%', marginTop : '0.3%' ,marginBottom : '0.3%'}} />  
                        <Button 
                            variant="outlined" 
                            size="medium" 
                            color="secondary" 
                            style={{display : 'block', marginTop : '8%', marginBottom : '8%'}}
                            endIcon={<SendIcon />}
                            onClick={() => setOpenSummary(
                            {
                                isOpen : true,
                                reservationId : reservation.id,
                            }              
                            )}
                            >
                            {`Reservation id ${reservation.id} `}
                        </Button>

                        <p> Set status :</p>

                        <ButtonGroup 
                            aria-label="outlined primary button group"
                            style={{minWidth : '75%', marginTop : '0.3%'}}
                        >
                            <Button 
                                size="medium" 
                                variant="outlined"
                                color="primary"
                                style={{display : 'block', marginBottom : '5%', marginRight : '7%', width : '100%'}}
                                onClick={() => {
                                    setReservationStatusChange({
                                        reservationId : reservation.id,
                                        status : ReservationStatus.ACCEPTED,
                                        toSent : true,
                                    });
                                    
                                }}
                                >
                                Accepted
                            </Button>

                            <Button 
                                size="medium" 
                                variant="outlined"
                                color="primary"
                                style={{display : 'block', marginBottom : '5%', marginRight : '7%', width : '100%'}}
                                onClick={() => {
                                    setReservationStatusChange({
                                        reservationId : reservation.id,
                                        status : ReservationStatus.ARCHIVED,
                                        toSent : true,
                                    });
                                    
                                }}
                                >
                                Archived
                            </Button>

                            <Button 
                                variant="outlined"
                                color="secondary"
                                size="medium" 
                                style={{display : 'block', marginBottom : '5%', width : '100%'}}
                                onClick={() => {
                                    setReservationStatusChange({
                                        reservationId : reservation.id,
                                        status : ReservationStatus.CANCELLED,
                                        toSent : true,
                                    });
                                    
                                }}
                                >
                                Cancelled
                            </Button>

                        </ButtonGroup>
                        
                        {openSummary.isOpen && openSummary.reservationId === reservation.id ? (
                            <SummaryDialog
                                open={openSummary.isOpen}
                                handleClose={() => setOpenSummary(OPEN_SUMMARY_DEFAULT)}
                                handleAccept={() => setOpenSummary(OPEN_SUMMARY_DEFAULT)}
                                reservation={reservation}
                                submit="Ok"
                                cancel="Cancel"
                            />
                        ) : null}
                    
                    </>
                )) : (
                    <p> None </p>
                )}


                </DialogContentText>
            </DialogContent>
            
            <DialogActions>
            </DialogActions>

        </Dialog>

    );
}

export default AdminReservationView;