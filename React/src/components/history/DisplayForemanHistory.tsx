import { Card, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from '@mui/material'
import ExpandMore from '../expandMore/ExpandMore'
import HistoryIcon from '@mui/icons-material/History'
import { formatDate } from '../../helpers/format.helper'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getForemanWorkHistory } from '../../api/foreman.api'
import { useNavigate } from 'react-router-dom'

type ForemanlHistoryParams = {
    foremanId: string
}

const DisplayForemanHistory = (params: ForemanlHistoryParams) => {
    let navigate = useNavigate()
    const queryWorkHistory = useQuery<Array<any>, AxiosError>(
        ['foreman-history', { id: params.foremanId }],
        async () => getForemanWorkHistory(params.foremanId),
        {
            enabled: !!params.foremanId && params.foremanId !== 'new' && params.foremanId != undefined,
        },
    )

    const addForemanHistoryTableCard = () => {
        return (
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Nazwa zlecenia</TableCell>
                        <TableCell>Data rozpoczęcia</TableCell>
                        <TableCell>Data zakończenia</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {queryWorkHistory.data &&
                        queryWorkHistory.data.map((row) => (
                            <TableRow
                                key={row.orderId}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                onClick={() => {
                                    navigate(`/orders/${row.orderId}/`)
                                }}
                            >
                                <TableCell component="th" scope="row">
                                    {row.orderName}
                                </TableCell>
                                <TableCell>
                                    {row.startDate != null ? formatDate(row.startDate.toString()) : 'Planowane'}
                                </TableCell>
                                <TableCell>
                                    {row.startDate != null
                                        ? row.endDate != null
                                            ? formatDate(row.endDate.toString())
                                            : 'W trakcie'
                                        : 'Planowane'}
                                </TableCell>
                            </TableRow>
                        ))}
                </TableBody>
            </Table>
        )
    }

    if (queryWorkHistory.data && queryWorkHistory.data.length > 0) {
        return (
            <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                <Card sx={{ width: '100%', left: '50%' }}>
                    <ExpandMore
                        titleIcon={<HistoryIcon />}
                        title="Historia zarządzanych zleceń"
                        cardContent={addForemanHistoryTableCard()}
                    />
                </Card>
            </Grid>
        )
    } else {
        return (
            <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                <Typography>Brygadzista nie zarządzał zleceniami</Typography>
            </Grid>
        )
    }
}

export default DisplayForemanHistory
