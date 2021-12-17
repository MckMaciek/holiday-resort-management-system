import * as React from 'react';

import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { ResortObjectInterface } from '../Interfaces/ResortObject';


interface Iprops {
    handleChange : (event : any) => void,
    availableResortObjects : Array<ResortObjectInterface>,
    choosenResortId : string,
}

const SelectAccommodations : React.FC<Iprops> = ({
    handleChange,
    availableResortObjects,
    choosenResortId,
}) => {

    return(
            <Box sx={{padding : '10%', width : '86%'}}>
                <FormControl fullWidth>
                    <InputLabel>Resort Objects</InputLabel>
                    <Select
                    value={choosenResortId}
                    onChange={handleChange}
                    >
                    {availableResortObjects.map(rO => {
                        return (
                            <MenuItem 
                            value={rO.id}
                            >
                                {rO.objectName}      
                            </MenuItem>
                        );
                    })}
                    </Select>
                </FormControl>
            </Box>
    );
}

export default SelectAccommodations;