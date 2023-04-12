import Box from '@mui/material/Box'
import CircularProgress from '@mui/material/CircularProgress/CircularProgress'
import Typography from '@mui/material/Typography'
import { QueriesStatusResult } from '../../hooks/useQueriesStatus'

type QueryBoxStatusProps = {
    queriesStatus: QueriesStatusResult
}

const QueryBoxStatus = (props: QueryBoxStatusProps) => {
    const { queriesStatus } = props

    return (
        <Box
            sx={{
                display: 'flex',
                minHeight: '200px',
                justifyContent: 'center',
                alignItems: 'center',
            }}
        >
            {queriesStatus.result == 'isLoading' ? (
                <CircularProgress />
            ) : queriesStatus.result == 'isError' ? (
                <Typography>{queriesStatus.errorMessage}</Typography>
            ) : (
                <Typography>Brak danych do wyświetlenia</Typography>
            )}
        </Box>
    )
}

export default QueryBoxStatus
