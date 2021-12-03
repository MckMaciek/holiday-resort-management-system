import {BrowserRouter as Router} from 'react-router-dom';
import LoginPage from '../Pages/LoginPage';
import MainPage from '../Pages/MainPage';
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
                    <Route exact path='/'>
                        <MainPage/>
                    </Route>

                    <Route path='/signin'> 
                       <LoginPage/>
                    </Route>

                    <Route path='/signup'> 
                       <RegisterPage/>
                    </Route>

                    <PermissionRouter/>
            </Switch>
        </Router>
    );
}

export default AppRouter;