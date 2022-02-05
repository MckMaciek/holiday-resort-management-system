import { createStyles, makeStyles, Theme } from '@material-ui/core';

const Footer = () => {

    const classes = useStyles();

    return(
        <div className={classes.root}> Holiday Web Resort </div>
    );
}

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        color : 'white',
        paddingBottom : '5vh',
        background : theme.palette.background.paper,
    },

  }));

export default Footer;