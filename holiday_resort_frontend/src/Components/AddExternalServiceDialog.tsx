import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import * as React from 'react';
import Axios from 'axios';
import API_URL from "../API_URL.json";
import {ExternalService} from '../Interfaces/ExternalService';
import {ExternalServiceResponse} from '../Interfaces/ExternalServiceResponse';
import CircularProgress from '@mui/material/CircularProgress';
import { pink } from '@mui/material/colors';

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
}

const AddExternalServiceDialog : React.FC<ComponentProps> = ({
    isOpen,
    jwtToken,
    closeHandler,
    acceptHandler,
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
                    <SelectExternalService/>
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

        </DialogContent>
        <DialogActions>
    </DialogActions>
    </Dialog>
    );

}

export default AddExternalServiceDialog;