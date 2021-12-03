import {LoginActionPayloadInterface} from '../Interfaces/LoginActionPayload';
import loginApiRequest from '../Stores/ApiRequests/LoginApiRequest';

import {errorInterface} from '../Interfaces/ErrorHandling';

import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';
import { createStyles, makeStyles, Theme } from '@material-ui/core';
import CircularProgress from '@mui/material/CircularProgress';
import { pink } from '@mui/material/colors';
import Alert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';

import {
    Redirect
  } from 'react-router-dom';

import LoginForm from "../Components/LoginForm";
import { useEffect, useState } from 'react';

interface MapDispatcherToProps {
    sendLoginRequest : (loginModel : LoginActionPayloadInterface) => void;
}

interface MapStateToProps {
    isLoginFetching : boolean,
    isAuthenticated : boolean,
    loginError : errorInterface,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    sendLoginRequest : (loginModel : LoginActionPayloadInterface) => dispatch(loginApiRequest(loginModel))
});

const mapStateToProps = (state : any) : MapStateToProps => ({
    isLoginFetching : state.LoginReducer.isLoginFetching,
    isAuthenticated : state.LoginReducer.isAuthenticated,
    loginError : state.LoginReducer.error,
});

const connector =  connect(mapStateToProps, mapDispatchToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;

const LoginPage : React.FC<PropsFromRedux> = ({
    isLoginFetching,
    isAuthenticated,
    loginError,
    sendLoginRequest,
}) : JSX.Element => {

    const classes = useStyles();

    const [errorAlert, setErrorAlert] = useState({
        isSet : false,
        message : '',
    })

    useEffect(() => {
        if(loginError !== null && loginError.isErrorFlagSet === true){
            setErrorAlert(
            {
                isSet : true, 
                message : 'Could not log in'
            })
        }
    }, [loginError])

    return(
        <div className={classes.root}>

            {isAuthenticated === true ? (
                <Redirect
                exact
                to={{
                    pathname: '/about',
                    state : {
                        from : '/signin'
                    }
                }}
                />
            ) : null }

            {errorAlert !== null && errorAlert.isSet === true ? (
                <Snackbar 
                open={errorAlert.isSet}
                className={classes.errorAlert}
                >
                    <Alert 
                    onClose={() => {setErrorAlert({isSet : false, message : ''}); loginError.isErrorFlagSet = false}}
                    variant="filled" 
                    severity="error"
                    
                    className={classes.errorAlert}
                    >
                    {errorAlert.message}
                    </Alert>
                </Snackbar>
            ) : null
            }

            {isLoginFetching === true ? (
            <CircularProgress 
                className={classes.spinner}
                size='7vh'
                sx={{
                    color: pink[800],
                    '&.Mui-checked': {
                        color: pink[600],
                    },
                    }}
                />) : null}

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
    },

    errorAlert : {
        width : "20vw",
    },

  }));
export default connector(LoginPage);