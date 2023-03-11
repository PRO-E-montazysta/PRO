import { Paper } from '@mui/material'
import { Box } from '@mui/system'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { useParams } from 'react-router-dom'
import { getElementDetails } from '../../api/element.api'
import { Element } from '../../types/model/Element'

const ElementDetails = () => {
    const params = useParams()

    const { isLoading, isError, error, data } = useQuery<Element, AxiosError>(
        ['element', { id: params.id }],
        async () => getElementDetails(params.id),
        {
            enabled: !!params.id,
        },
    )

    return (
        <Box>
            <Paper>{isLoading ? <p>Ładowanie...</p> : isError ? <p>{error.message}</p> : JSON.stringify(data)}</Paper>
        </Box>
    )
}

export default ElementDetails
