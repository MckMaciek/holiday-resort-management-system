import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';

import {RegisterActionPayloadInterface} from '../Interfaces/RegisterActionPayload';
import registerApiRequest from "../Stores/ApiRequests/RegisterApiRequest";
import { createStyles, makeStyles, Theme } from '@material-ui/core';

import Typography from '@mui/material/Typography';
import QuestionMarkIcon from '@mui/icons-material/QuestionMark';

import RegisterForm from '../Components/RegisterForm';

import { Link } from 'react-router-dom';


interface MapDispatcherToProps {
    sendRegisterRequest : (registerModel : RegisterActionPayloadInterface) => void;
}

interface MapStateToProps {
    isRegisterFetching : boolean,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    sendRegisterRequest : (registerModel : RegisterActionPayloadInterface) => dispatch(registerApiRequest(registerModel))
});

const mapStateToProps = (state : any) : MapStateToProps => ({
    isRegisterFetching : state.RegisterReducer.isRegisterFetching,
});

const connector =  connect(mapStateToProps, mapDispatchToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;


const RegisterPage : React.FC<PropsFromRedux> = ({
    isRegisterFetching,
    sendRegisterRequest
}) : JSX.Element => {

    const classes = useStyles();

    return(
        <div className={classes.root}>
            <RegisterForm />
            <Typography className={classes.aboutHeader} 
            align='center'
            variant='h3'
            >
                <Link className={classes.aboutLink} to='/about'> About Us?</Link>
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

    aboutLink : {
        color : 'white',
        textDecoration : 'none',
        textAlign : 'center',
        marginLeft : '10vw',
        marginRight : '5vw',
    },

    spinner: {
        marginBottom: '3vh',
    }
    
  }));
export default connector(RegisterPage);