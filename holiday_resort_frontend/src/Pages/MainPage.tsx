import { createStyles, makeStyles, Theme } from '@material-ui/core';

const MainPage = () : JSX.Element =>{

    const classes = useStyles();

    return(
        <div className = {classes.root}>
            <p> DSDASDSADSADSADSA </p>
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