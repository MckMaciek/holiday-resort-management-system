import {BrowserRouter as Router} from 'react-router-dom';
import LoginPage from '../Pages/LoginPage';
import MainPage from '../Pages/MainPage';
import RegisterPage from '../Pages/RegisterPage';

import {
    Route,
    Switch
  } from 'react-router-dom';


const AppRouter = (props : any) =>{
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

            </Switch>
        </Router>
    );
}

export default AppRouter;