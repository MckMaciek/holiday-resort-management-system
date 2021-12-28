import * as React from 'react';
import Box from '@mui/material/Box';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';


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
        <Box sx={{ margin: 1, width : '100%' }}>
        <Table >  
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
                        {reservationRemark.author}
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