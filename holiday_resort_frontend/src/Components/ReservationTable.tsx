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
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import Button from '@material-ui/core/Button';


import { createStyles, makeStyles, Theme } from '@material-ui/core';

function createData(
  name: string,
  calories: number,
  fat: number,
  carbs: number,
  protein: number,
  price: number,
  status : string,
) {
  return {
    name,
    calories,
    fat,
    carbs,
    protein,
    price,
    status,
  };
}

function Row(props: { row: ReturnType<typeof createData> }) {
  const { row } = props;
  const [open, setOpen] = React.useState(false);

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
          <p> {row.name} </p>
        </TableCell>
        <TableCell align="center">{row.calories}</TableCell>
        <TableCell align="center">{row.fat}</TableCell>
        <TableCell align="center">{row.carbs}</TableCell>
        <TableCell align="center">{row.protein}</TableCell>
        <TableCell align="center">{row.status}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={4}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
            <Typography 
            variant="h6" 
            >
                Operacje dla {row.name}
            </Typography>
            <Button 
                color="secondary" 
                variant="contained" 
                >
                Show
            </Button>
            <Button 
                color="secondary" 
                variant="contained" 
                >
                Edit
            </Button>
            <Button 
                color="secondary" 
                variant="contained" 
                >
                Delete
            </Button>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

const rows = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0, 3.99, "DOne"),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3, 4.99, "DOne"),
  createData('Eclair', 262, 16.0, 24, 6.0, 3.79, "DOne"),
  createData('Cupcake', 305, 3.7, 67, 4.3, 2.5, "DOne"),
  createData('Gingerbread', 356, 16.0, 49, 3.9, 1.5, "DOne"),
  createData('Cupcake', 305, 3.7, 67, 4.3, 2.5, "DOne"),

];

export default function ReservationTable() {

const classes = useStyles();

  return (
    <div className={classes.table}>
        <TableContainer component={Paper}>
        <Table 
        >
            <TableHead>
            <TableRow>
                <TableCell />
                <TableCell  
                align="center"> Lp.</TableCell>
                <TableCell  align="center"> Creation Date</TableCell>
                <TableCell  align="center"> Starting Date</TableCell>
                <TableCell  align="center"> Ending Date</TableCell>
                <TableCell  align="center"> Amount of people</TableCell>
                <TableCell  align="center"> Status </TableCell>
            </TableRow>
            </TableHead>
            <TableBody>
            {rows.map((row) => (
                <Row key={row.name} row={row} />
            ))}
            </TableBody>
        </Table>
        </TableContainer>
    </div>
  );
}

const useStyles = makeStyles((theme: Theme) => createStyles({
    table: {
        width : '87vw',
    },
  }));