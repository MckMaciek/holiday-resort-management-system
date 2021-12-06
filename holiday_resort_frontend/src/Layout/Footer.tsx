import { createStyles, makeStyles, Theme } from '@material-ui/core';

const Footer = () => {

    const classes = useStyles();

    return(
        <div className={classes.root}> Stopka taka szczególna dla harcerzy </div>
    );
}

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        color : 'white',
        paddingTop : '5vh',
        paddingBottom : '5vh',
        background : theme.palette.background.paper,
    },

  }));

export default Footer;