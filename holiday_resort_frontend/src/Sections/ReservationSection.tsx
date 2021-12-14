import * as React from "react";

import { createStyles, makeStyles, Theme } from '@material-ui/core';
import { pink } from '@mui/material/colors';

import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';
import CircularProgress from '@mui/material/CircularProgress';

import { ReservationInterface } from '../Interfaces/Reservation';

import {
    getReservations, 
    deleteAccommodationApi,
    markReservationStarted,
} from '../Stores/ApiRequests/ReservationApiRequest';

import ReservationTable from '../Components/ReservationTable';
import { useEffect, useState } from 'react';

interface MapDispatcherToProps {
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
}) => {

    const [componentChanged, setComponentChanged] = useState(false);

    useEffect(() => {
        if(jwtToken && jwtToken != ""){
            fetchReservations(jwtToken);
        }

    }, [jwtToken])

    useEffect(() => {
        if(jwtToken && jwtToken != ""){
            fetchReservations(jwtToken);
        }

    }, [objectModified])

    const classes = useStyles();

    // TABLE CONTEXT PO TO ŻEBY NIE PRZESYŁAĆ 2 RAZY PROPSY WGŁĄB DRZEWA TABELI
    const TableContextImp : TableContextInterface = { 
        jwtToken_ : jwtToken,
        roles_ : roles, 
        removeAccommodation_ : removeAccommodation,
        setReservationStarted_ : setReservationStarted,
    } 

    return(
        <div className={classes.root}>
            {reservation.length !== 0 ? (
                <>
                    <h1 className={classes.reservationHeader}> Reservation of Yours </h1>

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
        marginBottom : 'vh',
    }
  }));

export default connector(ReservationSection);