import Footer from "../Layout/Footer";
import Header from "../Layout/Header";
import MiddleSection from "../Layout/MiddleSection";

import { createStyles, makeStyles, Theme } from '@material-ui/core';

const PageComposition = () => {

    const classes = useStyles();

    return(
        <div className={classes.root}>
            <Header></Header>
            <MiddleSection></MiddleSection>
            <Footer></Footer>
        </div>
    );
}

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        backgroundColor: theme.palette.background.paper,
        flexDirection : 'column',
        color : 'white',
        minHeight : '100vh',
    },

  }));

export default PageComposition;