import Button from '@mui/material/Button';
import { useHistory } from "react-router-dom";
import { createStyles, makeStyles, Theme } from '@material-ui/core';
import * as React from 'react';

import { ThunkDispatch } from 'redux-thunk';
import { pink } from '@mui/material/colors';
import { connect, ConnectedProps  } from 'react-redux';

import {User} from "../Interfaces/User";

import UserTable from "../Components/UserTable";
import CircularProgress from '@mui/material/CircularProgress';

import {GetAllUsers} from "../Stores/ApiRequests/ManageUsersApiRequest";
import {ChangeReservationStatus} from "../Stores/ApiRequests/ReservationApiRequest";

import {ReservationStatus} from "../Enums/SetReservationStatus";

interface MapDispatcherToProps {
    getUsers : (jwtToken : string) => void,
    changeReservationStatus : (jwtToken : string, reservationId : number, userId : number, status : ReservationStatus) => void,
} 

interface MapStateToProps {

    users : Array<User>,
    isFetching : boolean,

    jwtToken : string,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    getUsers : (jwtToken : string) => dispatch(GetAllUsers(jwtToken)),
    changeReservationStatus : (jwtToken : string, reservationId : number, userId : number, status : ReservationStatus) => dispatch(ChangeReservationStatus(jwtToken, reservationId, userId, status))
});

const mapStateToProps = (state : any) : MapStateToProps => ({

    users : state.ManageUsersReducer.users,
    isFetching : state.ManageUsersReducer.isFetching,

    jwtToken : state.LoginReducer.jwt,
});

const connector =  connect(mapStateToProps, mapDispatchToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;


interface AdminContextInterface {
    jwtToken_: string,
    changeReservationStatus_  : (jwtToken : string, reservationId : number, userId : number, status : ReservationStatus) => void,
}

export const AdminOperationsContext = React.createContext<AdminContextInterface | null> (null);


const AdminSection : React.FC<PropsFromRedux> = ({
    users,
    isFetching,
    jwtToken,

    getUsers,
    changeReservationStatus
})  => {

    let history = useHistory();
    const classes = useStyles();

    React.useEffect(() => {
        if(jwtToken){

            getUsers(jwtToken); 
        }
    }, [])

    const TableContextImp : AdminContextInterface = { 
        jwtToken_ : jwtToken,
        changeReservationStatus_ : changeReservationStatus,
    } 


    return(
        <div className={classes.root}>

            {!isFetching && users ? (
                <>
                    <h1 className={classes.reservationHeader}> User List View </h1>
                    <div className={classes.table}>
                        <AdminOperationsContext.Provider
                                value={TableContextImp}
                        >
                            <UserTable
                                userList={users}
                            />
                        </AdminOperationsContext.Provider>
                    </div>
                </>
            ) : (

                <CircularProgress 
                className={classes.spinner}
                size='7vh'
                sx={{
                    color: pink[800],
                    '&.Mui-checked': {
                        color: pink[600],
                    },
                    }}
                />
            )}

            <Button 
                style={{marginTop : '4%'}}
                variant="contained"
                type="submit"
                color="primary"
                onClick={history.goBack}
                >
                Get Back
            </Button>
     
    </div>
    );
}
export default connector(AdminSection);

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        height : '70vh',
        display: 'flex',
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        color : 'white',
        flexGrow : 5,
        paddingBottom : '10vh',
    },
    table: {
        width : '90vw',
    },
    spinner: {
        marginBottom: '3vh',
    },
    reservationHeader : {
        marginBottom : '6vh',
    }
  }));
