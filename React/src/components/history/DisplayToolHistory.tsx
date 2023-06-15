import { Card, Grid, Link, Table, TableBody, TableCell, TableHead, TableRow, Typography } from '@mui/material'
import { ToolHistory } from '../../types/model/Tool'
import ExpandMore from '../expandMore/ExpandMore'
import HistoryIcon from '@mui/icons-material/History'
import { formatDate } from '../../helpers/format.helper'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getToolHistory } from '../../api/tool.api'

type ToolHistoryParams = {
    toolId: string | undefined
}

const DisplayToolHistory = (params: ToolHistoryParams) => {
    const queryToolHistory = useQuery<Array<ToolHistory>, AxiosError>(
        ['tool-history'],
        async () => getToolHistory(params.toolId && params.toolId !== 'new' ? params.toolId : ''),
        {
            enabled: !!params.toolId && params.toolId !== 'new',
        },
    )

    const addToolHistoryTableCard = () => {
        return (
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Nazwa etapu</TableCell>
                        <TableCell>Brygadzista</TableCell>
                        <TableCell>Początek etapu</TableCell>
                        <TableCell>Koniec etapu</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {queryToolHistory.data &&
                        queryToolHistory.data.map((row: { [x: string]: string | number }) => (
                            <TableRow
                                key={row['orderStageName']}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    <Link href={'/orders/' + row['orderId']}>{row['orderStageName']}</Link>
                                </TableCell>
                                <TableCell>
                                    <Link href={'/employees/' + row['foremanId']}>{row['foremanName']}</Link>
                                </TableCell>
                                <TableCell>{formatDate(String(row['orderStageStartDate']))}</TableCell>
                                <TableCell>{formatDate(String(row['orderStageStartDate']))}</TableCell>
                            </TableRow>
                        ))}
                </TableBody>
            </Table>
        )
    }

    if (queryToolHistory.data && queryToolHistory.data.length > 0) {
        return (
            <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                <Card sx={{ width: '100%', left: '50%' }}>
                    <ExpandMore
                        titleIcon={<HistoryIcon />}
                        title="Historia narzędzia"
                        cardContent={addToolHistoryTableCard()}
                    />
                </Card>
            </Grid>
        )
    } else {
        return (
            <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                <Typography>Narzędzie nie było jeszcze używane podczas etapów</Typography>
            </Grid>
        )
    }
}

export default DisplayToolHistory
