import {User} from "../../Interfaces/User";
import {ManageUsersGenericAction} from "../../Interfaces/ReduxInterfaces/ManageUserOperations";
import {ManageUserOperations} from "../ActionTypes/ManageUsersOperations";

interface INIT_STATE {

    users : Array<User>,
    isFetching : boolean,
    error : boolean,
};

const INIT_STATE_USERS : INIT_STATE = {

    users : [],
    isFetching : false,
    error : false,
}

const ManageUsersReducer = (state : INIT_STATE = INIT_STATE_USERS, action : ManageUsersGenericAction) : INIT_STATE => {

    switch(action.type){

        case ManageUserOperations.GET_USERS : {
            return {...state, users : action.users}
        }

        case ManageUserOperations.USERS_ERROR : {
            return {...state, error : action.error}
        }

        case ManageUserOperations.USERS_FETCHING : {
            return {...state, isFetching : action.isFetching}
        }

        default : {
            return state;
        }
    }
}
export default ManageUsersReducer;