import { Box, Paper } from '@mui/material'
import { EventInput } from '@fullcalendar/core'
import { useMemo } from 'react'
import { Moment } from 'moment'
import { stages } from './data'
import Calendar from './Calendar'
import CalendarFilter from './CalendarFilter'
import FormTitle from '../../components/form/FormTitle'
import FormBox from '../../components/form/FormBox'
import Planner from './Planner'

export type OrderStage = {
    eventId: number
    id: number
    orderId: number
    name: string
    orderName: string
    status: string
    startDate: Moment
    endDate: Moment
    plannedFittersNumber: number
    fitters: Array<any>
}

const Home = () => {
    const eventStages: EventInput[] = useMemo(
        () =>
            stages.map((s): EventInput => {
                return {
                    id: s.eventId.toString(),
                    title: s.name,
                    start: s.startDate.toDate(),
                    end: s.endDate.toDate(),
                    color: 'purple',
                    display: 'block',
                }
            }),
        [],
    )
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
                    <Calendar eventsCollection={eventStages} />
                </Paper>
                <Box sx={{ mt: '100px' }}>
                    <FormTitle mainTitle="Planer" />
                    <Paper sx={{ p: '20px' }}>
                        <Planner eventsCollection={eventStages} />
                    </Paper>
                </Box>
            </Box>
        </FormBox>
    )
}

export default Home
