import {LoginActionPayloadInterface} from '../Interfaces/LoginActionPayload';
import loginApiRequest from '../Stores/ApiRequests/LoginApiRequest';

import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';

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

    const testLoginModel = {
        username : "123",
        password : "123",
    }

    return(
        <>
            {console.log(isLoginFetching)};
            <p> TUTAJ JEST TO {isLoginFetching} </p>
            <button onClick={() => sendLoginRequest(testLoginModel)}> 
                CLICK ME
            </button>
        </>
    );
}

export default connector(LoginPage);