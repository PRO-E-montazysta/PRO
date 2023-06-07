import FullCalendar from '@fullcalendar/react' // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid' // a plugin!
import { EventInput, EventSourceInput } from '@fullcalendar/core'
import { useEffect, useMemo, useRef, useState } from 'react'
import { events, stages } from './data'
import CalendarEventDetails, { PopupEventInfo, cleanEventInfo } from './CalendarEventDetails'
import CalendarFilter from './CalendarFilter'
import FilterAltIcon from '@mui/icons-material/FilterAlt'
import { Box, Button, IconButton } from '@mui/material'
import moment, { Moment } from 'moment'

type CalendarProps = {
    eventsCollection: Array<EventInput>
    setCurrentDate: (value: Moment) => void
}

const Calendar = ({ eventsCollection, setCurrentDate }: CalendarProps) => {
    const [popupEventInfo, setPopupEventInfo] = useState<PopupEventInfo>(cleanEventInfo)
    const [filterOpen, setFilterOpen] = useState<boolean>(false)

    return (
        <>
            <Box>
                <IconButton onClick={() => setFilterOpen(!filterOpen)}>
                    <FilterAltIcon />
                </IconButton>
            </Box>
            {filterOpen && <CalendarFilter />}
            {/* <Button variant="contained" startIcon={}></Button> */}
            <FullCalendar
                plugins={[dayGridPlugin]}
                initialView="dayGridMonth"
                height={'80vh'}
                locale={'pl'}
                weekNumberCalculation={'ISO'}
                headerToolbar={{
                    left: 'title',
                    right: 'today prev,next',
                }}
                buttonText={{
                    today: 'dziś',
                }}
                showNonCurrentDates={false}
                fixedWeekCount={false}
                displayEventEnd={false}
                displayEventTime={false}
                events={[...events, ...eventsCollection]}
                eventClick={(e) => {
                    const _def = e.event._def
                    console.log(_def)
                    console.log(e)
                    const thisStage = stages.find((s) => s.eventId.toString() == _def.publicId)
                    if (thisStage)
                        //setTimeout to let fire ClickAwayListener first
                        setTimeout(() => {
                            setPopupEventInfo({
                                isOpen: true,
                                title: thisStage.name,
                                fromDate: thisStage.startDate,
                                toDate: thisStage.endDate,
                                status: thisStage.status,
                                subtitle: thisStage.orderName,
                                link: '/orders/' + thisStage.orderId,
                            })
                        }, 10)
                }}
                editable={true}
                datesSet={(e) => {
                    setCurrentDate(moment(e.start))
                }}
            />
            <CalendarEventDetails popupEventInfo={popupEventInfo} setPopupEventInfo={setPopupEventInfo} />
        </>
    )
}

export default Calendar
