import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

import { ThunkDispatch } from 'redux-thunk';
import { connect } from 'react-redux';

import {ResortObjectInterface} from '../Interfaces/ResortObject';

import {getAvailableResortObjectsApi} from '../Stores/ApiRequests/ResortObjectApiRequest';

interface MapDispatcherToProps {
    getAvailableResortObjects : (jwtToken : string) => void;
}

interface MapStateToProps {
    availableResortObjects  : Array<ResortObjectInterface>,
    isFetching : boolean,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    getAvailableResortObjects : (jwtToken : string) => dispatch(getAvailableResortObjectsApi(jwtToken))
});

const mapStateToProps = (state : any, accommodationProps : AccommodationDialogProps) : MapStateToProps => ({
    availableResortObjects : state.ResortObjectsReducer.availableResortObjects,
    isFetching : state.ResortObjectsReducer.isFetching,
});

interface AccommodationDialogProps{
    isOpen : boolean,
    propertyId : number,
    closeHandler : () => void,
}

type Props = MapStateToProps & MapDispatcherToProps & AccommodationDialogProps

const AccommodationDialogEdit : React.FC<Props> = ({
    availableResortObjects,
    isFetching,
    
    isOpen,
    propertyId,
    closeHandler,
}) => {

    return(
        <Dialog 
            open={isOpen} 
            onClose={closeHandler}
            fullWidth
            maxWidth='lg'
        >
            <DialogTitle>Accommodation with id {propertyId}</DialogTitle>
            <DialogContent
                style={{height:'600px'}}
            >
            <DialogContentText>
                Edit
            </DialogContentText>
            <TextField
                autoFocus
                margin="dense"
                id="name"
                label="Email Address"
                type="email"
                fullWidth
                variant="standard"
            />
            </DialogContent>
            <DialogActions>
                <Button onClick={closeHandler}>Cancel</Button>
                <Button onClick={closeHandler}>Subscribe</Button>
            </DialogActions>
        </Dialog>
    );
}

export default connect<MapStateToProps, MapDispatcherToProps, AccommodationDialogProps>(
    mapStateToProps,
    mapDispatchToProps
  )(AccommodationDialogEdit)
