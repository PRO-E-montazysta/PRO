import { Button } from '@mui/material'
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
                <Box sx={{ textAlign: 'center' }}>
                    <Typography>
                        {process.env.NODE_ENV === 'development'
                            ? queriesStatus.errorMessage
                            : 'Wewnętrzny błąd serwera. Skontaktuj się z administratorem'}
                    </Typography>
                    <Button
                        onClick={queriesStatus.handleResetStatus}
                        variant="contained"
                        id={`queryStatusBoxBtn-ServerError-OK`}
                    >
                        OK
                    </Button>
                </Box>
            ) : (
                <Box sx={{ textAlign: 'center' }}>
                    <Typography>Brak danych do wyświetlenia</Typography>
                    <Button
                        onClick={queriesStatus.handleResetStatus}
                        variant="contained"
                        id={`queryStatusBoxBtn-NoData-OK`}
                    >
                        OK
                    </Button>
                </Box>
            )}
        </Box>
    )
}

export default QueryBoxStatus
