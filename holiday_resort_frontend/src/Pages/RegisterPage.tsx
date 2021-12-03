import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps, useDispatch  } from 'react-redux';

import {RegisterActionPayloadInterface} from '../Interfaces/RegisterActionPayload';
import registerApiRequest from "../Stores/ApiRequests/RegisterApiRequest";
import {loginSetAuthenticated} from "../Stores/Actions/AuthOperations";

import { createStyles, makeStyles, Theme } from '@material-ui/core';
import Typography from '@mui/material/Typography';
import CircularProgress from '@mui/material/CircularProgress';
import { pink } from '@mui/material/colors';

import RegisterForm from '../Components/RegisterForm';

import { Link } from 'react-router-dom';
import { useEffect } from 'react';


interface MapDispatcherToProps {
    sendRegisterRequest : (registerModel : RegisterActionPayloadInterface) => void;
}

interface MapStateToProps {
    isRegisterFetching : boolean,
    isAuthenticated : boolean,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    sendRegisterRequest : (registerModel : RegisterActionPayloadInterface) => dispatch(registerApiRequest(registerModel))
});

const mapStateToProps = (state : any) : MapStateToProps => ({
    isRegisterFetching : state.RegisterReducer.isRegisterFetching,
    isAuthenticated : state.LoginReducer.isAuthenticated,
});

const connector =  connect(mapStateToProps, mapDispatchToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;


const RegisterPage : React.FC<PropsFromRedux> = ({
    isRegisterFetching,
    isAuthenticated,
    sendRegisterRequest
}) : JSX.Element => {

    const classes = useStyles();
    const disptach = useDispatch();

    useEffect(() => {
        if(isAuthenticated == true){
            disptach(loginSetAuthenticated(false));
        }

    }, [isAuthenticated]);

    return(
        <div className={classes.root}>
            <RegisterForm sendRegisterReq={sendRegisterRequest} />
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
                    <Link className={classes.aboutLink} to='/about'> About Us?</Link>
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