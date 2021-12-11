import TextField from '@mui/material/TextField';

interface IProps {
    id : string,
    label : string,
    type : string,
    variant : string,
    onChange : (event : any) => void,
}

const NumericTextField : React.FC<IProps> = ({
    id,
    label,
    type,
    variant,
    onChange,
}) => {

    return(
        <TextField
            id="filled-search"
            label="Number of people"
            type="search"
            variant="filled"
            //onChange={(event) => setNumberOfPeople(parseInt(event.target.value))}
            onChange={onChange}
        />
    );

}
export default NumericTextField;