import { useFormik } from 'formik';
import * as Yup from 'yup';

import {LoginActionPayloadInterface} from "../Interfaces/ReduxInterfaces/LoginActionPayload";
import { createStyles, makeStyles, Theme } from '@material-ui/core';

import {Link} from "react-router-dom";
import { useTranslation } from "react-i18next";

import Typography from '@mui/material/Typography';
import Button from '@material-ui/core/Button';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import Fade from '@mui/material/Fade';

import InputAdornment from '@mui/material/InputAdornment';
import IconButton from '@mui/material/IconButton';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import OutlinedInput from '@mui/material/OutlinedInput';
import { useState } from 'react';

interface FuncProps{
    sendLoginReq : (loginModel : LoginActionPayloadInterface) => void,
}

const LoginForm : React.FC<FuncProps> = ({sendLoginReq}) => {

    const classes = useStyles();
    const { t } = useTranslation();

    const headerName = t(`loginForm.header`);

    const [passwordVisible, setPasswordVisible] = useState<boolean>(false);

    const formik = useFormik({
        initialValues : {
            username : '',
            password : '',
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
                .trim(t(`formValidations.password-trim`))
                .min(8, t(`formValidations.password-min-characters`))
                .max(20, t(`formValidations.password-max-characters`))
                .matches(/[a-zA-Z]/, t(`formValidations.password-must-match-latin-letters`))
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
                        <Alert variant="filled" className={classes.validationBar} severity="warning">
                        <AlertTitle>{t(`Alerts.warning`)}</AlertTitle>
                            {formik.errors.password}
                        </Alert>
                    </Fade>
                ): null}

                {formik.errors.username && formik.touched.username ? (
                    <Fade in={formik.errors.username !== ""}>
                        <Alert variant="filled" className={classes.validationBar} severity="warning">
                            <AlertTitle>{t(`Alerts.warning`)}</AlertTitle>
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
                    <label className={classes.fieldLabels} htmlFor="username">{t(`loginForm.form-username`)} </label>
                    <OutlinedInput 
                        className={classes.inputField}
                        id="username"
                        type="text"
                        {...formik.getFieldProps('username')}
                        
                    />
 
                    <label className={classes.fieldLabels} htmlFor="password">{t(`loginForm.form-password`)}</label>
                    <OutlinedInput 
                        className={classes.inputField}
                        id="password" 
                        type={passwordVisible ? 'text' : 'password'}
                        {...formik.getFieldProps('password')}
                        endAdornment={
                            <InputAdornment position="end">
                            <IconButton
                                aria-label="toggle password visibility"
                                edge="end"
                                onClick={() => setPasswordVisible(!passwordVisible)}
                            >
                                {passwordVisible ? <VisibilityOff /> : <Visibility />}
                            </IconButton>
                            </InputAdornment>
                        }
                    />

                    <Button 
                    color="secondary" 
                    variant="contained" 
                    className={classes.submitButton}
                    type="submit"
                    >
                        {t(`loginForm.form-submit`)}
                    </Button>
                    <Link className={classes.redirectLink} to="/signup"> {t(`loginForm.no-account-link`)}</Link>
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
        minWidth : '16vw',
        minHeight: '3vh',
        marginBottom : '5%',
        background : 'white'
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