import {
    Route,
    Redirect
  } from 'react-router-dom';

const PrivateRouter = ({children, isAuth, ...rest}) => {

    return(
        <Route
            render={
                ({ location }) => {
                    return isAuth == true ? (children) : (
                        <Redirect
                            to={{
                                pathname: '/signin',
                                state : {
                                    from : location
                                }

                            }}
                        />
                    )
                }
            }
        >
        </Route>
    );
}
export default PrivateRouter;