import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';
import { TransitionProps } from '@mui/material/transitions';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';

import { DateRange } from 'react-date-range';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';


const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
      children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>,
  ) {
    return <Slide direction="up" ref={ref} {...props} />;
  });

interface IProps {
    isOpen : boolean,
    handleClose : () => void,
    handleAccept : () => void,
}

const NewReservationDialog : React.FC<IProps> = ({
    isOpen,
    handleClose,
    handleAccept
}) => {

    const handleSelect = (ranges : {selection : {startDate : Date, endDate : Date}}) => {
        setDateRange({
            startDate : ranges.selection.startDate,
            endDate : ranges.selection.endDate,
            disappear : false,
            key : 'selection',
        })
      }

      interface DateRangeInterface {
        startDate : Date,
        endDate : Date,
        disappear : boolean,
        key : string,
      }

      const DATE_RANGE_DEFAULT_STATE : DateRangeInterface = {
        startDate : new Date(),
        endDate : new Date(),
        disappear : false,
        key : 'selection',
      }

      const [dateRange, setDateRange] = React.useState<DateRangeInterface>(DATE_RANGE_DEFAULT_STATE);

    return(

        <Dialog
            open={isOpen}
            TransitionComponent={Transition}
            sx={{display : 'flex', justifyContent : 'center', alignItems : 'center'}}
            keepMounted
            maxWidth='lg'
            onClose={handleClose}
            aria-describedby="alert-dialog-slide-description"
        >
            <div>
                <DialogTitle>
                    <Typography
                        variant="h5"
                        style={{textAlign : 'center', marginBottom : '2%'}}
                    >

                     New Reservation 
                    </Typography>
                    <Divider />
                </DialogTitle>
                <DialogContent
                    style={{marginTop:'1%'}}
                >

                {dateRange && !dateRange.disappear ? (
                <div style={{display : 'flex', flexDirection : 'column', alignItems : 'center', justifyContent : 'center'}}>
                    <DateRange
                        ranges={[dateRange]}
                        onChange={handleSelect}
                        moveRangeOnFirstSelection={false}
                        minDate={new Date()}
                    />
                    <Button
                        variant="outlined"
                        onClick={() => setDateRange(dataRange => ({...dataRange, disappear : true}))}
                    >
                        Next
                    </Button>
                    <DialogContentText id="alert-dialog-slide-description">
                    </DialogContentText>
                </div>
                ) : (
                    <div>
                        <p> TODO DALEJ </p>
                    </div>
                )}

                </DialogContent>
                <DialogActions>
                </DialogActions>         
            </div>
        </Dialog>



    );
}
export default NewReservationDialog;