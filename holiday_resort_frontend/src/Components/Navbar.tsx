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
import { useRouteMatch } from "react-router";
import {Link} from "react-router-dom";

import { useHistory } from "react-router-dom";
import { ThunkDispatch } from 'redux-thunk';
import { connect, ConnectedProps, useDispatch } from 'react-redux';
import { useTranslation } from "react-i18next";


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
    const [anchorHamburger, setAnchorHamburger] = React.useState<null | HTMLElement>(null);

    let { path, url } = useRouteMatch();
    let history = useHistory();
    const { t } = useTranslation();
    const dispatch = useDispatch();


    const handleMenu = (event: React.MouseEvent<HTMLElement>) => setAnchorEl(event.currentTarget);
    const handleHamburger = (event: React.MouseEvent<HTMLElement>) => setAnchorHamburger(event.currentTarget);


    const handleClose = () => setAnchorEl(null);
    const handleHamburgerClose = () => setAnchorHamburger(null);
    

    const onLogout = () => {
        dispatch(loginSetAuthenticated(false));
    }

    const redirectToProfile = () => {

        const userProfile = {
            pathname: '/profile',
            state: { fromDashboard: true }
        }

        history.push(userProfile)
    }

    const redirectToReservations = () => {

        const userReservations = {
            pathname: '/reservations',
            state: { fromDashboard: true }
        }

        history.push(userReservations)
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
                    onClick={handleHamburger}
                    sx={{ mr: 2 }}
                >
                <MenuIcon />
                </IconButton>
                <Menu
                    id="menu-appbar"
                    anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'left',
                    }}
                    keepMounted
                    open={Boolean(anchorHamburger)}
                    onClose={handleHamburgerClose}
                >
                    <MenuItem onClick={redirectToReservations}>Reservations</MenuItem>
                </Menu>

                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    {t(`navbar.loggedAs`)} {username}
                </Typography>

                {roles.includes(RolesTypes.ADMIN) ? (
                <Link 
                    style={{textDecoration: 'none', marginRight : '3.5%'}} 
                    to={`${path}admin`}
                >
                    <Button 
                        variant="contained"
                        type="submit"
                        color="primary"
                        >
                        {t(`navbar.managerPanel`)}
                    </Button>
                </Link>

                ) : null}

                <Button 
                    variant="contained"
                    type="submit"
                    color="primary"
                    onClick={onLogout}
                    style={{marginRight : '0.7%'}}
                    >
                    {t(`navbar.contactUs`)}
                </Button>

                <Button 
                    variant="outlined"
                    type="submit"
                    color="inherit"
                    onClick={onLogout}
                    >
                    {isAuthenticated ? t(`navbar.LogOut`) : t(`navbar.LogIn`)}
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
                        <MenuItem onClick={handleClose}> {t(`navbar.profile`)} </MenuItem>
                        <MenuItem onClick={redirectToProfile}> {t(`navbar.myAccount`)} </MenuItem>
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