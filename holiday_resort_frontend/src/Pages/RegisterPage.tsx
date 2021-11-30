import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';

import {RegisterActionPayloadInterface} from '../Interfaces/RegisterActionPayload';
import registerApiRequest from "../Stores/ApiRequests/RegisterApiRequest";
import { createStyles, makeStyles, Theme } from '@material-ui/core';
import RegisterForm from '../Components/RegisterForm';

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
export default connector(RegisterPage);