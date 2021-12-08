import * as React from "react";

import { createStyles, makeStyles, Theme } from '@material-ui/core';
import { pink } from '@mui/material/colors';

import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';
import CircularProgress from '@mui/material/CircularProgress';

import { ReservationInterface } from '../Interfaces/Reservation';

import {
    getReservations, 
    deleteAccommodationApi
} from '../Stores/ApiRequests/ReservationApiRequest';

import ReservationTable from '../Components/ReservationTable';
import { useEffect, useState } from 'react';

interface MapDispatcherToProps {
    fetchReservations : (jwtToken : string) => void;
    removeAccommodation : (jwtToken : string, accommodationId : number) => void;
}

interface MapStateToProps {
    jwtToken : string,
    reservation : Array<ReservationInterface>,
    reservationFetching : boolean,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    fetchReservations : (jwtToken : string) => dispatch(getReservations(jwtToken)),
    removeAccommodation : (jwtToken : string, accommodationId : number) => dispatch(deleteAccommodationApi(jwtToken, accommodationId)),
});

const mapStateToProps = (state : any) : MapStateToProps => ({
    jwtToken : state.LoginReducer.jwt,
    reservation : state.ReservationReducer.reservation,
    reservationFetching : state.ReservationReducer.isFetching,
});

const connector =  connect(mapStateToProps, mapDispatchToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;

interface TableContextInterface {
    jwtToken_: string,
    removeAccommodation_ : (jwtToken : string, accommodationId : number) => void,
    setComponentRerender_  : (set : boolean) => void;
}

export const TableContext = React.createContext<TableContextInterface | null> (null);

const ReservationSection : React.FC<PropsFromRedux> = ({
    jwtToken,
    reservation,
    reservationFetching,
    fetchReservations,
    removeAccommodation,
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
            setComponentChanged(false);
        }

    }, [componentChanged])

    const classes = useStyles();

    // TABLE CONTEXT PO TO ŻEBY NIE PRZESYŁAĆ 2 RAZY PROPSY WGŁĄB DRZEWA TABELI
    const TableContextImp : TableContextInterface = { 
        jwtToken_ : jwtToken,
        removeAccommodation_ : removeAccommodation,
        setComponentRerender_ : setComponentChanged,
    } 

    return(
        <div className={classes.root}>
            {reservation.length !== 0 ? (
                <>
                    {reservationFetching ? (
                        <CircularProgress 
                        size='7vh'
                        sx={{
                            color: pink[800],
                            '&.Mui-checked': {
                                color: pink[600],
                            },
                            }}
                        />
                    ) : null}
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