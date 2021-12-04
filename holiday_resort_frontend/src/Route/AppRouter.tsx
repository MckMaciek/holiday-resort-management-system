import {BrowserRouter as Router} from 'react-router-dom';

import AboutPage from '../Pages/AboutUs';
import LoginPage from '../Pages/LoginPage';
import RegisterPage from '../Pages/RegisterPage';

import PermissionRouter from './PermissionRouter';

import {
    Route,
    Switch
  } from 'react-router-dom';

const AppRouter = () =>{

    return(
        <Router>
            <Switch>
                    <Route path='/signin'> 
                       <LoginPage/>
                    </Route>

                    <Route path='/signup'> 
                       <RegisterPage/>
                    </Route>

                    <Route path='/about'> 
                        <AboutPage/>
                    </Route>

                    <PermissionRouter/>
            </Switch>
        </Router>
    );
}

export default AppRouter;