import MainPage from '../Pages/MainPage';
import AdminPage from '../Pages/AdminPage';
  
import PrivateRouter from '../Route/PrivateRouter';
import { connect, ConnectedProps  } from 'react-redux';

import {RolesTypes} from '../Enums/Roles';

import {
    Switch
    ,Route
} from 'react-router-dom';
import MiddleSectionRoute from './MiddleSectionRoute';
import { useLocation, useRouteMatch } from 'react-router';


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

    const { path, url } = useRouteMatch();

    return(
        <Switch>
            <PrivateRouter
            isAuth={isAuthenticated && roles.includes(RolesTypes.ADMIN)}
            path={`${path}system`}
            >
                <AdminPage/>
            </PrivateRouter>
                                   

            <PrivateRouter
            isAuth={isAuthenticated && roles.includes(RolesTypes.USER)}
            path={path}
            >
                <MainPage/>
            </PrivateRouter>

        </Switch>
    );
}

export default connect(mapStateToProps, null, null, {
    pure: false,
  })(PermissionRouter);