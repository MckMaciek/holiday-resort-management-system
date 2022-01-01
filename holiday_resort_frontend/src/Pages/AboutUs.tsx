import { createStyles, makeStyles, Theme } from '@material-ui/core';
import { useHistory } from "react-router-dom";
import Typography from '@mui/material/Typography';
import Button from '@material-ui/core/Button';

const AboutPage = () => {

    const classes = useStyles();
    let history = useHistory();

    return(
        <div className={classes.root}>
            <Typography className={classes.aboutHeader} 
                align='center'
                variant='h2'
            >
                Holiday resort web application
            </Typography>
            <Button 
                color="secondary" 
                variant="contained" 
                type="submit"
                style={{marginTop : '5%', width : '10vw', height : '5vh'}}
                onClick={history.goBack}
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
        flexDirection : 'column',
        backgroundColor: theme.palette.background.paper,
        minHeight: '100vh',
    },

    aboutHeader : {
        color : 'white',
    }
  }));