import { useFormik } from 'formik';
import * as Yup from 'yup';

import {LoginActionPayloadInterface} from "../Interfaces/LoginActionPayload";
import { createStyles, makeStyles, Theme } from '@material-ui/core';

import {Link} from "react-router-dom";

import Typography from '@mui/material/Typography';
import Button from '@material-ui/core/Button';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import Fade from '@mui/material/Fade';

interface FuncProps{
    sendLoginReq : (loginModel : LoginActionPayloadInterface) => void,
}

const LoginForm : React.FC<FuncProps> = ({sendLoginReq}) => {

    const classes = useStyles();

    const headerName = "Holiday Resort";

    const formik = useFormik({
        initialValues : {
            username : '',
            password : '',
        },

        validationSchema: Yup.object({
            username: Yup.string()
                .min(6, 'Username must be 6 characters or more')
                .max(15, 'Username must be 15 characters or less')
                .trim('Username name cannot include leading and trailing spaces')
                .strict(true)
                .required('Username is required'),
            password: Yup.string()
                .required('Password is required') 
                .strict(true)
                .trim('Password cannot include leading and trailing spaces')
                .min(8, 'Password is too short - should be 8 chars minimum.')
                .max(20, 'Password must be 20 characters or less')
                .matches(/[a-zA-Z]/, 'Password can only contain Latin letters.')
        }),
        onSubmit: values => {
            sendLoginReq(values);
        },
    });

    return(

        <div className={classes.root}>
            <div className={classes.validationBar}>
                {formik.errors.password && formik.touched.password ? (
                    <Fade in={formik.errors.password !== ""}>
                        <Alert className={classes.validationBar} severity="warning">
                        <AlertTitle>Warning</AlertTitle>
                            {formik.errors.password}
                        </Alert>
                    </Fade>
                ): null}

                {formik.errors.username && formik.touched.username ? (
                    <Fade in={formik.errors.username !== ""}>
                        <Alert className={classes.validationBar} severity="warning">
                            <AlertTitle>Warning</AlertTitle>
                                {formik.errors.username}
                        </Alert>
                    </Fade>
                ): null}
            </div>

            <Typography className={classes.headerTitle}
            align='center'
            variant='h2'
            >
                {headerName}
            </Typography>

            <div className={classes.loginBox} >
                <form onSubmit={formik.handleSubmit}>
                    <label className={classes.fieldLabels} htmlFor="username">Username </label>
                    <input
                        className={classes.inputField}
                        id="username"
                        type="text"
                        {...formik.getFieldProps('username')}
                    />

                    <label className={classes.fieldLabels} htmlFor="password">Password</label>
                    <input
                        className={classes.inputField}
                        id="password"
                        type="password"
                        {...formik.getFieldProps('password')}
                    />

                    <Button 
                    color="secondary" 
                    variant="contained" 
                    className={classes.submitButton}
                    type="submit"
                    >
                        Log-In
                    </Button>
                    <Link className={classes.redirectLink} to="/signup"> No account? Sign up!</Link>
            </form>
        </div>  
        </div>  
    );
}

const useStyles = makeStyles((theme: Theme) => createStyles({

    root : {
        display : 'flex',
        flexDirection : 'column',
        alignItems : 'center',
        justifyContent : 'center',
    },

    loginBox : {
        display : 'flex',
        padding : "6vh 10vh",
        boxSizing: 'content-box',
        flexDirection : 'column',
        alignItems : 'center',
        justifyContent : 'center',
        border: '5px solid white',
        borderRadius : '15px',
},
    inputField: {
        display: 'block',
        minWidth : '14vw',
        minHeight: '3vh',
        marginBottom : '5%',
    },
    fieldLabels: {
        display : 'block',
        textAlign : 'center',
        minWidth : '14vw',
        color : 'white',
        marginBottom : '1%',
    },
    submitButton: {
        display : 'block',
        marginTop : '4vh',
        marginBottom : '2vh',
        minWidth : '14vw',
        minHeight: '3vh',
        marginLeft : 'auto',
        marginRight : 'auto',
    },
    invalidInput: {
        color : 'white',
    },
    validationBar:{
        marginBottom : "20px",
        width : "35vw",
    },
    redirectLink: {
        display : 'block',
        color : 'white',
        textDecoration : 'none',
        marginTop : '3vh',
        textAlign : 'center',
    },
    headerTitle: {
        color : 'white',
        paddingBottom: '4vh',
    },

  }));

export default LoginForm;