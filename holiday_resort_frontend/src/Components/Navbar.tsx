import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import AccountCircle from '@mui/icons-material/AccountCircle';
import Switch from '@mui/material/Switch';
import FormControlLabel from '@mui/material/FormControlLabel';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';

import { Redirect } from "react-router-dom";
import {RolesTypes} from '../Enums/Roles';

import {loginSetAuthenticated} from '../Stores/Actions/AuthOperations';


import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps, useDispatch } from 'react-redux';


interface MapStateToProps {
    isAuthenticated : boolean,
    username : string,

    roles : Array<string>,
}

const mapStateToProps = (state : any) : MapStateToProps => ({
    isAuthenticated : state.LoginReducer.isAuthenticated,
    username : state.LoginReducer.username,

    roles : state.LoginReducer.roles,
});

const connector =  connect(mapStateToProps);
type PropsFromRedux = ConnectedProps<typeof connector>;


const Navbar : React.FC<PropsFromRedux> = ({
    isAuthenticated,
    username,
    roles,
}) : JSX.Element => {

    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

    const dispatch = useDispatch();

    const handleMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const onLogout = () => {
        dispatch(loginSetAuthenticated(false));
    }

    return(
        <>
        {isAuthenticated && roles && roles.length !== 0 ? (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar 
            position="static"
            sx={{
                background : '#161a31',
            }}
            >
                <Toolbar>
                <IconButton
                    size="large"
                    edge="start"
                    color="inherit"
                    aria-label="menu"
                    sx={{ mr: 2 }}
                >
                <MenuIcon />
                </IconButton>
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    Hi {username} !
                </Typography>

                {roles.includes(RolesTypes.ADMIN) ? (

                <Button 
                    variant="contained"
                    type="submit"
                    color="primary"
                    onClick={onLogout}
                    style={{marginRight : '3.5%'}}
                    >
                    Manager Panel
                </Button>

                ) : null}

                <Button 
                    variant="contained"
                    type="submit"
                    color="primary"
                    onClick={onLogout}
                    style={{marginRight : '0.7%'}}
                    >
                    Contact Us
                </Button>

                <Button 
                    variant="outlined"
                    type="submit"
                    color="inherit"
                    onClick={onLogout}
                    >
                    {isAuthenticated ? 'Logout' : 'Login'}
                </Button>

                {isAuthenticated && (
                    <div>
                    <IconButton
                        sx={{ ml: 5 }}
                        size="large"
                        aria-label="Current user account"
                        aria-controls="menu-appbar"
                        aria-haspopup="true"
                        color="inherit"
                        onClick={handleMenu}
                    >
                    <AccountCircle />
                    </IconButton>
                    <Menu
                        id="menu-appbar"
                        anchorOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        keepMounted
                        open={Boolean(anchorEl)}
                        onClose={handleClose}
                    >
                        <MenuItem onClick={handleClose}>Profile</MenuItem>
                        <MenuItem onClick={handleClose}>My account</MenuItem>
                    </Menu>
                    </div>
                )}
                </Toolbar>
            </AppBar>
            </Box>
        ) : (
            <Redirect
                to={{
                    pathname: '/signin',
                    state : {
                        from : '/'
                    }
                }}
            />
        )}
        </>
    );
}

export default connector(Navbar);