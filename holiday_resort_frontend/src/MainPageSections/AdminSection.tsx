import { useRouteMatch } from "react-router";
import {Link} from "react-router-dom";
import Button from '@mui/material/Button';
import { useHistory } from "react-router-dom";
import { createStyles, makeStyles, Theme } from '@material-ui/core';

const AdminSection = () => {

    let history = useHistory();
    const classes = useStyles();

    return(
        <div className={classes.root}>
            <Button 
                variant="contained"
                type="submit"
                color="primary"
                onClick={history.goBack}
                >
                Get Back
            </Button>
     
        </div>
    );
}
export default AdminSection;

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        height : '70vh',
        display: 'flex',
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        color : 'white',
        flexGrow : 5,
        paddingBottom : '10vh',
    },
  }));
