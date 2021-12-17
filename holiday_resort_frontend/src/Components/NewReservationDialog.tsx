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

import { differenceInDays } from "date-fns"
import { ThunkDispatch } from 'redux-thunk';
import { connect } from 'react-redux';
import { format } from 'date-fns'

import {UserInfoResponse} from "../Interfaces/UserInfoResponse";

import {getUserInfo} from "../Stores/ApiRequests/LoginApiRequest";

import { DateRange } from 'react-date-range';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';

import { ResortObjectInterface } from '../Interfaces/ResortObject';
import {AccommodationRequest} from "../Interfaces/AccommodationRequest";

import NumericTextField from './NumericTextField';

import NewAccommodation from "../Components/NewAccommodation";
import {getAvailableResortObjectsApi} from "../Stores/ApiRequests/ResortObjectApiRequest";


const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
      children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>,
  ) {
    return <Slide direction="up" ref={ref} {...props} />;
  });


interface MapDispatcherToProps {
    fetchAvailableResortObj : (jwtToken : string) => void,
    fetchUserInfo : (jwtToken : string) => void,
}

interface MapStateToProps {

    jwtToken : string,
    resortObjects : Array<ResortObjectInterface>,
    userDetails : UserInfoResponse,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({

    fetchAvailableResortObj : (jwtToken : string) => dispatch(getAvailableResortObjectsApi(jwtToken)),
    fetchUserInfo : (jwtToken : string) => dispatch(getUserInfo(jwtToken)),
});

const mapStateToProps = (state : any, accommodationProps : ComponentProps) : MapStateToProps => ({

    jwtToken : state.LoginReducer.jwt,
    resortObjects : state.ResortObjectsReducer.availableResortObjects,
    userDetails : state.RegisterReducer.userDetails,
});


interface ComponentProps {
    isOpen : boolean,
    handleClose : () => void,
    handleAccept : () => void,
}

type Props = MapStateToProps & MapDispatcherToProps & ComponentProps

const NewReservationDialog : React.FC<Props> = ({
    isOpen,
    handleClose,
    handleAccept,

    resortObjects,
    jwtToken,
    fetchAvailableResortObj,
    fetchUserInfo,
    userDetails,
}) => {

    React.useEffect(() => {
        if(jwtToken){
            fetchUserInfo(jwtToken);
        }
    }, [])

    React.useEffect(() => {

        if(userDetails && userDetails.firstName !== '' && userDetails.lastName !== '' && userDetails.phoneNumber !== ''){
            setUserReservationDetails(userDetails);
        }

    }, [userDetails])

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

    interface NewReservationRequest {
        reservationEndingDate : Date,
        reservationStartingDate : Date,
        reservationName : string,
        accommodationRequestList : Array<AccommodationRequest>,
        reservationRemarksRequestList : Array<any>,
    }

    const NEW_RESERVATION_DEFAULT : NewReservationRequest = {
        reservationEndingDate : new Date(),
        reservationStartingDate : new Date(),
        reservationName : '',
        accommodationRequestList : [],
        reservationRemarksRequestList : [],
    }

    const [newReservation, setNewReservation] = React.useState<NewReservationRequest>(NEW_RESERVATION_DEFAULT);

    interface NewAccommodation {
        isSet : boolean,
    }

    const NEW_ACCOMMODATION_DEFAULT : NewAccommodation = {
        isSet : false,
    }

    const [newAccommodationDialog, setNewAccommodationDialog] = React.useState<NewAccommodation>(NEW_ACCOMMODATION_DEFAULT);


    const USER_DETAILS_DEFAULT : UserInfoResponse= {
        firstName : '',
        lastName : '',
        phoneNumber : '',
    }

    const [userReservationDetails, setUserReservationDetails] = React.useState<UserInfoResponse>(USER_DETAILS_DEFAULT);

    React.useEffect(() => {
        if(jwtToken && jwtToken !== ""){
            fetchAvailableResortObj(jwtToken);
        }
    }, [])

    const formatDate = (startDate : Date, endDate : Date) => {
        if(startDate && endDate){
            return ` ${startDate.toLocaleDateString()} to ${endDate.toLocaleDateString()}`
        }
    } 

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
                        style={{textAlign : 'center', marginBottom : '1%'}}
                    >

                     New Reservation 
                    </Typography>
                    <Divider />
                </DialogTitle>
                <DialogContent
                    style={ dateRange.disappear 
                        ? {marginTop:'1%', width : '40vw', height : '70vh'}
                        : {marginTop:'1%'}
                    }
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
                    <div style={{
                    display : 'flex', 
                    justifyContent : 'center', 
                    alignItems : 'center',
                    flexDirection : 'column',
                    }}>
                        {
                        userDetails && resortObjects && resortObjects.length !== 0 ? (

                            <>
                                <Typography
                                    variant="h5"
                                    textAlign={'center'}
                                >
                                    Choosen date frame: 
                                    {formatDate(dateRange.startDate, dateRange.endDate)}
                                </Typography>
                                <Typography
                                    variant="h5"
                                    style={{marginBottom : '3%'}}
                                >
                                    <p> ({differenceInDays(dateRange.endDate, dateRange.startDate)} days)</p>
                                </Typography>
                  
                                <NumericTextField
                                    id={'reservationName'}
                                    label={'Reservation Name'}
                                    defaultValue=''
                                    optWidth={'50%'}
                                    type={'search'}
                                    onChange={(event) => 
                                        setNewReservation(reservation => ({...reservation, reservationName : event.target.value}))}
                                />

                                <div style={{   
                                    display : 'flex', 
                                    justifyContent : 'center', 
                                    alignItems : 'center',
                                    flexDirection : 'row',
                                    }}>

                                    <NumericTextField
                                        id={'firstName'}
                                        label={'First Name'}
                                        type={'search'}
                                        optWidth={'27%'}
                                        defaultValue={userDetails.firstName}
                                        onChange={(event) => 
                                            setUserReservationDetails(userDetails => ({...userDetails, firstName : event.target.value}))}
                                    />

                                    <NumericTextField
                                        id={'lastName'}
                                        label={'Last Name'}
                                        type={'search'}
                                        optWidth={'27%'}
                                        defaultValue={userDetails.lastName}
                                        onChange={(event) => 
                                            setUserReservationDetails(userDetails => ({...userDetails, lastName : event.target.value}))}
                                    />

                                    <NumericTextField
                                        id={'phoneNumber'}
                                        label={'Phone Number'}
                                        type={'search'}
                                        optWidth={'27%'}
                                        defaultValue={userDetails.phoneNumber}
                                        onChange={(event) => 
                                            setUserReservationDetails(userDetails => ({...userDetails, phoneNumber : event.target.value}))}
                                    />
                                </div>

                                <Button
                                    variant="outlined"
                                    color ="secondary"
                                    style={{marginTop : '5%', width : '30%'}}
                                    onClick={() => setNewAccommodationDialog({isSet : true})}
                                >  
                                    Add accommodation
                                </Button>

                                <NewAccommodation
                                    isOpen={newAccommodationDialog.isSet}
                                    closeHandler={() => {

                                        setNewAccommodationDialog(NEW_ACCOMMODATION_DEFAULT)
                                        }
                                    }
                                    acceptHandler={() => {}}
                                    resortObjects={resortObjects}
                                    jwtToken={jwtToken}
                                />

                                <Button
                                    variant="outlined"
                                    color ="secondary"
                                    style={{marginTop : '5%', width : '30%'}}
                                >  
                                    Save
                                </Button>
                            </>
                        ) : null}
                    </div>
                )}

                </DialogContent>
                <DialogActions>
                </DialogActions>         
            </div>
        </Dialog>



    );
}
export default connect<MapStateToProps, MapDispatcherToProps, ComponentProps>(
    mapStateToProps,
    mapDispatchToProps
  )(React.memo(NewReservationDialog))