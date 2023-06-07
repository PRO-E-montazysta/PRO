import { Box, Paper } from '@mui/material'
import { EventInput } from '@fullcalendar/core'
import { useEffect, useMemo, useState } from 'react'
import { Moment } from 'moment'
import { stages } from './data'
import Calendar from './Calendar'
import CalendarFilter from './CalendarFilter'
import FormTitle from '../../components/form/FormTitle'
import FormBox from '../../components/form/FormBox'
import Planner from './Planner'
import moment from 'moment'
import { useUnavailabilityByMonth } from './hooks'

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
    const [currentDate, setCurrentDate] = useState(moment())
    const unavailabilityByMonth = useUnavailabilityByMonth(currentDate.year(), currentDate.month())

    useEffect(() => {
        console.log(unavailabilityByMonth)
    }, [unavailabilityByMonth])

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
                    <Calendar eventsCollection={eventStages} setCurrentDate={setCurrentDate} />
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
