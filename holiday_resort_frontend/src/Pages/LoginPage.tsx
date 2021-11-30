import {LoginActionPayloadInterface} from '../Interfaces/LoginActionPayload';
import loginApiRequest from '../Stores/ApiRequests/LoginApiRequest';

import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';
import { createStyles, makeStyles, Theme } from '@material-ui/core';
import CircularProgress from '@mui/material/CircularProgress';

import LoginForm from "../Components/LoginForm";
import { useState } from 'react';

interface MapDispatcherToProps {
    sendLoginRequest : (loginModel : LoginActionPayloadInterface) => void;
}

interface MapStateToProps {
    isLoginFetching : boolean,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    sendLoginRequest : (loginModel : LoginActionPayloadInterface) => dispatch(loginApiRequest(loginModel))
});

const mapStateToProps = (state : any) : MapStateToProps => ({
    isLoginFetching : state.LoginReducer.isLoginFetching,
});

const connector =  connect(mapStateToProps, mapDispatchToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;

const LoginPage : React.FC<PropsFromRedux> = ({
    isLoginFetching,
    sendLoginRequest,
}) : JSX.Element => {

    const classes = useStyles();

    return(
        <div className={classes.root}>

            {isLoginFetching === true ? (<CircularProgress className={classes.spinner}/>) : null}

            <LoginForm sendLoginReq={sendLoginRequest} />
        </div>
    );
}

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        backgroundColor: theme.palette.background.paper,
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        minHeight: '100vh',
    },
    spinner: {
        marginBottom: '3vh',
    }
    
  }));
export default connector(LoginPage);