import { useFormik } from 'formik';
import * as Yup from 'yup';
import "yup-phone";

import { createStyles, makeStyles, Theme } from '@material-ui/core';

import {Link} from "react-router-dom";

import Typography from '@mui/material/Typography';
import Button from '@material-ui/core/Button';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import Fade from '@mui/material/Fade';

const RegisterForm = () => {

    const classes = useStyles();

    const formik = useFormik({
        initialValues : {
            username : '',
            password : '',
            email : '',
            firstName : '',
            lastName : '',
            phoneNumber : '',
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
                .matches(/[a-zA-Z]/, 'Password can only contain Latin letters.'),
            email : Yup.string()
                .email('Must be a valid email')
                .max(50, "Email is too big")
                .required('Email is required'),
            firstName : Yup.string()
                .min(1, 'First name must be 1 characters or more')
                .max(20, 'First name must be 20 characters or less')
                .trim('First name cannot include leading and trailing spaces')
                .strict(true)
                .required('First name is required'),
            lastName : Yup.string()
                .min(1, 'Last name must be 1 characters or more')
                .max(20, 'Last name must be 20 characters or less')
                .trim('Last name cannot include leading and trailing spaces')
                .strict(true)
                .required('Last name is required'), 
            phoneNumber : Yup.string()
                .phone()
                .required('Phone number is required')

        }),
        onSubmit: values => {
            alert('elo');
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

            <div className={classes.registerBox} >
                <form onSubmit={formik.handleSubmit}>
                    <div className={classes.userInputContainer}>
                        <label className={classes.fieldLabels} htmlFor="username">Username </label>
                        <input
                            className={classes.userPersonalInput}
                            id="username"
                            type="text"
                            {...formik.getFieldProps('username')}
                        />

                        <label className={classes.fieldLabels} htmlFor="password">Password</label>
                        <input
                            className={classes.userPersonalInput}
                            id="password"
                            type="password"
                            {...formik.getFieldProps('password')}
                        />

                        <label className={classes.fieldLabels} htmlFor="email">E-mail</label>
                        <input
                            id="email"
                            type="email"
                            className={classes.userPersonalInput}
                            {...formik.getFieldProps('email')}
                        />

                        <label className={classes.fieldLabels} htmlFor="firstName">First name</label>
                        <input
                            id="firstName"
                            type="firstName"
                            className={classes.userPersonalInput}
                            {...formik.getFieldProps('firstName')}
                        />

                        <label className={classes.fieldLabels} htmlFor="lastName">Last name</label>
                        <input
                            id="lastName"
                            type="lastName"
                            className={classes.userPersonalInput}
                            {...formik.getFieldProps('lastName')}
                        />

                        <label className={classes.fieldLabels} htmlFor="phoneNumber">Phone number</label>
                        <input
                            id="phoneNumber"
                            type="phoneNumber"
                            className={classes.userPersonalInput}
                            {...formik.getFieldProps('phoneNumber')}
                        />

                        <Button 
                        color="secondary" 
                        variant="contained" 
                        className={classes.submitButton}
                        type="submit"
                        >
                            Sign-Up
                        </Button>
                        <Link className={classes.redirectLink} to="/signin"> Already have an account?</Link>                      
                    </div>
            </form>
        </div>  
        </div>  
    );
};

const useStyles = makeStyles((theme: Theme) => createStyles({

    root : {
        display : 'flex',
        flexDirection : 'column',
        justifyContent : 'flex-start',
    },

    userPersonalInput : {
        display: 'block',
        minWidth : '14vw',
        minHeight: '3vh',
        marginBottom : '1vh',
    },

    registerBox : {
        display : 'flex',
        justifyContent : 'flex-start',
        boxSizing: 'content-box',
        flexDirection : 'column',
        border: '5px solid white',
        borderRadius : '15px',
    },

    userInputContainer: {
        marginTop : '1vh',
        marginLeft : '1vw',
    },

    fieldLabels: {
        display : 'inline',
        textAlign : 'center',
        color : 'white',
        marginTop : '1vh',
        minWidth : '1vw',
        minHeight: '3vh',
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
        marginBottom : '1vh',
    },
    headerTitle: {
        color : 'white',
        paddingBottom: '4vh',
    },

  }));

export default RegisterForm