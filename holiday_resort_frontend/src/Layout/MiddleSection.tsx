import { createStyles, makeStyles, Theme } from '@material-ui/core';
import MiddleSectionRoute from '../Route/MiddleSectionRoute';

const MiddleSection = () => {

    const classes = useStyles();

    return(
        <> 
            <MiddleSectionRoute/>
        </>
    );
}

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        backgroundColor: theme.palette.background.paper,
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        color : 'white',
    },

  }));

  export default MiddleSection;