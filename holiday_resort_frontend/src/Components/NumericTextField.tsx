import TextField from '@mui/material/TextField';

interface IProps {
    id : string,
    label : string,
    type : string,
    onChange : (event : any) => void,
    defaultValue : string,
    optWidth : string,
}

const NumericTextField : React.FC<IProps> = ({
    id,
    label,
    type,
    onChange,
    defaultValue,
    optWidth,
}) => {

    return(
        <TextField
            id={id}
            sx={{width : optWidth ,marginTop : '1.5%', marginRight : '1.9%', marginBottom : '1.3%'}}
            label={label}
            defaultValue={defaultValue ? defaultValue : ''}
            type={type}
            onChange={onChange}
        />
    );

}
export default NumericTextField;