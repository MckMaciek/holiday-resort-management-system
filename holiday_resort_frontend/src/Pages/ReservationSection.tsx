import * as React from "react";

import { createStyles, makeStyles, Theme } from '@material-ui/core';
import { pink } from '@mui/material/colors';

import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';
import CircularProgress from '@mui/material/CircularProgress';
import Alert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';
import {postReservation} from "../Stores/ApiRequests/ReservationApiRequest";
import { ReservationInterface } from '../Interfaces/Reservation';

import Box from '@mui/material/Box';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';

import {UserInfoResponse} from "../Interfaces/UserInfoResponse";

import {
    getReservations, 
    deleteAccommodationApi,
    markReservationStarted,
    deleteReservationApi,
    patchReservation,
} from '../Stores/ApiRequests/ReservationApiRequest';

import ReservationTable from '../Components/ReservationTable';
import NewReservationDialog from "../Components/NewReservationDialog";
import { NewReservationRequest } from '../Interfaces/NewReservationRequest';
import { useEffect, useState } from 'react';

interface MapDispatcherToProps {
    sendReservation : (jwtToken : string, reservationRequest : NewReservationRequest) => void,
    updateReservation : (jwtToken : string, reservationRequest : NewReservationRequest) => void,
    removeReservation : (jwtToken : string, reservationId : number) => void,
    fetchReservations : (jwtToken : string) => void;
    removeAccommodation : (jwtToken : string, accommodationId : number) => void;
    setReservationStarted : (jwtToken : string, reservationId : number) => void;
}

interface MapStateToProps {
    jwtToken : string,
    reservation : Array<ReservationInterface>,
    reservationFetching : boolean,
    roles : Array<String>,
    userDetails : UserInfoResponse,

    objectModified : boolean,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    
    sendReservation : (jwtToken : string, reservationRequest : NewReservationRequest) => dispatch(postReservation(jwtToken, reservationRequest)),
    updateReservation : (jwtToken : string, reservationRequest : NewReservationRequest) => dispatch(patchReservation(jwtToken, reservationRequest)),
    removeReservation : (jwtToken : string, reservationId) => dispatch(deleteReservationApi(jwtToken, reservationId)),
    fetchReservations : (jwtToken : string) => dispatch(getReservations(jwtToken)),
    removeAccommodation : (jwtToken : string, accommodationId : number) => dispatch(deleteAccommodationApi(jwtToken, accommodationId)),
    setReservationStarted : (jwtToken : string, reservationId : number) => dispatch(markReservationStarted(jwtToken, reservationId)),
});

const mapStateToProps = (state : any) : MapStateToProps => ({
    jwtToken : state.LoginReducer.jwt,
    reservation : state.ReservationReducer.reservation,
    reservationFetching : state.ReservationReducer.isFetching,
    roles : state.LoginReducer.roles,
    userDetails : state.RegisterReducer.userDetails,

    objectModified : state.UserOperationsReducer.objectModified,
});

