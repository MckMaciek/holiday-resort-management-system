import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import OutlinedInput from '@mui/material/OutlinedInput';
import { useState } from 'react';
import InputAdornment from '@mui/material/InputAdornment';
import IconButton from '@mui/material/IconButton';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

interface DialogProps {
    isOpen : boolean,
    onAcceptHandler : () => void,
    onCloseHandler : () => void,
}

const ResetPasswordDialog : React.FC<DialogProps> = ({
    isOpen,
    onAcceptHandler,
    onCloseHandler,

}) => {

    interface PasswordInput {
        password : string,
        showPassword : boolean,
    }

    const DEFAULT_PASSWORD_INPUT = {
        password : '',
        showPassword : false,
    }

    const [password, setPassword] = useState<PasswordInput>(DEFAULT_PASSWORD_INPUT);
    const [confirmPassword, setConfirmPassword] = useState<PasswordInput>(DEFAULT_PASSWORD_INPUT);
    const [newPassword, setNewPassword] = useState<PasswordInput>(DEFAULT_PASSWORD_INPUT);

    return(
        <Dialog
            open={isOpen}
            onClose={onCloseHandler}
        >
            <DialogTitle> Change Your Credentials </DialogTitle>
            <DialogContent sx={{minHeight : '15vh', minWidth : '15vw'}}>
                <div style={{display : 'flex', flexDirection: 'column', justifyContent : 'center', alignItems : 'center'}}>
                    <OutlinedInput 
                        style={{marginBottom : '2%', marginTop : '3%', width : '100%'}} 
                        id="outlined-basic" 
                        type={password.showPassword ? 'text' : 'password'}
                        placeholder="Current"
                        onChange={(event) => setPassword(password => ({
                            ...password,
                            password : event.target.value,
                        }))} 
                        endAdornment={
                            <InputAdornment position="end">
                              <IconButton
                                aria-label="toggle password visibility"
                                onClick={() => setPassword(password => ({
                                    ...password,
                                    showPassword : !password.showPassword,
                                }))}
                                edge="end"
                              >
                                {password.showPassword ? <VisibilityOff /> : <Visibility />}
                              </IconButton>
                            </InputAdornment>
                          }
                    />

                    <OutlinedInput 
                        style={{marginBottom : '2%', width : '100%'}} 
                        id="outlined-basic" 
                        type={confirmPassword.showPassword ? 'text' : 'password'}
                        placeholder="New" 
                        onChange={(event) => setConfirmPassword(password => ({
                            ...password,
                            password : event.target.value,
                        }))} 
                        endAdornment={
                            <InputAdornment position="end">
                              <IconButton
                                aria-label="toggle password visibility"
                                onClick={() => setConfirmPassword(password => ({
                                    ...password,
                                    showPassword : !password.showPassword,
                                }))}
                                edge="end"
                              >
                                {confirmPassword.showPassword ? <VisibilityOff /> : <Visibility />}
                              </IconButton>
                            </InputAdornment>
                          }
                    />

                    <OutlinedInput 
                        style={{width : '100%'}}
                        id="outlined-basic" 
                        type={newPassword.showPassword ? 'text' : 'password'}
                        placeholder="Confirm new" 
                        onChange={(event) => setNewPassword(password => ({
                            ...password,
                            password : event.target.value,
                        }))}
                        endAdornment={
                            <InputAdornment position="end">
                              <IconButton
                                aria-label="toggle password visibility"
                                onClick={() => setNewPassword(password => ({
                                    ...password,
                                    showPassword : !password.showPassword,
                                }))}
                                edge="end"
                              >
                                {newPassword.showPassword ? <VisibilityOff /> : <Visibility />}
                              </IconButton>
                            </InputAdornment>
                          }
                    />
                
                </div>
            </DialogContent>
            
            <DialogActions>
                <Button onClick={onCloseHandler}> Cancel </Button>
                <Button onClick={onAcceptHandler} autoFocus>
                    Change
                </Button>
        </DialogActions>

        </Dialog>
    );
} 
export default ResetPasswordDialog;