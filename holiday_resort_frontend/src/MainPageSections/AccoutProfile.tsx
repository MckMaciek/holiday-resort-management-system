import { createStyles, makeStyles, Theme } from '@material-ui/core';
import Typography from '@mui/material/Typography';
import { connect, ConnectedProps  } from 'react-redux';
import {UserInfoResponse} from '../Interfaces/UserInfoResponse';
import Button from '@mui/material/Button';
import Paper from "@material-ui/core/Paper";
import { pink } from '@mui/material/colors';
import Divider from '@mui/material/Divider';
import BungalowIcon from '@mui/icons-material/Bungalow';

import ResetPasswordDialog from '../Components/ResetPasswordDialog';
import { useState } from 'react';

interface MapStateToProps {
    jwtToken : string,
    roles : Array<String>,
    userDetails : UserInfoResponse,

    username : string,
    email : string,
}

const mapStateToProps = (state : any) : MapStateToProps => ({

    jwtToken : state.LoginReducer.jwt,
    roles : state.LoginReducer.roles,
    userDetails : state.RegisterReducer.userDetails,

    username : state.LoginReducer.username,
    email : state.LoginReducer.email,
});

const connector =  connect(mapStateToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;

const AccountProfile : React.FC<PropsFromRedux> = ({
    userDetails,
    roles,
    username,
    email,

}) => {

    const classes = useStyles();

    interface ResertPasswordDialog {
        isSet : boolean,
    }

    const DEFAUlt_RESET_PASSWORD_DIALOG : ResertPasswordDialog = {
        isSet : false,
    }

    const [resertPasswordDialog, setResetPasswordDialog] = useState<ResertPasswordDialog>(DEFAUlt_RESET_PASSWORD_DIALOG);

    return(
        <>
        {userDetails && username && email &&  roles.length !== 0 ? (
            <>
                <Typography
                    variant="h4"
                    sx={{marginTop : '0.5%', marginLeft : '5vw', marginBottom : '1%', color: '#1E90FF'}}
                >
                    My account profile
                </Typography>
            <div className={classes.root}>
                <div className={classes.userBox}>
                <Paper elevation={16} style={{ minWidth : '50vw', color : 'white', padding : '1%'}}>
                    <p style={{fontSize : '1.2rem', marginBottom : '5%', marginLeft : '1%'}}> <BungalowIcon style={{marginRight : '1%'}}/> Username  : {username} </p>
                    <p style={{fontSize : '1.2rem', marginBottom : '5%', marginLeft : '1%'}}> <BungalowIcon style={{marginRight : '1%'}}/>Email  : {email}</p>
                    <p style={{fontSize : '1.2rem', marginBottom : '5%', marginLeft : '1%'}}> <BungalowIcon style={{marginRight : '1%'}}/>First Name  : {userDetails.firstName} </p>
                    <p style={{fontSize : '1.2rem', marginBottom : '5%', marginLeft : '1%'}}> <BungalowIcon style={{marginRight : '1%'}}/>Last Name  : {userDetails.lastName} </p>
                    <p style={{fontSize : '1.2rem', marginBottom : '5%', marginLeft : '1%'}}> <BungalowIcon style={{marginRight : '1%'}}/>Phone Number  : {userDetails.phoneNumber} </p>
                    <p style={{fontSize : '1.2rem', marginBottom : '5%', marginLeft : '1%'}}> <BungalowIcon style={{marginRight : '1%'}}/>Account types : </p>
                    {roles.map(role => (
                        <span style={{marginLeft : '1%'}}> {role} , </span>
                    ))}
                </Paper>
                </div>
                <div className={classes.userOperations}>
                    <div style={{minHeight : '50vh', minWidth : '100%', color : 'white', padding : '1%'}}>
                        <Button 
                            variant="contained"
                            type="submit"
                            color="primary"
                            style={{minWidth : '10vw', minHeight : '5vh'}}
                            onClick={() => setResetPasswordDialog({isSet : true})}
                        >
                        Reset password
                    </Button>
                    </div>

                {resertPasswordDialog && resertPasswordDialog.isSet ? (
                    <ResetPasswordDialog
                        isOpen={resertPasswordDialog.isSet}
                        onAcceptHandler={() => setResetPasswordDialog({isSet : false})}
                        onCloseHandler={() => setResetPasswordDialog({isSet : false})}
                    />
                ) : null}

                </div>
            </div>
            </>
        ) : null}
        </>
    );
}

export default connector(AccountProfile);

const useStyles = makeStyles((theme: Theme) => createStyles({
    root: {
        minHeight : '70vh',
        display: 'flex',
        flexDirection : 'row',
        color : 'white',
        paddingBottom : '3vh',
        fontFamily : 'Arial',
    },

    userBox : {
        display : 'flex',
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'flex-start',
        marginLeft : '5vw',
        paddingBottom : '1%',
    },

    userOperations : {
        display : 'flex',
        flexDirection : 'column',
        justifyContent : 'center',
        alignItems : 'center',
        marginLeft : '5vw',
    }

  }));
