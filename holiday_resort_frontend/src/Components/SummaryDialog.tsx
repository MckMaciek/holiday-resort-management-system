import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';
import { TransitionProps } from '@mui/material/transitions';

import {ReservationInterface} from "../Interfaces/Reservation";


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
}

const SummaryDialog : React.FC<IProps> = ({
    open,
    submit,
    cancel,
    handleClose,
    reservation,
}) => {

    {console.log(reservation)}

    return(
        <Dialog
            open={open}
            TransitionComponent={Transition}
            keepMounted
            maxWidth='lg'
            onClose={handleClose}
            aria-describedby="alert-dialog-slide-description"
        >
            <DialogTitle>{"Use Google's location service?"}</DialogTitle>
            <DialogContent
                style={{height:'70vh', marginTop:'3%'}}
            >
            <DialogContentText id="alert-dialog-slide-description">
                Let Google help apps determine location. This means sending anonymous
                location data to Google, even when no apps are running.
            </DialogContentText>
            </DialogContent>
            <DialogActions>
            <Button onClick={handleClose}> {cancel} </Button>
            <Button onClick={handleClose}> {submit} </Button>
            </DialogActions>
        </Dialog>
    );

}
export default SummaryDialog;