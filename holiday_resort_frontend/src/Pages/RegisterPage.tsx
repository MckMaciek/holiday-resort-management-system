import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps, useDispatch  } from 'react-redux';

import {RegisterActionPayloadInterface} from '../Interfaces/ReduxInterfaces/RegisterActionPayload';
import registerApiRequest from "../Stores/ApiRequests/RegisterApiRequest";
import {loginSetAuthenticated} from "../Stores/Actions/AuthOperations";

import { createStyles, makeStyles, Theme } from '@material-ui/core';
import Typography from '@mui/material/Typography';
import CircularProgress from '@mui/material/CircularProgress';
import { pink } from '@mui/material/colors';

import RegisterForm from '../Components/RegisterForm';
import { useTranslation } from "react-i18next";

import { Link } from 'react-router-dom';
import { useEffect } from 'react';


interface MapDispatcherToProps {
    sendRegisterRequest : (registerModel : RegisterActionPayloadInterface) => void;
}

interface MapStateToProps {
    isRegisterFetching : boolean,
    isAuthenticated : boolean,
    isEmailSent : boolean,
    isEmailFetching : boolean,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    sendRegisterRequest : (registerModel : RegisterActionPayloadInterface) => dispatch(registerApiRequest(registerModel))
});

const mapStateToProps = (state : any) : MapStateToProps => ({
    isRegisterFetching : state.RegisterReducer.isRegisterFetching,
    isAuthenticated : state.LoginReducer.isAuthenticated,
    isEmailSent : state.EmailReducer.isSent,
    isEmailFetching : state.EmailReducer.isFetching,
});

const connector =  connect(mapStateToProps, mapDispatchToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;


const RegisterPage : React.FC<PropsFromRedux> = ({
    isRegisterFetching,
    isAuthenticated,
    isEmailSent,
    isEmailFetching,
    sendRegisterRequest
}) : JSX.Element => {

    const classes = useStyles();
    const dispatch = useDispatch();
    const { t } = useTranslation();

    useEffect(() => {
        if(isAuthenticated === true){
            dispatch(loginSetAuthenticated(false));
        }

    }, [isAuthenticated]);

    return(
        <div className={classes.root}>
            <RegisterForm 
            sendRegisterReq={sendRegisterRequest} 
            isEmailSent={isEmailSent} 
            isEmailFetching={isEmailFetching}
            />
            <Typography className={classes.aboutHeader} 
            align='center'
            variant='h3'
            >
                <div className={classes.spinnerWithAboutContainer}>
                    {isRegisterFetching === true ? (<CircularProgress 
                                                    className={classes.spinner}
                                                    size='7vh'
                                                    sx={{
                                                        color: pink[800],
                                                        '&.Mui-checked': {
                                                          color: pink[600],
                                                        },
                                                      }}
                                                    />) : null}
                    <Link className={classes.aboutLink} to='/about'> {t(`registerPage.aboutUs-link`)}</Link>
                </div>
            </Typography>
        </div>
    );

}


const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        backgroundColor: theme.palette.background.paper,
        flexDirection : 'row',
        justifyContent : 'center',
        alignItems : 'center',
        minHeight: '100vh',
    },

    aboutHeader:{
        display : 'flex',
        marginBottom : '1vh',
    },

    aboutIcon : {
        color : 'white',
        fontSize : '44px',
    },

    spinnerWithAboutContainer : {
        display : 'flex',
        justifyContent : 'center',
        alignItems : 'center',
        flexDirection : 'column',
        marginLeft : '10vw',
        marginRight : 'auto',
    },

    aboutLink : {
        color : 'white',
        textDecoration : 'none',
        textAlign : 'center',
    },

    spinner: {
        marginBottom: '1vh',
        position : 'absolute',
        top : '35%',
    }
    
  }));
export default connector(RegisterPage);