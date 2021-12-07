import { createStyles, makeStyles, Theme } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import Image from 'material-ui-image'

import { useRouteMatch } from "react-router";
import {Link} from "react-router-dom";

const DefaultMiddleSection = () => {

    let { path, url } = useRouteMatch();

    const classes = useStyles();

    return(
        <div className={classes.root}>
            <h1> DEFAULT MIDDLE SECTION ! </h1>
            <Link 
            style={{ textDecoration: 'none', color : 'white', marginTop : '4vh' }} 
            to={`${path}reservations`}
            >
                <Button 
                    style={
                        {
                        background : '#311b92', 
                        color : 'white',
                        }
                    }
                    size="large"
                    variant="contained" 
                    type="submit"
                    >
                    See my reservations!
                    </Button>
            </Link>
        </div>
    );

}

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        backgroundColor: theme.palette.background.paper,
        flexGrow : 5,
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        color : 'white',
    },

  }));

export default DefaultMiddleSection;