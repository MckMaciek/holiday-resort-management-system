import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Chip from '@mui/material/Chip';
import OutlinedInput from '@mui/material/OutlinedInput';

import {EventRequest} from '../Interfaces/EventRequest';
import {ResortObjectInterface} from '../Interfaces/ResortObject';

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
    choosenResortObjectId : number,
    resortObjectEvents : Array<EventInterface>
}

const SelectAccommodationEvents : React.FC<IProps> = ({
    eventType,
    handleChangeEvent,
    chosenEvents,
    choosenResortObjectId,
    resortObjectEvents,

}) => {


    return(
        <Box sx={{ minWidth: 120 }}>
        <FormControl sx={{ m: 1, minWidth: 600}} >
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
                    {event.eventType} - Costs {event.price} Zł
                </MenuItem>
            ))}
        </Select>
        {chosenEvents.length !== 0 ? (
        <p> Additional price for resources is : {
            chosenEvents.map(event => event.price).reduce((acc, sum) => acc + sum)
        } Zł </p>
        ) : null}
    
    </FormControl>
    </Box>
    );
}

export default SelectAccommodationEvents;