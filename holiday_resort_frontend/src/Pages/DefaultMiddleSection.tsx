import { createStyles, makeStyles, Theme } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import Image from 'material-ui-image'

import HBO from '../HBO.png';

import { useRouteMatch } from "react-router";
import {Link} from "react-router-dom";

const DefaultMiddleSection = () => {

    let { path, url } = useRouteMatch();

    const classes = useStyles();

    return(
        <div className={classes.root}>
            <img src={HBO} alt="HBO" width="500" height="500"/> 
            <Link 
            style={{ textDecoration: 'none', color : 'white', marginTop : '4vh' }} 
            to={`${path}reservations`}
            >
                <Button 
                    style={
                        {
                        color : 'white',
                        background : '#161a31',
                        }
                    }
                    size="large"
                    variant="contained" 
                    color='secondary'
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

    mainHeader : {
        marginBottom : '25vh',
    }

  }));

export default DefaultMiddleSection;