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
    TextField,
    Button,
} from '@mui/material';
import { Link } from "react-router-dom";
import { styled } from '@mui/material/styles';
import { AxiosError } from 'axios';
import { useQuery } from 'react-query';
import { getAllCompanies } from '../../api/company.api';

const CustomTextField = styled(TextField)(({ theme }) => ({
    label: { shrink: true, color: theme.palette.secondary.contrastText },
    input: { color: theme.palette.secondary.contrastText },
    '& label.Mui-focused': {
        color: theme.palette.secondary.contrastText,
    },
    '& .MuiOutlinedInput-root': {
        '& fieldset': {
            borderColor: '#96C0FB',
        },
        '&:hover fieldset': {
            borderColor: theme.palette.secondary.contrastText,
        },
    },
    '&.Mui-focused fieldset': {
        borderColor: 'green',
    },
    border: '2px solid #96C0FB',
    borderRadius: '8px',
    marginBottom: '25px'
}));

const StyledTableCell = styled(TableCell)({
    borderBottom: 'none',
    color: 'white',
});

const StyledTableCellHeader = styled(TableCell)({
    color: 'white',
    textAlign: 'center',
});

const ToolElementButton = styled(Button)({
    color: 'white',
    background: '#282A3A',
    width: '100px',
    height: '40px',
    'border-radius': '3px',
    'font-weight': '600',
    'font-size': '13px',
    'line-height': '32px',
    'letter-spacing': '0.2px',
    marginRight: '5px'
});

const CloudAdmin = () => {
    let isLoading = false;
    let isError = false;
    let error = {
        message: "PRZYKŁADOWY BŁĄD"
    };
    let data = [{
        id: 123,
        name: "Nazwa", 
        date: "07-09-2012",
        status: "Aktywna"
    },
    {
        id: 123,
        name: "Nazwa", 
        date: "07-09-2012",
        status: "Aktywna"
    }];
    //const { isLoading, isError, error, data } = useQuery<Array<any>, AxiosError>('all-companies', getAllCompanies)

    let content;

    if (isLoading) content = <p>Ładowanie danych</p>
    else if (isError) content = <p>{error.message}</p>
    else content = (
        <Box sx={{ flexGrow: 1, padding: 3 }}>
            <Grid container spacing={2}>
                <Grid item xs={2}
                    height="100%"
                    bgcolor="#1A1C26"
                    padding="16px 16px 0"
                    marginTop="16px"
                    textAlign="center">
                    <Typography
                        variant="h5"
                        fontWeight="bold"
                        color="white"
                        marginBottom="16px">
                        Filtrowanie
                    </Typography>
                    <CustomTextField
                        name="id"
                        type="text"
                        label="Id"
                        fullWidth
                    />
                    <CustomTextField
                        name="name"
                        type="text"
                        label="Nazwa firmy"
                        fullWidth
                    />
                    <CustomTextField
                        name="status"
                        type="text"
                        label="Status"
                        fullWidth
                    />
                    <Button
                        sx={{
                            color: 'white',
                            background: '#282A3A',
                            width: '100px',
                            height: '40px',
                            'border-radius': '3px',
                            'font-weight': '600',
                            'font-size': '13px',
                            'line-height': '32px',
                            'letter-spacing': '0.2px',
                            marginBottom: '16px'
                        }}
                    >
                        Filtruj
                    </Button>
                </Grid>
                <Grid item xs={10}>
                    <TableContainer component={Paper} sx={{ backgroundColor: '#1A1C26' }}>
                        <Table aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <StyledTableCell colSpan={5}>
                                        <Typography variant="h5" fontWeight={"bold"}>Lista firm</Typography>
                                    </StyledTableCell>
                                </TableRow>
                                <TableRow>
                                    <StyledTableCellHeader>Id</StyledTableCellHeader>
                                    <StyledTableCellHeader align="left">Nazwa firmy</StyledTableCellHeader>
                                    <StyledTableCellHeader align="left">Data utworzenia</StyledTableCellHeader>
                                    <StyledTableCellHeader align="left">Status</StyledTableCellHeader>
                                    <StyledTableCellHeader align="left">Akcje</StyledTableCellHeader>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {
                                    data && data.map(company => (
                                        <TableRow>
                                            <StyledTableCell>{company.id}</StyledTableCell>
                                            <StyledTableCell align="left">{company.name}</StyledTableCell>
                                            <StyledTableCell align="left">{company.date}</StyledTableCell>
                                            <StyledTableCell align="left">{company.status}</StyledTableCell>
                                            <StyledTableCell align="left" style={{ textAlign: "center" }}>
                                                <Link to='/company-list/edit/{company.id}'>
                                                    <ToolElementButton>Edytuj</ToolElementButton>
                                                </Link>
                                                <Link to='/company-list/block/{company.id}'>
                                                    <ToolElementButton>Blokuj</ToolElementButton>
                                                </Link>
                                            </StyledTableCell>
                                        </TableRow>
                                    ))
                                }
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Grid>
            </Grid>
        </Box>
    );

    return content;
}

export default CloudAdmin;