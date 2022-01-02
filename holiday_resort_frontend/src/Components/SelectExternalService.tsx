import {ExternalServiceResponse} from "../Interfaces/ExternalServiceResponse";
import Switch from '@mui/material/Switch';
import FormLabel from '@mui/material/FormLabel';
import FormControl from '@mui/material/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import { createRef, forwardRef, useEffect, useRef, useState } from "react";
import ButtonGroup from '@mui/material/ButtonGroup';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import * as React from 'react';
import { Calendar } from 'react-date-range';

interface ExternalServicesCheckBox extends ExternalServiceResponse {
    isSet : boolean,
    amountOfPeople : number,
    remarks : string,
    date : Date,
}

interface ExternalServiceRemarksInt {
    id : number,
    remarks : string,
}

interface ComponentProps {
    externalServices : Array<ExternalServiceResponse>,
    setExternalServicesCheckboxValues : React.Dispatch<React.SetStateAction<Array<ExternalServicesCheckBox>>>,
    externalServicesCheckboxValues : Array<ExternalServicesCheckBox>,
    setExternalServiceRemarks : React.Dispatch<React.SetStateAction<Array<ExternalServiceRemarksInt>>>,
}

const SelectExternalService : React.FC<ComponentProps> = ({
    externalServices,
    setExternalServicesCheckboxValues,
    externalServicesCheckboxValues,
    setExternalServiceRemarks,
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
                    remarks : '',
                    isNumberOfPeopleIrrelevant : externalService.isNumberOfPeopleIrrelevant,
                    date : new Date(),
                }]);
            });
        }
        
    }, [])

    
    const sendValue = (index : any, operation : string) => {

        let selectedCheckBox = externalServicesCheckboxValues.filter(checkbox => checkbox.id === parseInt(index));
        let checkboxIndex = externalServicesCheckboxValues.indexOf(selectedCheckBox[0]);

        setExternalServicesCheckboxValues(ext => [...ext.slice(0, checkboxIndex),{
                ...selectedCheckBox[0],
                amountOfPeople : (operation === 'add' ? selectedCheckBox[0].amountOfPeople + 1 :  Math.max(selectedCheckBox[0].amountOfPeople - 1, 0)),
            }, ...ext.slice(++checkboxIndex)]);
     }

     const changeDate = (index : any, item : any) => {

        let selectedCheckBox = externalServicesCheckboxValues.filter(checkbox => checkbox.id === parseInt(index));
        let checkboxIndex = externalServicesCheckboxValues.indexOf(selectedCheckBox[0]);

        setExternalServicesCheckboxValues(ext => [...ext.slice(0, checkboxIndex),{
                ...selectedCheckBox[0],
                date : item,
            }, ...ext.slice(++checkboxIndex)]);
     }


    const handleSelectChange = async (event: React.ChangeEvent<HTMLInputElement>) => {

        let selectedCheckBox = externalServicesCheckboxValues.filter(checkbox => checkbox.id === parseInt(event.target.name));
        let index = externalServicesCheckboxValues.indexOf(selectedCheckBox[0]);

        if(selectedCheckBox[0].id === parseInt(event.target.name)){

            if(event.target.checked === true){
                await setExternalServicesCheckboxValues(ext => [...ext.slice(0,index),{
                    ...selectedCheckBox[0],
                    isSet : event.target.checked,
                }, ...ext.slice(++index)]);
            }
            else {
                await setExternalServicesCheckboxValues(ext => [...ext.slice(0,index),{
                    ...selectedCheckBox[0],
                    isSet : event.target.checked,
                    amountOfPeople : 0,
                }, ...ext.slice(++index)]);
            }
        }
    }

    return(
        <div>
            {externalServicesCheckboxValues && externalServicesCheckboxValues.length === externalServices.length ? (
                
                <FormControl 
                    component="fieldset"
                    sx={{display : 'flex'}}
                >

                    <FormLabel 
                        component="legend"
                        sx={{marginTop : '5%', marginBottom : '5%'}}
                        >
                            <Typography
                            >
                                Available External Services :
                            </Typography>  
                    </FormLabel>
                    <FormGroup>
                    <>
                        {externalServicesCheckboxValues.map((externalServicesChkbx) => (
                            <>
                            {console.log(externalServicesChkbx)}   
                            <FormControlLabel
                            control={
                                <Switch 
                                    checked={externalServicesChkbx.isSet} 
                                    onChange={handleSelectChange} 
                                    sx={{marginTop : '5%', marginBottom : '5%', marginRight : '1.7%'}}
                                    name={externalServicesChkbx.id.toString()} 
                                    size="medium"
                                    edge="end"
                                    color="secondary"
                                />
                            }
                            label={!externalServicesChkbx.isNumberOfPeopleIrrelevant ? `${externalServicesChkbx.serviceName} - costs ${externalServicesChkbx.cost} zł per person` : `${externalServicesChkbx.serviceName}`}
                            />

                            {externalServicesChkbx.isSet ? (

                                <Calendar 
                                    onChange={item => changeDate(externalServicesChkbx.id, item)}
                                    date={externalServicesChkbx.date} 
                                />

                            ) : null}

                            {!externalServicesChkbx.isNumberOfPeopleIrrelevant && externalServicesChkbx.isSet ? (
                                
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
                                        style={{marginLeft : '5%'}}
                                    >   
                                        {externalServicesChkbx.amountOfPeople}  People 
                                    </span> 

                                </ButtonGroup>
                            ) : null}
                            </>     
                        ))}
                    </>
                    </FormGroup>
                    <FormGroup>
                    </FormGroup>
                </FormControl>

            ) : null}

        </div>
    );
}

export default SelectExternalService;