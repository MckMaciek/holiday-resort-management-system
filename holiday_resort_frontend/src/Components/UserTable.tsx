import Button from '@mui/material/Button';
import * as React from 'react';

import {User} from "../Interfaces/User";

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

import AdminReservationView from "../Components/AdminReservationView";

interface UserTable {
    userList : Array<User>,
}

const UserTable : React.FC<UserTable> = ({
    userList
}) => {

    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
  
    const handleChangePage = (event: unknown, newPage: number) => {
      setPage(newPage);
    };
  
    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
      setRowsPerPage(+event.target.value);
      setPage(0);
    };

    interface ViewReservations {
        userId : number,
        isOpen : boolean,
    }

    const VIEW_RESERVATIONS_DEFAULT : ViewReservations = {
        userId : -1,
        isOpen : false,
    }

    const [viewReservations, setViewReservations] = React.useState<ViewReservations>(VIEW_RESERVATIONS_DEFAULT);

    return(
        <TableContainer component={Paper}>
            <Table 
            >
                <TableHead>
                <TableRow>
                    <TableCell  align="left"> User Unique Id</TableCell>
                    <TableCell  align="left"> User First Name</TableCell>
                    <TableCell  align="left"> User Last Name</TableCell>
                    <TableCell  align="left"> User Email</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                    {userList.map(user => (
                        <>
                            <TableRow 
                                hover
                                key={user.id}
                                onClick={() => setViewReservations({
                                    userId : user.id,
                                    isOpen : true,
                                })}
                                >
                                <TableCell 
                                component="th" 
                                scope="row"
                                >
                                    {user.id}
                                </TableCell>
                                <TableCell 
                                component="th" 
                                scope="row"
                                >
                                    {user.firstName}
                                </TableCell>
                                <TableCell 
                                component="th" 
                                scope="row"
                                >
                                    {user.lastName}
                                </TableCell>
                                <TableCell 
                                component="th" 
                                scope="row"
                                >
                                    {user.email}
                                </TableCell>

                                </TableRow>
                                {viewReservations.isOpen && viewReservations.userId === user.id  ? (
                                    <AdminReservationView
                                        isOpen={viewReservations.isOpen}
                                        userId={user.id}
                                        userName={user.firstName}
                                        reservationList={user.reservationResponses}
                                        closeHandler={() => setViewReservations(VIEW_RESERVATIONS_DEFAULT)}
                                        acceptHandler={() => setViewReservations(VIEW_RESERVATIONS_DEFAULT)}
                                    />
                                ) : null}
                            </>
                    ))}
                </TableBody>
            </Table>
            <TablePagination
                rowsPerPageOptions={[1,5,15]}
                component="div"
                count={4}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </TableContainer>


    );
}

export default UserTable;