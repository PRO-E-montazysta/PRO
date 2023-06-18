import { Box, Paper } from '@mui/material'

import Calendar from './Calendar'
import FormTitle from '../../components/form/FormTitle'
import FormBox from '../../components/form/FormBox'
import { isAuthorized } from '../../utils/authorize'
import { Role } from '../../types/roleEnum'
import { Navigate } from 'react-router-dom'

const Home = () => {
    return (
        <FormBox>
            {isAuthorized([Role.CLOUD_ADMIN]) ? (
                <Navigate to={'/companies'} />
            ) : (
                <Box
                    sx={{
                        maxWidth: '1200px',
                        m: '20px auto',
                    }}
                >
                    <FormTitle mainTitle="Kalendarz" />
                    <Paper sx={{ p: '20px' }}>
                        <Calendar />
                    </Paper>
                </Box>
            )}
        </FormBox>
    )
}

export default Home
