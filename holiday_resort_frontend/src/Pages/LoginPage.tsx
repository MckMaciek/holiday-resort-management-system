import {LoginActionPayloadInterface} from '../Interfaces/LoginActionPayload';
import loginApiRequest from '../Stores/ApiRequests/LoginApiRequest';

import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';
import { createStyles, makeStyles, Theme } from '@material-ui/core';
import Alert from '@mui/material/Alert';

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

    const [validationStatus, setValidationStatus] = useState({status : false, message : ""});

    const validationFailed = (reason : string) => {
        setValidationStatus({
            status : true,
            message : reason
        });
    }

    return(
        <div className={classes.root}>
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

    validationLabel: {
        textAlign : 'center',
        marginBottom: "10vh",
        minWidth : "100vw",
        background: "red",
    }
  }));
export default connector(LoginPage);