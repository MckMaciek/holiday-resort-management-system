import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps  } from 'react-redux';

import {RegisterActionPayloadInterface} from '../Interfaces/RegisterActionPayload';
import registerApiRequest from "../Stores/ApiRequests/RegisterApiRequest";

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

    const testRegisterModel = {
        username : "Maciej123",
        password : "Musial",
        email : "mckmusial14323@gmail.com",
        firstName : "J",
        lastName : "J",
        phoneNumber : "666666666"
    }

    return(
        <>
            {console.log(isRegisterFetching)};
            <p> TUTAJ JEST TO REJESTRACJA {isRegisterFetching} </p>
            <button onClick={() => sendRegisterRequest(testRegisterModel)}> 
                CLICK ME
            </button>
        </>
    );

}
export default connector(RegisterPage);