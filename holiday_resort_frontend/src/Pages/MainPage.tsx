import { createStyles, makeStyles, Theme } from '@material-ui/core';
import {Link} from "react-router-dom";

const MainPage = () : JSX.Element =>{

    const classes = useStyles();

    return(
        <div className = {classes.root}>
            <p> DSDASDSADSADSADSA </p>
            <Link  to="/system"> Link dla admina</Link>
        </div>
    );

}
export default MainPage;


const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        backgroundColor: theme.palette.background.paper,
        minHeight: '100vh',
    }
  }));