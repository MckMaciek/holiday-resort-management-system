import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';

interface DialogProps {
    isOpen : boolean,
    closeHandler : () => void,
    onAcceptHandler : () => void,
    dialogTitle : string,
    dialogDescription : string,
    disagreeText : string,
    agreeText : string,
}

const DialogConfirm : React.FC<DialogProps> = ({
    isOpen,
    closeHandler,
    onAcceptHandler,
    dialogTitle,
    dialogDescription,
    disagreeText,
    agreeText,
}) => {

    return(
        <Dialog
            open={isOpen}
            onClose={closeHandler}
        >
            <DialogTitle> {dialogTitle} </DialogTitle>
            <DialogContent>
                <DialogContentText>
                    {dialogDescription}
                </DialogContentText>
            </DialogContent>
            
            <DialogActions>
                <Button onClick={closeHandler}>{disagreeText}</Button>
                <Button onClick={onAcceptHandler} autoFocus>
                    {agreeText}
                </Button>
        </DialogActions>

        </Dialog>
    );
}
export default DialogConfirm;