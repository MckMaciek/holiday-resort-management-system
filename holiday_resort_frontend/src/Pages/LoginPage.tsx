import {LoginActionPayloadInterface} from '../Interfaces/ReduxInterfaces/LoginActionPayload';
import {loginApiRequest} from '../Stores/ApiRequests/LoginApiRequest';

import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps, useDispatch  } from 'react-redux';
import { createStyles, makeStyles, Theme } from '@material-ui/core';
import CircularProgress from '@mui/material/CircularProgress';
import { pink } from '@mui/material/colors';
import Alert from '@mui/material/Alert';
import Snackbar from '@mui/material/Snackbar';
import { useTranslation } from "react-i18next";
import {AlertActionType}  from '../Enums/AlertTypes';
import {registerEmailSent} from '../Stores/Actions/EmailOperations';

import {loginAttemptFailed} from '../Stores/Actions/UserOperations';

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
    isEmailSent : boolean,
    isLoginFailed : boolean,
}

interface AlertState {
    isSet : boolean,
    message : string,
    cause : AlertActionType.EMAIL_SENT | AlertActionType.LOGIN_FAILED | AlertActionType.NONE,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    sendLoginRequest : (loginModel : LoginActionPayloadInterface) => dispatch(loginApiRequest(loginModel))
});

const mapStateToProps = (state : any) : MapStateToProps => ({
    isLoginFetching : state.LoginReducer.isLoginFetching,
    isAuthenticated : state.LoginReducer.isAuthenticated,
    isEmailSent : state.EmailReducer.isSent,
    isLoginFailed : state.UserOperationsReducer.isLoginFailed,
});


const connector =  connect(mapStateToProps, mapDispatchToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;

const LoginPage : React.FC<PropsFromRedux> = ({
    isLoginFetching,
    isAuthenticated,
    isEmailSent,
    isLoginFailed,
    sendLoginRequest,
}) : JSX.Element => {

    const classes = useStyles();
    const dispatch = useDispatch();
    const { t } = useTranslation();

    const [alertMessage, setAlertMessage] = useState<AlertState>({
        isSet : false,
        message : '',
        cause : AlertActionType.NONE,
    })

    useEffect(() => {
        if(isLoginFailed){
            setAlertMessage(
            {
                isSet : true, 
                message : t(`loginPage.alert.failed-log-in`),
                cause : AlertActionType.LOGIN_FAILED,
            })
        }

        if(isEmailSent){
            setAlertMessage(
            {
                isSet : true, 
                message : t(`loginPage.alert.email-sent`),
                cause : AlertActionType.EMAIL_SENT,
            })
        }

    }, [isLoginFailed, isEmailSent])

    return(

        <div className={classes.root}>

            {isAuthenticated === true ? (
                <Redirect
                exact
                to={{
                    pathname: '/',
                    state : {
                        from : '/signin'
                    }
                }}
                />
            ) : null }

            {alertMessage && alertMessage.cause === AlertActionType.EMAIL_SENT ? (
                <Snackbar
                    open={alertMessage.isSet}
                    >
                        <Alert 
                        onClose={() => {
                            setAlertMessage({
                                isSet : false, 
                                message : '', 
                                cause : AlertActionType.NONE
                            }); 
                            dispatch(registerEmailSent(false))}
                        }
                        variant="filled" 
                        severity="success"
                        className={classes.errorAlert}
                        >
                        {alertMessage.message}
                        </Alert>
                </Snackbar>
            ) : null
            }

            {alertMessage && alertMessage.cause === AlertActionType.LOGIN_FAILED ? (
                <Snackbar 
                open={alertMessage.isSet}
                className={classes.errorAlert}
                >
                    <Alert 
                    onClose={
                        () => {
                            setAlertMessage({
                            isSet : false, 
                            message : '', 
                            cause : AlertActionType.NONE
                        }); 
                        dispatch(loginAttemptFailed(false));
                        }
                    }
                    variant="filled" 
                    severity="error"      
                    className={classes.errorAlert}
                    >
                    {alertMessage.message}
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