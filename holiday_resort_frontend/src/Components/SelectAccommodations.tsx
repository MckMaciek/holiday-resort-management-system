import * as React from 'react';

import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { ResortObjectInterface } from '../Interfaces/ResortObject';


interface Iprops {
    handleChange : () => void,
    availableResortObjects : Array<ResortObjectInterface>,
    choosenResortObjectId : number,
}

const SelectAccommodations : React.FC<Iprops> = ({
    handleChange,
    availableResortObjects,
    choosenResortObjectId,
}) => {

    return(
            <Box sx={{ minWidth: 120 }}>
                <FormControl fullWidth>
                    <InputLabel>Resort Objects Available</InputLabel>
                    <Select
                    value={choosenResortObjectId}
                    label="Object"
                    onChange={handleChange}
                    sx={{
                        marginBottom : '5%'
                    }}
                    >
                    {availableResortObjects.map(rO => {
                        return (
                            <MenuItem value={rO.id}>{rO.objectName}</MenuItem>
                        );
                    })}
                    </Select>
                </FormControl>
            </Box>
    );
}

export default SelectAccommodations;