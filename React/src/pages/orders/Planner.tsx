import FullCalendar from '@fullcalendar/react' // must go before plugins
import timeGridPlugin from '@fullcalendar/timegrid'
import { useEffect, useState } from 'react'
import { EventInput } from '@fullcalendar/core'
import PlannerStageDetails from './PlannerStageDetails'
import { useOrderStages } from './hooks'
import moment, { Moment } from 'moment'
import { Box } from '@mui/material'
import { PopupPlannerInfo, cleanPlannerInfo } from './FittersList'
import PlannerStagePopup from './PlannerStagePopup'

type PlannerProps = {
    orderId?: string
    initialDate: Moment
}

const Planner = ({ orderId, initialDate }: PlannerProps) => {
    const [popupEventInfo, setPopupEventInfo] = useState<PopupPlannerInfo>(cleanPlannerInfo)

    const orderStagesQuery = useOrderStages(orderId)
    const [orderStagesAsEvents, setOrderStagesAsEvents] = useState<EventInput[]>([])

    useEffect(() => {
        if (orderStagesQuery.data) {
            console.log(orderStagesQuery.data)
            const events: EventInput[] = orderStagesQuery.data.map((d) => {
                return {
                    title: `${d.name} (${d.fitters.length}/${d.plannedFittersNumber})`,
                    id: d.id?.toString(),
                    groupId: d.orderId.toString(),
                    start: moment(d.plannedStartDate).toDate(),
                    end: moment(d.plannedEndDate).toDate(),
                }
            })
            console.log(events)
            setOrderStagesAsEvents(events)
        }
    }, [orderStagesQuery.data])

    return (
        <>
            <FullCalendar
                initialDate={initialDate.toDate()}
                plugins={[timeGridPlugin]}
                initialView="timeGridWeek"
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
                displayEventEnd={true}
                events={orderStagesAsEvents}
                eventClick={(e) => {
                    const thisId = e.event._def.publicId

                    //setTimeout to let fire ClickAwayListener first
                    setTimeout(() => {
                        setPopupEventInfo({
                            isOpen: true,
                            stageId: thisId,
                        })
                    }, 10)
                }}
            />
            {/* <PlannerStageDetails popupEventInfo={popupEventInfo} setPopupEventInfo={setPopupEventInfo} /> */}
            <PlannerStagePopup popupEventInfo={popupEventInfo} setPopupEventInfo={setPopupEventInfo} />
        </>
    )
}

export default Planner
