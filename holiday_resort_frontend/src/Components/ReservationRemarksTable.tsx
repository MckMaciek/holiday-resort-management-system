import * as React from 'react';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import Typography from '@mui/material/Typography';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';

import {ReservationRemarksInterface} from "../Interfaces/Reservation";

interface IProps {
    reservationRemarks : Array<ReservationRemarksInterface>,
}

const ReservationRemarksTable : React.FC<IProps> = ({
    reservationRemarks,
}) => {
    {console.log(reservationRemarks)}
    return(
    <>
        {reservationRemarks.length !== 0 ? (
        <Box sx={{ margin: 1 }}>
        <Table>  
            <TableHead>
                <TableRow>
                    <TableCell  align="center"> Topic</TableCell>
                    <TableCell  align="center"> Description </TableCell>
                    <TableCell  align="center"> Creation Date</TableCell>
                    <TableCell  align="center"> Modification Date </TableCell>
                    <TableCell  align="center"> Author </TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
            {reservationRemarks.map(reservationRemark => (
                <TableRow key={reservationRemark.id}>
                    <TableCell 
                    component="th" 
                    scope="row"
                    >
                        {reservationRemark.topic}
                    </TableCell>
                    <TableCell 
                    component="th" 
                    scope="row"
                    >
                        {reservationRemark.description}
                    </TableCell>
                    <TableCell 
                    component="th" 
                    scope="row"
                    >
                        {reservationRemark.creationDate}
                    </TableCell>
                    <TableCell 
                    component="th" 
                    scope="row"
                    >
                        {!!!reservationRemark.modificationDate ? "" : reservationRemark.modificationDate}
                    </TableCell>
                    <TableCell 
                    component="th" 
                    scope="row"
                    >
                        Autor na razie domyślny
                    </TableCell>
                </TableRow>
            ))}
            </TableBody>
        </Table>
        </Box>
        ) : null}
    </>
    );

};

export default ReservationRemarksTable