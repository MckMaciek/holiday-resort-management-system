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

import AddExternalServiceDialog from './AddExternalServiceDialog';

import { differenceInDays } from "date-fns"
import { ThunkDispatch } from 'redux-thunk';
import { connect } from 'react-redux';
import HomeIcon from '@mui/icons-material/Home';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import Avatar from '@mui/material/Avatar';
import DeleteIcon from '@mui/icons-material/Delete';

import {UserInfoResponse} from "../Interfaces/UserInfoResponse";

import {getUserInfo} from "../Stores/ApiRequests/LoginApiRequest";

import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';

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

    const handleDateSelect = (ranges : {selection : {startDate : Date, endDate : Date}}) => {
        setDateRange({
            startDate : ranges.selection.startDate,
            endDate : ranges.selection.endDate,
            disappear : false,
            key : 'selection',
        })

        setNewReservation(reservation => ({
            ...reservation, 
            reservationEndingDate : ranges.selection.endDate,
            reservationStartingDate : ranges.selection.startDate,
        }));
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

    interface NewAccommodationDialog {
        isSet : boolean,
    }

    const NEW_ACCOMMODATION_DEFAULT : NewAccommodationDialog = {
        isSet : false,
    }

    const [newAccommodationDialog, setNewAccommodationDialog] = React.useState<NewAccommodationDialog>(NEW_ACCOMMODATION_DEFAULT);

    interface NewExternalServiceDialog {
        isSet : boolean,
    }
    
    const NEW_EXTERNAL_SERVICE_DIALOG : NewExternalServiceDialog = {
        isSet : false,
    }

    const [newExternalServiceDialog, setNewExternalServiceDialog] = React.useState<NewExternalServiceDialog>(NEW_EXTERNAL_SERVICE_DIALOG);


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

    const transformRoToEvents = (eventId : number) => {
        let resortObjEvents = resortObjects.flatMap(rO => rO.eventResponseList.filter(event => event.id === eventId));
        return resortObjEvents;
    }
    
    const transformROIdToName = (resortObjectId : number) => {
        let resortObj = resortObjects.filter(rO => rO.id === resortObjectId)[0];
        return resortObj.objectName;
    }

    const removeResortObj = (resortObjectId : number) => {
        let resortObj = resortObjects.filter(rO => rO.id === resortObjectId)[0];

        newReservation.accommodationRequestList.filter(accommodation => accommodation.resortObjectId !== resortObj.id);
        setNewReservation(reservation => 
            ({...reservation, 
                accommodationRequestList : 
                newReservation.accommodationRequestList
                        .filter(accDelete => accDelete.resortObjectId !== resortObj.id)
            }));
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
                        onChange={handleDateSelect}
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

                                <Button
                                    variant="outlined"
                                    color ="secondary"
                                    style={{marginTop : '1%', width : '30%'}}
                                    onClick={() => setNewExternalServiceDialog({isSet : true})}
                                >  
                                    Add External Service
                                </Button>

                                <AddExternalServiceDialog
                                    isOpen={newExternalServiceDialog.isSet}
                                    jwtToken={jwtToken}
                                    closeHandler={() => setNewExternalServiceDialog({isSet : false})}
                                    acceptHandler={() => setNewExternalServiceDialog({isSet : false})}
                                />

                                <NewAccommodation
                                    isOpen={newAccommodationDialog.isSet}
                                    modifyReservation={setNewReservation}
                                    closeHandler={() => {
                                        setNewAccommodationDialog(NEW_ACCOMMODATION_DEFAULT)
                                        }
                                    }
                                    acceptHandler={() => {
                                        setNewAccommodationDialog(NEW_ACCOMMODATION_DEFAULT)
                                    }}
                                    resortObjects={resortObjects}
                                    jwtToken={jwtToken}
                                />

                                {newReservation.accommodationRequestList.length !== 0 ? (

                                <List>
                                    {newReservation.accommodationRequestList.map(accommodation => (
                                        <ListItem
                                        
                                        sx={{ 
                                            marginTop : '1.8%',
                                            borderRadius : '3%',
                                            padding : '4%', 
                                            width: '100%', 
                                            maxWidth: 360, 
                                            bgcolor: 'background.paper', 
                                            borderStyle : 'groove' 
                                        }}
                                        >
                                            <ListItemAvatar>
                                                <Avatar>
                                                    <HomeIcon/>
                                                </Avatar>
                                            </ListItemAvatar>
                                            <ListItemText
                                                primary={`Object name : ${transformROIdToName(accommodation.resortObjectId)}`}
                                                secondary={
                                                    <div>
                                                        <p> Choosen people {accommodation.numberOfPeople.toString()} </p> 
                                                        {accommodation.eventRequests.length !== 0 ? (
                                                                <p> Added : </p>
                                                        ) : null}
                                                        {accommodation.eventRequests.map((event) => (
                                                                <span> {transformRoToEvents(event.id)[0].eventType} , </span>
                                                        ))}
                                                    <div>                                            
                                                            <Button 
                                                                variant="outlined" 
                                                                color="secondary"
                                                                style={{marginTop : '5%'}}
                                                                startIcon={<DeleteIcon />}
                                                                onClick={() => removeResortObj(accommodation.resortObjectId)}
                                                            >
                                                                Delete
                                                            </Button>     
                                                        </div>   
                                                    </div>
                                                }
                                            />
                                        </ListItem>
                                    ))}
                                </List>

                                ) : null}

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