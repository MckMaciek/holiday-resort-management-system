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
    fetchReservations,
    removeAccommodation,
    setReservationStarted,
    removeReservation,
    sendReservation,
    updateReservation,
}) => {

    const [componentChanged, setComponentChanged] = useState(false);

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

    // TABLE CONTEXT PO TO ŻEBY NIE PRZESYŁAĆ 2 RAZY PROPSY WGŁĄB DRZEWA TABELI
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
                        <h1 className={classes.reservationHeader}> Loading Reservation of Yours </h1>
                        <CircularProgress 
                        size='7vh'
                        sx={{
                            color: pink[800],
                            '&.Mui-checked': {
                                color: pink[600],
                            },
                            }}
                        />
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