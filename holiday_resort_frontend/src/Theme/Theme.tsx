import {createTheme } from "@material-ui/core/styles"

import {MuiThemeProvider } from "@material-ui/core/styles";
import AppRouter from '../Route/AppRouter';

const theme = createTheme({
    palette : {
        background : {
            paper: '#161A31',
        },
        text :{
            primary : '#616A94',
            secondary : '#F3F6FB',
        }
    }
});

const Theming = (props : any) : JSX.Element =>{
    return(
        <MuiThemeProvider theme={theme} >
            <AppRouter/>
        </MuiThemeProvider>
    );
}

export default Theming;