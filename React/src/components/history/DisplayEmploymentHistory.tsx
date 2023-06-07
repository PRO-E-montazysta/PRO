import { Card, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from '@mui/material'
import ExpandMore from '../expandMore/ExpandMore'
import HistoryIcon from '@mui/icons-material/History'
import { formatDate, formatShortDate } from '../../helpers/format.helper'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getAllEmployeeEmployments } from '../../api/employment.api'
import { Employment } from '../../types/model/Employment'

type EmploymentHistoryParams = {
    employeeId: string
}

const DisplayEmploymentHistory = (params: EmploymentHistoryParams) => {
    const queryEmploymentHistory = useQuery<Array<Employment>, AxiosError>(
        ['employee-employments', { id: params.employeeId }],
        async () => getAllEmployeeEmployments(params.employeeId),
        {
            enabled: !!params.employeeId && params.employeeId !== 'new',
        },
    )

    const addEmploymentHistoryTableCard = () => {
        return (
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Data początku zatrudnienia</TableCell>
                        <TableCell>Data końca zatrudnienia</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {queryEmploymentHistory.data &&
                        queryEmploymentHistory.data.map((row) => (
                            <TableRow
                                //key={row['orderStageName']}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    {formatShortDate(row.dateOfEmployment.toString())}
                                </TableCell>
                                <TableCell>
                                    {row['dateOfDismiss'] != null
                                        ? formatShortDate(row.dateOfDismiss.toString())
                                        : 'Trwa'}
                                </TableCell>
                            </TableRow>
                        ))}
                </TableBody>
            </Table>
        )
    }

    if (queryEmploymentHistory.data && queryEmploymentHistory.data.length > 0) {
        return (
            <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                <Card sx={{ width: '100%', left: '50%' }}>
                    <ExpandMore
                        titleIcon={<HistoryIcon />}
                        title="Historia zatrudnienia"
                        cardContent={addEmploymentHistoryTableCard()}
                    />
                </Card>
            </Grid>
        )
    } else {
        return (
            <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                <Typography>Ta osoba nie miała przypisanego zatrudnienia</Typography>
            </Grid>
        )
    }
}

export default DisplayEmploymentHistory
