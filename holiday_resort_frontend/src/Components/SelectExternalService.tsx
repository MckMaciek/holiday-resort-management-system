import {ExternalServiceResponse} from "../Interfaces/ExternalServiceResponse";
import Switch from '@mui/material/Switch';
import FormLabel from '@mui/material/FormLabel';
import FormControl from '@mui/material/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import { useEffect, useRef, useState } from "react";
import ButtonGroup from '@mui/material/ButtonGroup';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import Typography from '@mui/material/Typography';


interface ExternalServicesCheckBox extends ExternalServiceResponse {
    isSet : boolean,
    amountOfPeople : number,
}
interface ComponentProps {
    externalServices : Array<ExternalServiceResponse>,
    setExternalServicesCheckboxValues : React.Dispatch<React.SetStateAction<Array<ExternalServicesCheckBox>>>,
    externalServicesCheckboxValues : Array<ExternalServicesCheckBox>,
}

const SelectExternalService : React.FC<ComponentProps> = ({
    externalServices,
    setExternalServicesCheckboxValues,
    externalServicesCheckboxValues,
}) => {

    useEffect(() => {
        
        if(externalServicesCheckboxValues.length === 0){
            externalServices.forEach(externalService => {
                setExternalServicesCheckboxValues(extChkbx => [...extChkbx, {
                    id : externalService.id,
                    cost : externalService.cost,
                    serviceName : externalService.serviceName,
                    isSet : false,
                    amountOfPeople : 0,
                }]);
            });
        }
        
    }, [])
    
    const sendValue = (index : any, operation : string) => {

        let selectedCheckBox = externalServicesCheckboxValues.filter(checkbox => checkbox.id === parseInt(index));
        let checkboxIndex = externalServicesCheckboxValues.indexOf(selectedCheckBox[0]);

        setExternalServicesCheckboxValues(ext => [...ext.slice(0, checkboxIndex),{
                ...selectedCheckBox[0],
                amountOfPeople : (operation === 'add' ? selectedCheckBox[0].amountOfPeople + 1 : selectedCheckBox[0].amountOfPeople - 1),
            }, ...ext.slice(++checkboxIndex)]);
     }

    const handleSelectChange = async (event: React.ChangeEvent<HTMLInputElement>) => {

        let selectedCheckBox = externalServicesCheckboxValues.filter(checkbox => checkbox.id === parseInt(event.target.name));
        let index = externalServicesCheckboxValues.indexOf(selectedCheckBox[0]);

        await setExternalServicesCheckboxValues(ext => [...ext.slice(0,index),{
                id : selectedCheckBox[0].id,
                isSet : event.target.checked,
                cost : selectedCheckBox[0].cost,
                serviceName : selectedCheckBox[0].serviceName,
                amountOfPeople : selectedCheckBox[0].amountOfPeople,
            }, ...ext.slice(++index)]);
    }

    
    console.log(externalServicesCheckboxValues)
    return(

        <div>

            {externalServicesCheckboxValues && externalServicesCheckboxValues.length === externalServices.length ? (
                
                <FormControl component="fieldset">

                    <FormLabel 
                        component="legend"
                        sx={{marginTop : '5%', marginBottom : '5%'}}
                        >
                            Choose available External Services
                    </FormLabel>
                    <FormGroup>
                    <>
                        {externalServicesCheckboxValues.map((externalServicesChkbx, index) => (
                            <>
                                
                                <FormControlLabel
                                control={
                                    <Switch 
                                    checked={externalServicesChkbx.isSet} 
                                    onChange={handleSelectChange} 
                                    name={externalServicesChkbx.id.toString()} 
                                    size="medium"
                                    />
                                }
                                label={`${externalServicesChkbx.serviceName} - costs ${externalServicesChkbx.cost} zł`}
                                />

                            <ButtonGroup
                                disabled={!externalServicesChkbx.isSet}
                            >  
                                <Button
                                    aria-label="reduce"
                                    onClick={() => {
                                        sendValue(externalServicesChkbx.id, 'sub');
                                    }}
                                >
                                    <RemoveIcon fontSize="small" />
                                </Button>
                                <Button
                                    aria-label="increase"
                                    onClick={() => {
                                        sendValue(externalServicesChkbx.id, 'add');
                                    }}
                                >
                                    <AddIcon fontSize="small" />
                                </Button>

                                <span
                                    style={{marginLeft : '55%'}}
                                >   
                                    {externalServicesChkbx.amountOfPeople}  People 
                                </span>    
                            </ButtonGroup>

           
                            </>     
                        ))}
                    </>
                    </FormGroup>
                </FormControl>

            ) : null}

        </div>
    );
}

export default SelectExternalService;