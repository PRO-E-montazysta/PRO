import {
  Box,
  Grid,
  Paper,
  TableContainer,
  Table,
  TableCell,
  TableHead,
  TableRow,
  TableBody,
  Typography,
  Checkbox,
} from '@mui/material';
import React from 'react';
import { styled } from '@mui/material/styles';
import { getAllUsers } from '../../api/user.api';
import { useQuery } from 'react-query'
import { AxiosError } from 'axios';


function createData(number: number, name: string, phone: string, email: string, status: string) {
  return { number, name, phone, email, status };
}
const rows = [
  createData(1, 'Jakub Kowalski', '123456789', 'kowalskie@example.com', 'wolny'),
  createData(2, 'Maciej Doe', '222222222', 'kowalskie@example.com', 'wolny'),
  createData(3, 'Katarzyna Pietrzak', '333333333', 'kowalskie@example.com', 'wolny'),
  createData(4, 'Andrzej Nowakowski', '444444444', 'kowalskie@example.com', 'wolny'),
  createData(5, 'Piotr Nowak', '555555555', 'kowalskie@example.com', 'wolny'),
  createData(5, 'Piotr Nowak', '555555555', 'kowalskie@example.com', 'wolny'),
  createData(5, 'Piotr Nowak', '555555555', 'kowalskie@example.com', 'wolny'),
];
const StyledTableCell = styled(TableCell)({
  borderBottom: 'none',
  color: 'white',
});

const StyledTableCellHeader = styled(TableCell)({
  color: 'white',
});

const Employees = () => {
  const { isLoading, isError, error: usersError, data: usersData } = useQuery<Array<any>, AxiosError>('all-users', getAllUsers)

  let content;
  if (isLoading) content = <p>Loading...</p>
  else if (isError) content = <p>{usersError.message}</p>
  else content = (
    <Box sx={{ flexGrow: 1, padding: 3 }}>
      <Grid container spacing={2}>
        {JSON.stringify(usersData)}
        <Grid item xs={2}>
          <Paper sx={{ height: '100%' }}>Osobny komponent na filtry</Paper>
        </Grid>
        <Grid item xs={10}>
          <TableContainer component={Paper} sx={{ backgroundColor: '#1A1C26' }}>
            <Table aria-label="simple table">
              <TableHead>
                <TableRow>
                  <StyledTableCell colSpan={2}>
                    <Typography>Lista pracowników</Typography>
                  </StyledTableCell>
                </TableRow>
                <TableRow>
                  <StyledTableCellHeader padding="checkbox">
                    <Checkbox color="primary" />
                  </StyledTableCellHeader>
                  <StyledTableCellHeader>Pracownik</StyledTableCellHeader>
                  <StyledTableCellHeader align="left">Telefon</StyledTableCellHeader>
                  <StyledTableCellHeader align="left">email</StyledTableCellHeader>
                  <StyledTableCellHeader align="left">status</StyledTableCellHeader>
                </TableRow>
              </TableHead>
              <TableBody>
                {rows.map((row) => (
                  <TableRow key={row.number}>
                    <StyledTableCell padding="checkbox">
                      <Checkbox color="primary" />
                    </StyledTableCell>
                    <StyledTableCell align="left">{row.name}</StyledTableCell>
                    <StyledTableCell align="left">{row.phone}</StyledTableCell>
                    <StyledTableCell align="left">{row.email}</StyledTableCell>
                    <StyledTableCell align="left">{row.status}</StyledTableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
      </Grid>
    </Box>
  );

  return content;
};

export default Employees;
