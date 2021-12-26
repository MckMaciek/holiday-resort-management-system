import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import * as React from 'react';
import Axios from 'axios';
import API_URL from "../API_URL.json";
import {ExternalServiceResponse} from '../Interfaces/ExternalServiceResponse';
import CircularProgress from '@mui/material/CircularProgress';
import { pink } from '@mui/material/colors';
import Button from '@mui/material/Button';
import { Dispatch, SetStateAction } from "react";
import {NewReservationRequest} from '../Interfaces/NewReservationRequest';
import {ExternalServiceRequest} from '../Interfaces/ExternalServiceRequest';

import SelectExternalService from './SelectExternalService';


const fetchExternalServices = async (jwtToken : string) : Promise<Array<ExternalServiceResponse>> => {

    const config = {
        headers: { Authorization: `Bearer ${jwtToken}` },
        'Content-Type': 'application/json',
    };

    const resortObjectEvents = await Axios.get(
            `${API_URL.SERVER_URL}${API_URL.GET_AVAILALBE_EXTERNAL_SERVICES}`, config);
        
    return resortObjectEvents.data as Array<ExternalServiceResponse>;
}

interface ComponentProps {
    isOpen : boolean,
    closeHandler : () => void,
    acceptHandler : () => void,
    jwtToken : string,
    modifyReservation : Dispatch<SetStateAction<NewReservationRequest>>,
}

const AddExternalServiceDialog : React.FC<ComponentProps> = ({
    isOpen,
    jwtToken,
    closeHandler,
    acceptHandler,
    modifyReservation,
})  => {

    React.useEffect(() => {
        if(jwtToken && jwtToken !== ""){
            (async () => {
                const fetchedExternalServices =  await fetchExternalServices(jwtToken);
                setExternalServices(fetchedExternalServices);
            })()
        }
    }, [])

    const [externalServices, setExternalServices] = React.useState<Array<ExternalServiceResponse>>([]);

    interface ExternalServicesCheckBox extends ExternalServiceResponse {
        isSet : boolean,
        remarks : string,
        amountOfPeople : number,
    }

        
    interface ExternalServiceRemarksInt {
        id : number,
        remarks : string,
    }

    const NEW_EXTENRAL_SERVICE_REMARKS_DEFAULT : ExternalServiceRemarksInt = {
        id : -1,
        remarks : '',
    }
    
    const [externalSericeRemarks, setExternalServiceRemarks] = React.useState<Array<ExternalServiceRemarksInt>>([]);

    const [externalServicesCheckboxValues, setExternalServicesCheckboxValues] = React.useState<Array<ExternalServicesCheckBox>>([])

    const addExternalServiceToReservation = () => {

        let externalServicesRequest : Array<ExternalServiceRequest> = []

        externalServicesCheckboxValues.forEach(externalService => {

            if(externalService.isSet){
                let externalServiceObj : ExternalServiceRequest = {
                    serviceRequestId : externalService.id,
                    amountOfPeople : externalService.amountOfPeople,
                    remarks : externalService.remarks,
                }
                externalServicesRequest.push(externalServiceObj);
            }
            console.log(externalServicesRequest)
        });

        modifyReservation(reservation => ({
            ...reservation,
            externalServicesRequests : externalServicesRequest
        }));
    }

    return(
        <Dialog
        open={isOpen}
        fullWidth
        onClose={closeHandler}
    >
        <DialogTitle> 
            <Typography
                    variant="h5"
                    style={{textAlign : 'center', marginBottom : '1%'}}
            >

                 Select External Service 
            </Typography>
            <Divider />
        </DialogTitle>
        <DialogContent            
        >
            {externalServices.length !== 0 ? (
                <div>
                    <SelectExternalService
                        externalServices={externalServices}
                        setExternalServicesCheckboxValues={setExternalServicesCheckboxValues}
                        externalServicesCheckboxValues={externalServicesCheckboxValues}
                        setExternalServiceRemarks={setExternalServiceRemarks}
                    />
                </div>
            ) : (
            <CircularProgress 
            size='7vh'
            sx={{
                color: pink[800],
                '&.Mui-checked': {
                    color: pink[600],
                },
                }}
            />
            )}
            {externalServices.length !== 0 ? (
                <Button
                    aria-label="reduce"
                    sx={{marginTop : '4%'}}
                    onClick={() => {
                        
                        addExternalServiceToReservation();
                        acceptHandler();
                    }
                    }
                >
                    Ok
                </Button>
            ) : null}

        </DialogContent>
        <DialogActions>
    </DialogActions>
    </Dialog>
    );

}

export default AddExternalServiceDialog;