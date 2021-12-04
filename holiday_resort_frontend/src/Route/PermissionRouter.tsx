import MainPage from '../Pages/MainPage';
import AdminPage from '../Pages/AdminPage';
  
import PrivateRouter from '../Route/PrivateRouter';
import { connect, ConnectedProps  } from 'react-redux';

import {RolesTypes} from '../Interfaces/Roles';

import {
    Switch
} from 'react-router-dom';


interface MapStateToProps {
    isAuthenticated : boolean,
    roles : Array<String>,
}

const mapStateToProps = (state : any) : MapStateToProps => ({
    isAuthenticated : state.LoginReducer.isAuthenticated,
    roles : state.LoginReducer.roles,
});

const connector =  connect(mapStateToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;

const PermissionRouter : React.FC<PropsFromRedux> = (
    {
        isAuthenticated,
        roles,
    }
    ) => {

    return(
        <Switch>
            <PrivateRouter
            isAuth={isAuthenticated && roles.includes(RolesTypes.ADMIN)}
            path='/system'
            >
                <AdminPage/>
            </PrivateRouter>

            <PrivateRouter 
            isAuth={isAuthenticated && roles.includes(RolesTypes.USER)}
            exact
            path='/'
            >
                <MainPage/>
            </PrivateRouter>
        </Switch>
    );
}

export default connector(PermissionRouter);