import { useFormik } from 'formik';
import * as Yup from 'yup';
import "yup-phone";

import { createStyles, makeStyles, Theme } from '@material-ui/core';

import {Link} from "react-router-dom";

import Button from '@material-ui/core/Button';
import Alert from '@mui/material/Alert';
import Typography from '@mui/material/Typography';
import Fade from '@mui/material/Fade';

const RegisterForm = () => {

    const classes = useStyles();

    const formik = useFormik({
        initialValues : {
            username : '',
            password : '',
            passwordConfirmation : '',
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
            passwordConfirmation: Yup.string()
                .test('passwords-match', 'Passwords has to match', function(value){
                    return this.parent.password === value
                }),
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


    const showValidationAlert = (formikError : string | undefined, formikTouched : boolean | undefined) =>{

        if(formikError && formikTouched){
            return(
                    <Fade in={formikError !== ""}>
                    <Alert variant="filled" className={classes.validationBar} severity="warning">
                        {formikError}
                    </Alert>
                </Fade>
            )
        }
    }

    return(

        <div className={classes.root}>
            <Typography className={classes.headerTitle} 
                align='center'
                variant='h3'
            >
                Please introduce <span className={classes.fontRed}>Yourself.</span>
            </Typography>
            <div className={classes.registerBox} >
                <form onSubmit={formik.handleSubmit}>
                    <div className={classes.userInputContainer}>

                    <label className={classes.fieldLabels} htmlFor="username">Username </label>
                    <div className={classes.validationContainer}>
                        <input
                            className={classes.userInput}
                            id="username"
                            type="text"
                            {...formik.getFieldProps('username')}
                        />
                        {showValidationAlert(formik.errors.username, formik.touched.username)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="password">Password</label>
                    <div className={classes.validationContainer}>
                        <input
                            className={classes.userInput}
                            id="password"
                            type="password"
                            {...formik.getFieldProps('password')}
                        />
                        {showValidationAlert(formik.errors.password, formik.touched.password)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="passwordConfirmation">Password Confirm</label>
                    <div className={classes.validationContainer}>
                        <input
                            className={classes.userInput}
                            id="passwordConfirmation"
                            type="password"
                            {...formik.getFieldProps('passwordConfirmation')}
                        />
                        {showValidationAlert(formik.errors.passwordConfirmation, formik.touched.passwordConfirmation)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="email">E-mail</label>
                    <div className={classes.validationContainer}>
                        <input
                            id="email"
                            type="email"
                            className={classes.userInput}
                            {...formik.getFieldProps('email')}
                        />
                        {showValidationAlert(formik.errors.email, formik.touched.email)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="firstName">First name</label>
                    <div className={classes.validationContainer}>
                        <input
                            id="firstName"
                            type="firstName"
                            className={classes.userInput}
                            {...formik.getFieldProps('firstName')}
                        />
                        {showValidationAlert(formik.errors.firstName, formik.touched.firstName)}
                    </div>
                        
                    <label className={classes.fieldLabels} htmlFor="lastName">Last name</label>
                    <div className={classes.validationContainer}>
                        <input
                            id="lastName"
                            type="lastName"
                            className={classes.userInput}
                            {...formik.getFieldProps('lastName')}
                        />
                        {showValidationAlert(formik.errors.lastName, formik.touched.lastName)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="phoneNumber">Phone number</label>
                    <div className={classes.validationContainer}>
                        <input
                            id="phoneNumber"
                            type="phoneNumber"
                            className={classes.userInput}
                            {...formik.getFieldProps('phoneNumber')}
                        />
                        {showValidationAlert(formik.errors.phoneNumber, formik.touched.phoneNumber)}
                    </div>

                    <Button 
                    color="secondary" 
                    variant="contained" 
                    className={classes.submitButton}
                    type="submit"
                    >
                        Sign-Up
                    </Button>
                </div>
            </form>
        </div>  
        <Link className={classes.redirectLink} to="/signin"> Already have an account?</Link>                      
        </div>  
    );
};

const useStyles = makeStyles((theme: Theme) => createStyles({

    root : {
        display : 'flex',
        flexDirection : 'column',
        justifyContent : 'flex-start',
    },

    userInput : {
        display: 'block',
        minWidth : '14vw',
        height: '3.3Vh',
        marginBottom : '1vh',
        marginTop : '1%',
        borderRadius : '5px',
        marginLeft : '1vw',
    },

    fontRed : {
        color : '#ff4040',
    },

    validationContainer : {
        display : 'flex',
        flexDirection : 'row',
        alignItems : 'center',
    },

    registerBox : {
        display : 'flex',
        justifyContent : 'flex-start',
        boxSizing: 'content-box',
        flexDirection : 'column',
        border: '5px solid white',
        borderRadius : '15px',
        minWidth : '50vw',
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
        marginLeft : '1vw',
        minWidth : '1vw',
        minHeight: '3vh',
    },
    submitButton: {
        display : 'block',
        marginTop : '4vh',
        marginBottom : '3vh',
        minWidth : '14vw',
        minHeight: '3vh',
        marginLeft : 'auto',
        marginRight : 'auto',
    },

    validationBar:{
        marginLeft : '3vw',
        marginRight : '3vw',
        minHeight : '1vh',
        minWidth : "20vw",
    },
    redirectLink: {
        display : 'block',
        color : 'white',
        textDecoration : 'none',
        marginTop : '2vh',
        textAlign : 'center',
        marginBottom : '1vh',
    },
    headerTitle: {
        color : 'white',
        paddingBottom: '4vh',
    },

  }));

export default RegisterForm