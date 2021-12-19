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
import Button from '@material-ui/core/Button';
import TablePagination from '@mui/material/TablePagination';
import SendIcon from '@mui/icons-material/Send';

import DeleteIcon from '@mui/icons-material/Delete';
import ModeEditIcon from '@mui/icons-material/ModeEdit';

import DialogConfirm from '../Components/DialogConfirm';
import AccommodationDialogEdit from '../Components/AccommodationDialogEdit';
import SummaryDialog from '../Components/SummaryDialog';

import ReservationRemarksTable from './ReservationRemarksTable'; 

import { TableContext } from '../MainPageSections/ReservationSection';

import { ReservationInterface } from '../Interfaces/Reservation';
import { createStyles, makeStyles, Theme } from '@material-ui/core';
import { RolesTypes } from '../Enums/Roles';

const Row = ({row} : any) => {

  const [open, setOpen] = React.useState(false);
  const [remarksTableOpen, setRemarksTableOpen] = React.useState(false);

  interface DELETE_DIALOG_CONFIRMATION_INTERFACE {
    isSet : boolean,
    id : number,
    propertyName : string,
  }

  interface EDIT_DIALOG_INTERFACE {
    isSet : boolean,
    id : number,
    propertyName : string,
  }

  interface SUMMARY_DIALOG_INTERFACE {
    isSet : boolean,
    id : number,
  }

  const DELETE_DIALOG_CONFIRMATION_DEFAULT : DELETE_DIALOG_CONFIRMATION_INTERFACE = {
    isSet : false,
    id : -1,
    propertyName : "",
  }
  const [deleteDialog, setDeleteDialog] = React.useState<DELETE_DIALOG_CONFIRMATION_INTERFACE>(DELETE_DIALOG_CONFIRMATION_DEFAULT);
  
  const EDIT_DIALOG_DEFAULT : EDIT_DIALOG_INTERFACE = {
    isSet : false,
    id : -1,
    propertyName : "",
  }
  const [editDialog, setEditDialog] = React.useState<EDIT_DIALOG_INTERFACE>(EDIT_DIALOG_DEFAULT);


  const SUMMARY_DIALOG_DEFAULT : SUMMARY_DIALOG_INTERFACE = {
    isSet : false,
    id : -1,
  }

  const [summaryDialog, setSummaryDialog] = React.useState<SUMMARY_DIALOG_INTERFACE>(SUMMARY_DIALOG_DEFAULT);

  
  const classes = useStyles();
  const TableContextImp = React.useContext(TableContext);


  return (
    <React.Fragment>
      <TableRow key={row.key} sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            size="large"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          <p> {row.reservationName} </p>
        </TableCell>
        <TableCell align="center">{row.reservationDate}</TableCell>
        <TableCell align="center">{row.reservationEndingDate}</TableCell>
        <TableCell align="center">{row.finalPrice}</TableCell>
        <TableCell align="center">{row.reservationStatus}</TableCell>
        <TableCell align="center">
            <Button variant="outlined" size="medium" color="secondary" endIcon={<SendIcon />}
            onClick={() => setSummaryDialog(
              {
                isSet : true,
                id : row.id,
              }              
              )}
            >
            Show
            </Button>

            {summaryDialog.isSet && summaryDialog.id === row.id ? (
                      <SummaryDialog
                        submit="Send"
                        cancel='Close'
                        open={summaryDialog.isSet}
                        reservation={row}
                        handleClose={() => setSummaryDialog(SUMMARY_DIALOG_DEFAULT)}
                        handleAccept={() => setSummaryDialog(SUMMARY_DIALOG_DEFAULT)}
                      />
            ) : null}

        </TableCell> 
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={4}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
            <Table 
            >
            <TableHead>
            <TableRow>
                <TableCell  align="left"> Object Name</TableCell>
                <TableCell  align="center"> Object Type</TableCell>
                <TableCell  align="center"> People</TableCell>
            </TableRow>
            </TableHead>
            <TableBody>
            {row.accommodationResponses.map((innerRow) => (
                <TableRow key={innerRow.resortObject.id}>
                  <TableCell 
                  component="th" 
                  scope="row"
                  >
                      {innerRow.resortObject.objectName}
                  </TableCell>
                  <TableCell 
                  align="center"
                  >
                      {innerRow.resortObject.objectType}
                  </TableCell>
                  <TableCell 
                  align="center"
                  >
                      {innerRow.numberOfPeople}
                  </TableCell>

                  <div style={{marginLeft : '6%'}}>
                    <Button
                    color="primary" 
                    type="submit"
                    variant="contained"
                    disabled={(row.reservationStatus !== "DRAFT")} //&& !TableContextImp?.roles_.includes(RolesTypes.ADMIN)
                    className={classes.editButton}
                    onClick={() => {
                      setEditDialog({
                        isSet : true,
                        id : innerRow.id,
                        propertyName : innerRow.resortObject.objectName,
                      });
                    }}
                    startIcon={<ModeEditIcon/>}
                    >
                        EDIT
                    </Button>

                    <Button 
                    color="secondary"
                    variant="contained"
                    type="submit"
                    className={classes.deleteButton}
                    disabled={(row.reservationStatus !== "DRAFT")} //&& !TableContextImp?.roles_.includes(RolesTypes.ADMIN)
                    onClick={() => { 
                      setDeleteDialog({
                        isSet : true,
                        id : innerRow.id,
                        propertyName : innerRow.resortObject.objectName,
                      });
                    }}
                    startIcon={<DeleteIcon/>}
                    >
                        DELETE
                    </Button>

                    <DialogConfirm
                        isOpen={deleteDialog.isSet}
                        closeHandler={() => setDeleteDialog(DELETE_DIALOG_CONFIRMATION_DEFAULT)}
                        onAcceptHandler={
                          () => {
                            TableContextImp?.removeAccommodation_(TableContextImp.jwtToken_, deleteDialog.id)
                            setDeleteDialog(DELETE_DIALOG_CONFIRMATION_DEFAULT) 
                          }  
                        }
                        dialogTitle="Are you sure to delete?"
                        dialogDescription= {`Accommodation named ${deleteDialog.propertyName} will be deleted`}
                        disagreeText="Back"
                        agreeText="Delete"
                    >
                    </DialogConfirm>

                    {editDialog.isSet && editDialog.id === innerRow.id ? (
                      <AccommodationDialogEdit
                          isOpen={editDialog.isSet}
                          propertyId={editDialog.id}
                          propertyName={editDialog.propertyName}
                          resortObjectId={innerRow.resortObject.id}
                          closeHandler={() => setEditDialog(EDIT_DIALOG_DEFAULT)}
                          onAcceptHandler={
                            () => {
                              setEditDialog(EDIT_DIALOG_DEFAULT) 
                            }
                          }
                      />
                    ): null}
                 </div>
              </TableRow>
            ))}
              <IconButton
                sx={{
                  marginTop : '2%',
                }}
                size="large"
                onClick={() => setRemarksTableOpen(!remarksTableOpen)}
              >
              {remarksTableOpen ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
              <Typography
              sx={{
                  marginTop : '3%',
                  marginLeft : '1%',
              }}
              variant='h5'
              >
                  Uwagi do zamówienia
              </Typography>
              </IconButton>
            <Collapse in={remarksTableOpen} timeout="auto" unmountOnExit>
              <ReservationRemarksTable
                reservationRemarks={row.reservationRemarksResponse}
              />
            </Collapse>
            </TableBody>

            </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

interface IMyProps {
  reservationList : Array<ReservationInterface>,
}

const ReservationTable : React.FC<IMyProps> = ({
  reservationList,
}) : JSX.Element =>  {

const classes = useStyles();

  React.useEffect(() => {
    //RERENDER WHEN RESERAVION LIST CHANGES
  }, [reservationList])


  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(15);

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  {console.log(reservationList)}

  return (
    <div className={classes.table}>
        <TableContainer component={Paper}>
        <Table 
        >
            <TableHead>
            <TableRow>
                <TableCell />
                <TableCell  align="left"> Reservation name</TableCell>
                <TableCell  align="center"> Starting</TableCell>
                <TableCell  align="center"> Ending</TableCell>
                <TableCell  align="center"> Final price</TableCell>
                <TableCell  align="center"> Status</TableCell>
                <TableCell  align="center"></TableCell>
            </TableRow>
            </TableHead>
            <TableBody>
            {reservationList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => (
                <>
                <Row 
                  key={row.id} 
                  row={row}
                />
                </>
            ))}
            </TableBody>
        </Table>
        <TablePagination
          rowsPerPageOptions={[1,5,15]}
          component="div"
          count={reservationList.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
      />
        </TableContainer>
    </div>
  );
}

export default ReservationTable;

const useStyles = makeStyles((theme: Theme) => createStyles({
    table: {
        width : '87vw',
    },

    editButton : {
      marginRight : '1vw',
      marginTop : '3%',
    },

    deleteButton : {
      marginTop : '3%',
    }

  }));