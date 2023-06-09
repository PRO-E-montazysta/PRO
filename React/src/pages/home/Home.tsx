import { Box, Paper } from '@mui/material'

import Calendar from './Calendar'
import FormTitle from '../../components/form/FormTitle'
import FormBox from '../../components/form/FormBox'
import Planner from './Planner'

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
                <Box sx={{ mt: '100px' }}>
                    <FormTitle mainTitle="Planer" />
                    <Paper sx={{ p: '20px' }}>
                        {/* <Planner eventsCollection={events} /> */}
                    </Paper>
                </Box>
            </Box>
        </FormBox>
    )
}

export default Home
