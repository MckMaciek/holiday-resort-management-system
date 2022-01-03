import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import Button from '@material-ui/core/Button';
import SendIcon from '@mui/icons-material/Send';

import * as React from 'react';

import SummaryDialog from "../Components/SummaryDialog";
import {ReservationInterface} from "../Interfaces/Reservation";

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
                        <Button variant="outlined" size="medium" color="secondary" endIcon={<SendIcon />}
                            onClick={() => setOpenSummary(
                            {
                                isOpen : true,
                                reservationId : reservation.id,
                            }              
                            )}
                            >
                            {`Reservation ${reservation.id}`}
                        </Button>

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