const connector =  connect(mapStateToProps, mapDispatchToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;

interface TableContextInterface {
    jwtToken_: string,
    roles_ : Array<String>,
    removeAccommodation_ : (jwtToken : string, accommodationId : number) => void,
    setReservationStarted_ : (jwtToken : string, reservationId : number) => void,
    removeReservation_ : (jwtToken : string, reservationId : number) => void,
    updateReservation_ : (jwtToken : string, reservationRequest : NewReservationRequest) => void,
}

export const TableContext = React.createContext<TableContextInterface | null> (null);

const ReservationSection : React.FC<PropsFromRedux> = ({
    jwtToken,
    reservation,
    reservationFetching,
    roles,
    objectModified,
    userDetails,
    fetchReservations,
    removeAccommodation,
    setReservationStarted,
    removeReservation,
    sendReservation,
    updateReservation,
}) => {

    interface operationAlertInterface {
        isSet : boolean,
        message : string,
    }

    const OPERATION_ALERT_DEFAULT : operationAlertInterface  = {
        isSet : false,
        message : '',
    }

    const [operationAlert, setOperationAlert] = useState<operationAlertInterface>(OPERATION_ALERT_DEFAULT)

    interface NewReservationDialogInterface {
        isSet : boolean,
    }

    const NEW_RESERVATION_DIALOG_DEFAULT : NewReservationDialogInterface = {
        isSet : false,
    }

    const [newReservationDialog, setNewReservationDialog] = useState<NewReservationDialogInterface>(NEW_RESERVATION_DIALOG_DEFAULT);

    useEffect(() => {
        if(jwtToken && jwtToken != ""){
            fetchReservations(jwtToken);
        }

    }, [jwtToken])

    useEffect(() => {
        if(jwtToken && jwtToken != ""){
            fetchReservations(jwtToken);

            if(objectModified === true){
                setOperationAlert({
                    isSet : true,
                    message : 'Edited with success'
                })
            }
        }

    }, [objectModified])

    const classes = useStyles();

    // TABLE CONTEXT PO TO ??EBY NIE PRZESY??A?? 2 RAZY PROPSY WG????B DRZEWA TABELI
    const TableContextImp : TableContextInterface = { 
        jwtToken_ : jwtToken,
        roles_ : roles, 
        removeAccommodation_ : removeAccommodation,
        setReservationStarted_ : setReservationStarted,
        removeReservation_ : removeReservation,
        updateReservation_ : updateReservation,
    } 

    return(
        <>
            <div className={classes.root}>

                {newReservationDialog && newReservationDialog.isSet ? (
                    <NewReservationDialog
                        reservationName=""
                        reservationId={null}
                        externalServiceText="Add"
                        reservationOperation="New Reservation"
                        isOpen={newReservationDialog.isSet}
                        handleClose={() => setNewReservationDialog(NEW_RESERVATION_DIALOG_DEFAULT)}
                        handleAccept={() => setNewReservationDialog(NEW_RESERVATION_DIALOG_DEFAULT)}
                        createOrUpdate={sendReservation}
                    />
                ) : null}

                {reservation.length !== 0 ? (
                    <>

                        <h1 className={classes.reservationHeader}> Reservation of <span style={{color :  'red'}}> Yours </span> </h1>
                            
                        {true ? (
                            <Snackbar
                                open={operationAlert.isSet}
                                onClose={() => setOperationAlert(OPERATION_ALERT_DEFAULT)}
                                autoHideDuration={3000}
                            >
                                <Alert 
                                sx={{width : '40vw', marginBottom : '2vh', marginTop : '1vh'}}
                                onClose={() => {
                                    setOperationAlert(OPERATION_ALERT_DEFAULT); 
                                }}
                                variant="filled" 
                                severity="success"
                                >
                                        {operationAlert.message}
                                </Alert>
                            </Snackbar>
                        ) : null}

                        <TableContext.Provider
                            value={TableContextImp}
                        >
                            <ReservationTable 
                                reservationList={reservation}
                            />
                        </TableContext.Provider>
                    </>
                ) : (
                    <>
                        <h1> {userDetails.firstName}, do not wait :) </h1>
                        <h1> Add <span style={{color :  'red'}}> Your Own </span> reservation!</h1>
                        <h2 style={{marginTop : '4%'}}> Click on add icon in the right corner</h2>
                    </>
                )}
            </div>
            <Box sx={{ 
                '& > :not(style)': { m: 1 }, 
                'display' : 'flex',
                'justifyContent' : 'flex-end',
                'marginRight' : '6vw',

                }}>
                <Fab 
                    color="primary" 
                    aria-label="add" 
                    onClick={() => setNewReservationDialog({isSet : true})}
                    style={{
                        height : '8vh', 
                        width : '4vw',
                        background : '#161a31',
                        marginBottom : '4vh',
                    }}
                >
                    <AddIcon />
                </Fab>
            </Box>
        </>
    );

}

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        color : 'white',
        flexGrow : 5,
        paddingBottom : '10vh',
    },
    reservationHeader : {
        marginBottom : '15vh',
    }
  }));

export default connector(ReservationSection);