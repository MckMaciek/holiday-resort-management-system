import { createStyles, makeStyles, Theme } from '@material-ui/core';


import ReservationTable from '../Components/ReservationTable';


const ReservationSection = () => {

    const classes = useStyles();

    return(
        <div className={classes.root}>
            <h1 className={classes.reservationHeader}> User reservations </h1>
            {ReservationTable()}
        </div>
    );

}

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        display: 'flex',
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        color : 'white',
        flexGrow : 5,
        paddingBottom : '10vh',
    },
    reservationHeader : {
        marginBottom : 'vh',
    }
  }));

export default ReservationSection;