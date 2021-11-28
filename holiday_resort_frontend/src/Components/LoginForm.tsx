import { useFormik } from 'formik';
import * as Yup from 'yup';

import {LoginActionPayloadInterface} from "../Interfaces/LoginActionPayload";
import { createStyles, makeStyles, Theme } from '@material-ui/core';

import Button from '@material-ui/core/Button';
import Alert from '@mui/material/Alert';
import Fade from '@mui/material/Fade';

interface FuncProps{
    sendLoginReq : (loginModel : LoginActionPayloadInterface) => void,
}

const LoginForm : React.FC<FuncProps> = ({sendLoginReq}) => {

    const classes = useStyles();

    const formik = useFormik({
        initialValues : {
            username : '',
            password : '',
        },

        validationSchema: Yup.object({
            username: Yup.string()
                .max(15, 'Username must be 15 characters or less')
                .required('Username is required'),
            password: Yup.string()
                .required('No password provided.') 
                .min(8, 'Password is too short - should be 8 chars minimum.')
                .matches(/[a-zA-Z]/, 'Password can only contain Latin letters.')
        }),
        onSubmit: values => {
            alert(JSON.stringify(values, null, 2));
            sendLoginReq(values);
        },
    });

    return(

        <div className={classes.root}>
            <div className={classes.validationBar}>
                {formik.errors.password && formik.touched.password ? (
                    <Fade in={formik.errors.password !== ""}>
                        <Alert className={classes.validationBar} severity="error">{formik.errors.password}</Alert>
                    </Fade>
                ): null}

                {formik.errors.username && formik.touched.username ? (
                    <Fade in={formik.errors.username !== ""}>
                        <Alert className={classes.validationBar} severity="error">{formik.errors.username}</Alert>
                    </Fade>
                ): null}
            </div>

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
        padding : "5vh",
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
        marginTop : '15%',
        marginBottom : '2%',
        minWidth : '14vw',
        minHeight: '3vh',
    },
    invalidInput: {
        color : 'white',
    },
    validationBar:{
        marginBottom : "20px",
        width : "100%",
    }

  }));

export default LoginForm;