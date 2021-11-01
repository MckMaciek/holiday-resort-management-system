import {RegisterGenericAction} from "../../Interfaces/AuthOperations"
import {errorInterface} from "../../Interfaces/ErrorHandling";
import {AuthOperationRegisterTypes} from "../ActionTypes/AuthOperationTypes";
import {RegisterResponse} from "../../Interfaces/RegisterResponse";

interface INIT_STATE_REGISTER_INTERFACE {

    isRegisterInReducer : boolean,
    isRegisterFetching : boolean,
    registerStatusCode : RegisterResponse | null,
    error : errorInterface | null
};

const INIT_STATE_REGISTER : INIT_STATE_REGISTER_INTERFACE = {

    isRegisterInReducer : false,
    isRegisterFetching : false,
    registerStatusCode : null, 
    error : null
}


const RegisterReducer = (state : INIT_STATE_REGISTER_INTERFACE = INIT_STATE_REGISTER, action : RegisterGenericAction ) : INIT_STATE_REGISTER_INTERFACE => {
    
    switch(action.type){
        case AuthOperationRegisterTypes.REGISTER_ACTION : {
            return {...state, registerStatusCode : action.payload}
        }
        case AuthOperationRegisterTypes.REGISTER_FETCHING : {
            return {...state, isRegisterFetching : action.isFetching}
        }
        case AuthOperationRegisterTypes.REGISTER_SET_IN_REDUCER : {
            return {...state, isRegisterInReducer : action.isSet}
        }
        case AuthOperationRegisterTypes.REGISTER_PROCESS_ERROR : {
            return {...state, error : action.error}
        }

        default : {
            return state;
        }
    }
}
export default RegisterReducer;