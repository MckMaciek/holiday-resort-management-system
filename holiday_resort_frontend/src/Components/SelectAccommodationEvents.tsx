import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Chip from '@mui/material/Chip';
import OutlinedInput from '@mui/material/OutlinedInput';

import {EventRequest} from '../Interfaces/EventRequest';

import {EventInterface} from "../Interfaces/Event";


const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;

const MenuProps = {
    PaperProps: {
      style: {
        maxHeight: ITEM_HEIGHT * 5.5 + ITEM_PADDING_TOP,
        width: 270,
      },
    },
};


interface IProps {
    eventType : string[],
    handleChangeEvent : (event: any) => void,
    chosenEvents : Array<EventRequest>,
    resortObjectEvents : Array<EventInterface>
}

const SelectAccommodationEvents : React.FC<IProps> = ({
    eventType,
    handleChangeEvent,
    chosenEvents,
    resortObjectEvents,

}) => {

    return(
        <div>
        {resortObjectEvents ? (
            <Box sx={{ minWidth: 6 }}>
            <FormControl sx={{ m: 2.5, minWidth: 500}} >
            <InputLabel id="chip-events-label">
                Resources
            </InputLabel>
            <Select
                multiple
                labelId="chip-events-label"
                value={eventType}
                input={<OutlinedInput id="chip-events-label" label="chip-events-label" />}
                onChange={handleChangeEvent}
                renderValue={(selected) => (
                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                    {selected.map((value) => (
                        <Chip key={value} label={value} />
                    ))}
                    </Box>
                )}
                MenuProps={MenuProps}
                >
                {    
                resortObjectEvents.map((event) => (
                    <MenuItem 
                    value={event.eventType}
                    >
                        {event.eventType} - Costs {event.price} Z??
                    </MenuItem>
                ))}
            </Select>
            {chosenEvents.length !== 0 ? (
            <p> Additional price for resources is : {
                chosenEvents.map(event => event.price).reduce((acc, sum) => acc + sum)
            } Z?? </p>
            ) : null}
        
        </FormControl>
        </Box>
    ) : null}
    </div>
    );
}

export default SelectAccommodationEvents;