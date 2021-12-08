import {
    Route,
    Switch
  } from 'react-router-dom';

import PrivateRouter from '../Route/PrivateRouter';
import { connect, ConnectedProps  } from 'react-redux';
import {RolesTypes} from '../Enums/Roles';
import {useRouteMatch } from 'react-router';
import {Link} from "react-router-dom";

import ReservationSection from '../Sections/ReservationSection';
import DefaultMiddleSection from '../Sections/DefaultMiddleSection';

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

const MiddleSectionRoute : React.FC<PropsFromRedux> = ({
    isAuthenticated,
    roles,
}) => {

    let { path, url } = useRouteMatch();

    return(
        <>
            <Switch>  
                <PrivateRouter 
                    isAuth={isAuthenticated && roles.includes(RolesTypes.USER)}
                    exact
                    path={path}
                    >
                        <DefaultMiddleSection/>
                </PrivateRouter>

                <PrivateRouter 
                    isAuth={isAuthenticated && roles.includes(RolesTypes.USER)}
                    path={`${path}reservations`}
                    >
                        <ReservationSection/>
                </PrivateRouter>
            </Switch>
        </>
    );
}

export default connect(mapStateToProps, null, null, {
    pure: false,
  })(MiddleSectionRoute);