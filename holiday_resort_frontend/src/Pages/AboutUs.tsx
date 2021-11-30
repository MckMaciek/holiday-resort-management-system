import { createStyles, makeStyles, Theme } from '@material-ui/core';

import Typography from '@mui/material/Typography';
import Button from '@material-ui/core/Button';

const AboutPage = () => {

    const classes = useStyles();

    return(
        <div className={classes.root}>
            <Typography className={classes.aboutHeader} 
                align='center'
                variant='h2'
            >
                Holiday Resort Web Application
            </Typography>
            <Button 
                color="secondary" 
                variant="contained" 
                type="submit"
            >
                Return
            </Button>
        </div>
    )
}
export default AboutPage;

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        justifyContent : 'center',
        alignItems : 'center',
        backgroundColor: theme.palette.background.paper,
        minHeight: '100vh',
    },

    aboutHeader : {
        color : 'white',
    }
  }));