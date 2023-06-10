import { Box, Paper } from '@mui/material'

import Calendar from './Calendar'
import FormTitle from '../../components/form/FormTitle'
import FormBox from '../../components/form/FormBox'


const Home = () => {

    return (
        <FormBox>
            <Box
                sx={{
                    maxWidth: '1200px',
                    m: '20px auto',
                }}
            >
                <FormTitle mainTitle="Kalendarz" />
                <Paper sx={{ p: '20px' }}>
                    <Calendar  />
                </Paper>
            </Box>
        </FormBox>
    )
}

export default Home