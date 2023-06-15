import { Card, Grid, Table, TableBody, TableCell, TableHead, TableRow, Typography } from '@mui/material'
import ExpandMore from '../expandMore/ExpandMore'
import HistoryIcon from '@mui/icons-material/History'
import { formatDate } from '../../helpers/format.helper'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useNavigate } from 'react-router-dom'
import { getFitterWorkHistory } from '../../api/fitter.api'

type FitterlHistoryParams = {
    fitterId: string
}

const DisplayFitterHistory = (params: FitterlHistoryParams) => {
    let navigate = useNavigate()
    const queryWorkHistory = useQuery<Array<any>, AxiosError>(
        ['fitter-history', { id: params.fitterId }],
        async () => getFitterWorkHistory(params.fitterId),
        {
            enabled: !!params.fitterId && params.fitterId !== 'new' && params.fitterId != undefined,
        },
    )

    const addFitterHistoryTableCard = () => {
        return (
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Nazwa zlecenia</TableCell>
                        <TableCell>Nazwa etapu</TableCell>
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
                                <TableCell>{row.orderStageName}</TableCell>
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
                        title="Historia wykonanych etapów"
                        cardContent={addFitterHistoryTableCard()}
                    />
                </Card>
            </Grid>
        )
    } else {
        return (
            <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                <Typography>Montażysta nie brał udziału w etapach</Typography>
            </Grid>
        )
    }
}

export default DisplayFitterHistory
