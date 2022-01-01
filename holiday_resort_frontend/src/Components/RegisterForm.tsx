import { useFormik } from 'formik';
import * as Yup from 'yup';
import "yup-phone";

import {RegisterActionPayloadInterface} from "../Interfaces/ReduxInterfaces/RegisterActionPayload";

import { createStyles, makeStyles, Theme } from '@material-ui/core';
import { pink } from '@mui/material/colors';
import { useTranslation } from "react-i18next";

import {Link} from "react-router-dom";

import Button from '@material-ui/core/Button';
import Alert from '@mui/material/Alert';
import Typography from '@mui/material/Typography';
import Fade from '@mui/material/Fade';
import Checkbox from '@mui/material/Checkbox';
import { Redirect } from "react-router-dom";

interface FuncProps{
    sendRegisterReq : (registerModel : RegisterActionPayloadInterface) => void,
    isEmailSent : boolean,
    isEmailFetching : boolean,
}
 
const RegisterForm : React.FC<FuncProps> = ({sendRegisterReq, isEmailSent, isEmailFetching}) => {

    const classes = useStyles();
    const { t } = useTranslation();

    const formik = useFormik({
        initialValues : {
            username : '',
            password : '',
            passwordConfirmation : '',
            email : '',
            firstName : '',
            lastName : '',
            phoneNumber : '',
            acceptTerms: false,
        },

        validationSchema: Yup.object({
            username: Yup.string()
                .min(6, t(`formValidations.username-min-characters`))
                .max(15, t(`formValidations.username-max-characters`))
                .trim(t(`formValidations.username-trim`))
                .strict(true)
                .required(t(`formValidations.username-required`)),
            password: Yup.string()
                .required(t(`formValidations.password-required`)) 
                .strict(true)
                .trim(t(`formValidations.password-min-characters`))
                .min(8, t(`formValidations.password-min-characters`))
                .max(20, t(`formValidations.password-max-characters`))
                .matches(/[a-zA-Z]/, t(`formValidations.password-must-match-latin-letters`)),
            passwordConfirmation: Yup.string()
                .test('passwords-match', t(`formValidations.passwords-has-to-match`), function(value){
                    return this.parent.password === value
                }),
            email : Yup.string()
                .email(t(`formValidations.email-validation`))
                .max(50, t(`formValidations.email-max-characters`))
                .required(t(`formValidations.email-required`)),
            firstName : Yup.string()
                .min(1, t(`formValidations.firstName-min-characters`))
                .max(20, t(`formValidations.firstName-max-characters`))
                .trim(t(`formValidations.firstName-trim`))
                .strict(true)
                .required(t(`formValidations.firstName-required`)),
            lastName : Yup.string()
                .min(1, t(`formValidations.lastName-min-characters`))
                .max(20, t(`formValidations.lastName-max-characters`))
                .trim(t(`formValidations.lastName-trim`))
                .strict(true)
                .required(t(`formValidations.lastName-required`)), 
            phoneNumber : Yup.string()
                .phone()
                .required(t(`formValidations.phoneNumber-required`)),
            acceptTerms: Yup.bool()
                .oneOf([true], t(`formValidations.acceptTermsAndConditions-required`))

        }),
        onSubmit: values => {
            sendRegisterReq(values);
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

    const redirectToLogin = () => {
    
        return (        
            <Redirect   
            exact
            to={{
                pathname: '/signin',
                state : {
                    from : '/signup'
                }
            }}
            />
        )
    }

    return(

        <div className={classes.root}>
            <Typography className={classes.headerTitle} 
                align='center'
                variant='h3'
            >
                {t(`registerForm.header.firstPart`)} 
                <span className={classes.fontRed}>{t(`registerForm.header.secondColoredPart`)} </span>
            </Typography>

            {isEmailFetching ? 
            redirectToLogin()
            : null }

            <div className={classes.registerBox} >
                <form onSubmit={formik.handleSubmit}>
                    <div className={classes.userInputContainer}>

                    <label className={classes.fieldLabels} htmlFor="username">
                        {t(`registerForm.username`)}     
                    </label>
                    <div className={classes.validationContainer}>
                        <input
                            className={classes.userInput}
                            id="username"
                            type="text"
                            {...formik.getFieldProps('username')}
                        />
                        {showValidationAlert(formik.errors.username, formik.touched.username)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="password">
                        {t(`registerForm.password`)}   
                    </label>
                    <div className={classes.validationContainer}>
                        <input
                            className={classes.userInput}
                            id="password"
                            type="password"
                            {...formik.getFieldProps('password')}
                        />
                        {showValidationAlert(formik.errors.password, formik.touched.password)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="passwordConfirmation">
                        {t(`registerForm.password-confirm`)}  
                    </label>
                    <div className={classes.validationContainer}>
                        <input
                            className={classes.userInput}
                            id="passwordConfirmation"
                            type="password"
                            {...formik.getFieldProps('passwordConfirmation')}
                        />
                        {showValidationAlert(formik.errors.passwordConfirmation, formik.touched.passwordConfirmation)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="email">
                        {t(`registerForm.email`)}
                    </label>
                    <div className={classes.validationContainer}>
                        <input
                            id="email"
                            type="email"
                            className={classes.userInput}
                            {...formik.getFieldProps('email')}
                        />
                        {showValidationAlert(formik.errors.email, formik.touched.email)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="firstName">
                        {t(`registerForm.firstName`)}
                    </label>
                    <div className={classes.validationContainer}>
                        <input
                            id="firstName"
                            type="firstName"
                            className={classes.userInput}
                            {...formik.getFieldProps('firstName')}
                        />
                        {showValidationAlert(formik.errors.firstName, formik.touched.firstName)}
                    </div>
                        
                    <label className={classes.fieldLabels} htmlFor="lastName">
                        {t(`registerForm.lastName`)}
                    </label>
                    <div className={classes.validationContainer}>
                        <input
                            id="lastName"
                            type="lastName"
                            className={classes.userInput}
                            {...formik.getFieldProps('lastName')}
                        />
                        {showValidationAlert(formik.errors.lastName, formik.touched.lastName)}
                    </div>

                    <label className={classes.fieldLabels} htmlFor="phoneNumber">
                        {t(`registerForm.phoneNumber`)}
                    </label>
                    <div className={classes.validationContainer}>
                        <input
                            id="phoneNumber"
                            type="phoneNumber"
                            className={classes.userInput}
                            {...formik.getFieldProps('phoneNumber')}
                        />
                        {showValidationAlert(formik.errors.phoneNumber, formik.touched.phoneNumber)}
                    </div>

                    <label>
                    <div className={classes.validationContainer}>
                        <div className={classes.acceptTerms}>
                            <Checkbox
                            id="acceptTerms"
                            sx={{
                                color: pink[800],
                                '&.Mui-checked': {
                                  color: pink[600],
                                },
                              }}
                            {...formik.getFieldProps('acceptTerms')}
                            />
                                {t(`registerForm.acceptTermsAndConditions`)}
                        </div>
                        {showValidationAlert(formik.errors.acceptTerms, formik.touched.acceptTerms)}
                    </div>
                    </label>

                    <Button 
                    color="secondary" 
                    variant="contained" 
                    className={classes.submitButton}
                    type="submit"
                    >
                        {t(`registerForm.sign-up-submit`)}
                    </Button>
                </div>
            </form>
        </div>  
        <Link className={classes.redirectLink} to="/signin"> {t(`registerForm.already-have-an-account`)} </Link>                      
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

    acceptTerms : {
        display : 'block',
        textAlign : 'center',
        minWidth : '14vw',
        color : 'white',
        marginTop : '1vh',
        marginLeft : '1.3vw',
        marginBottom : '1vh',
        minHeight: '3.3Vh',
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
        marginTop : '2vh',
        marginBottom : '2vh',
        minWidth : '14vw',
        minHeight: '2vh',
        marginLeft : 'auto',
        marginRight : 'auto',
    },

    validationBar:{
        marginLeft : '3vw',
        marginRight : '3vw',
        marginBottom : '0.5wh',
        marginTop : '1vh',
        minHeight : '1vh',
        minWidth : "20vw",
    },
    redirectLink: {
        display : 'block',
        color : 'white',
        textDecoration : 'none',
        marginTop : '1vh',
        textAlign : 'center',
        marginBottom : '1vh',
    },
    headerTitle: {
        color : 'white',
        paddingBottom: '4vh',
        marginTop : '1vh',
    },

  }));

export default RegisterForm