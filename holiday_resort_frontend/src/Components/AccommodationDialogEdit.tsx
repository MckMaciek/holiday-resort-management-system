import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import CircularProgress from '@mui/material/CircularProgress';
import { pink } from '@mui/material/colors';

import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';


import { ThunkDispatch } from 'redux-thunk';
import { connect } from 'react-redux';

import {ResortObjectInterface} from '../Interfaces/ResortObject';

import {getAvailableResortObjectsApi} from '../Stores/ApiRequests/ResortObjectApiRequest';
import { useEffect } from 'react';

interface MapDispatcherToProps {
    getAvailableResortObjects : (jwtToken : string) => void;
}

interface MapStateToProps {
    availableResortObjects  : Array<ResortObjectInterface>,
    isFetching : boolean,
    jwtToken : string,
}

const mapDispatchToProps = (dispatch : ThunkDispatch<{}, {}, any>) : MapDispatcherToProps => ({
    getAvailableResortObjects : (jwtToken : string) => dispatch(getAvailableResortObjectsApi(jwtToken))
});

const mapStateToProps = (state : any, accommodationProps : AccommodationDialogProps) : MapStateToProps => ({
    availableResortObjects : state.ResortObjectsReducer.availableResortObjects,
    isFetching : state.ResortObjectsReducer.isFetching,
    jwtToken : state.LoginReducer.jwt,
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
    jwtToken,
    getAvailableResortObjects,
    
    isOpen,
    propertyId,
    closeHandler,
}) => {

    useEffect(() => {
        if(jwtToken && jwtToken !== ""){
            getAvailableResortObjects(jwtToken);
        }

    }, [propertyId])

    const [choosenResortObject, setChoosenResortObject] = React.useState('');

    const handleChange = (event: SelectChangeEvent) => {
        setChoosenResortObject(event.target.value as string);
    };

    return(
        <>
            {console.log(availableResortObjects)}
            {isFetching && availableResortObjects.length === 0 ? (
                <CircularProgress 
                    size='7vh'
                    sx={{
                        color: pink[800],
                        '&.Mui-checked': {
                            color: pink[600],
                        },
                        }}
                    />) : (
                <Dialog 
                open={isOpen} 
                onClose={closeHandler}
                fullWidth
                maxWidth='lg'
                >
                <DialogTitle> Accommodation with id {propertyId}</DialogTitle>
                <DialogContent
                    style={{height:'600px'}}
                >
                <DialogContentText>
                    Edit
                </DialogContentText>
                <Box sx={{ minWidth: 120 }}>
                <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">Available Resort Objects </InputLabel>
                    <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={choosenResortObject}
                    label="Object"
                    onChange={handleChange}
                    >
                    {availableResortObjects.map(rO => {
                        return (
                            <MenuItem value={rO.objectName}>{rO.objectName}</MenuItem>
                        );
                    })}
                    </Select>
                </FormControl>
                </Box>
                </DialogContent>
                <DialogActions>
                    <Button onClick={closeHandler}>Cancel</Button>
                    <Button onClick={closeHandler}>Subscribe</Button>
                </DialogActions>
            </Dialog> 
            )}
        </>
    );
}

export default connect<MapStateToProps, MapDispatcherToProps, AccommodationDialogProps>(
    mapStateToProps,
    mapDispatchToProps
  )(React.memo(AccommodationDialogEdit))
