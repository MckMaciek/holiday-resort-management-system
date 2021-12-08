import * as React from 'react';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import Button from '@material-ui/core/Button';

import DeleteIcon from '@mui/icons-material/Delete';
import ModeEditIcon from '@mui/icons-material/ModeEdit';

import DialogConfirm from '../Components/DialogConfirm';

import {TableContext} from '../Sections/ReservationSection';

import { ReservationInterface } from '../Interfaces/Reservation';
import { createStyles, makeStyles, Theme } from '@material-ui/core';

const Row = ({row} : any) => {

  const [open, setOpen] = React.useState(false);

  const DIALOG_CONFIRMATION_DEFAULT = {
    isSet : false,
    id : -1,
    propertyName : "",
  }
  const [dialogConfirmation, setDialogConfirmation] = React.useState(DIALOG_CONFIRMATION_DEFAULT);
  



  const classes = useStyles();
  const TableContextImp = React.useContext(TableContext);

  return (
    <React.Fragment>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            size="large"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          <p> {row.id} </p>
        </TableCell>
        <TableCell align="center">{row.reservationDate}</TableCell>
        <TableCell align="center">{row.finalPrice}</TableCell>
        <TableCell align="center">{row.reservationStatus}</TableCell>
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
                <TableCell  align="center"> Choosen Amount of People</TableCell>
                <TableCell  align="center"> Max Amount of People</TableCell>
                <TableCell  align="center"> Price Per Person</TableCell>
                <TableCell  align="center"> Price Per Unused Space</TableCell>
                <TableCell  align="center"> Operations </TableCell>
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
                  <TableCell 
                  align="center"
                  >
                      {innerRow.resortObject.maxAmountOfPeople}
                  </TableCell>
                  <TableCell 
                  align="center"
                  > 
                    {innerRow.resortObject.pricePerPerson}
                  </TableCell>
                  <TableCell 
                  align="center"
                  > 
                    {innerRow.resortObject.unusedSpacePrice}
                  </TableCell>
                  <TableCell 
                  align="center"
                  > 
                  <>
                    <Button
                    color="primary" 
                    type="submit"
                    variant="contained"
                    disabled={row.reservationStatus !== "STARTED"}
                    className={classes.operationButton}
                    startIcon={<ModeEditIcon/>}
                    >
                        EDIT
                    </Button>

                    <Button 
                    color="secondary"
                    variant="contained"
                    type="submit"
                    disabled={row.reservationStatus !== "STARTED"}
                    onClick={() => { 
                      setDialogConfirmation({
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
                          isOpen={dialogConfirmation.isSet}
                          closeHandler={() => setDialogConfirmation(DIALOG_CONFIRMATION_DEFAULT)}
                          onAcceptHandler={
                            () => {
                              TableContextImp?.removeAccommodation_(TableContextImp.jwtToken_, dialogConfirmation.id)
                              TableContextImp?.setComponentRerender_(true)
                              setDialogConfirmation(DIALOG_CONFIRMATION_DEFAULT) 
                            }  
                          }
                          dialogTitle="Are you sure to delete?"
                          dialogDescription= {`Accommodation with ${dialogConfirmation.propertyName} will be deleted`}
                          disagreeText="Back"
                          agreeText="Delete"
                    >
                    </DialogConfirm>

                  </>
                  </TableCell>
              </TableRow>
            ))}
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
  return (
    <div className={classes.table}>
        <TableContainer component={Paper}>
        <Table 
        >
            <TableHead>
            <TableRow>
                <TableCell />
                <TableCell  align="left"> Reservation Id</TableCell>
                <TableCell  align="center"> Created</TableCell>
                <TableCell  align="center"> Final price</TableCell>
                <TableCell  align="center"> Status</TableCell>
            </TableRow>
            </TableHead>
            <TableBody>
            {reservationList.map((row) => (
                <>
                <Row 
                  key={row.id} 
                  row={row}
                />
                </>
            ))}
            </TableBody>
        </Table>
        </TableContainer>
    </div>
  );
}

export default ReservationTable;

const useStyles = makeStyles((theme: Theme) => createStyles({
    table: {
        width : '87vw',
    },

    operationButton : {
      marginRight : '1vw',
    }

  }));