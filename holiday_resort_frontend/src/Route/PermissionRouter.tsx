import AboutPage from '../Pages/AboutUs';
  
import PrivateRouter from '../Route/PrivateRouter';
import { connect, ConnectedProps  } from 'react-redux';

interface MapStateToProps {
    isAuthenticated : boolean,
}

const mapStateToProps = (state : any) : MapStateToProps => ({
    isAuthenticated : state.LoginReducer.isAuthenticated,
});

const connector =  connect(mapStateToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;

const PermissionRouter : React.FC<PropsFromRedux> = ({isAuthenticated}) => {

    return(
        <PrivateRouter 
            isAuth={isAuthenticated === true}
            path='/about'
        >
            <AboutPage/>
        </PrivateRouter>

    );
}

export default connector(PermissionRouter);