import FullCalendar from '@fullcalendar/react' // must go before plugins
import timeGridPlugin from '@fullcalendar/timegrid'
import { events, stages } from './data'
import CalendarEventDetails, { PopupEventInfo, cleanEventInfo } from './CalendarEventDetails'
import { useState } from 'react'
import { EventInput } from '@fullcalendar/core'
import PlannerStageDetails, { PopupPlannerInfo, cleanPlannerInfo } from './PlannerStageDetails'

type PlannerProps = {
    eventsCollection: Array<EventInput>
}

const Planner = ({ eventsCollection }: PlannerProps) => {
    const [popupEventInfo, setPopupEventInfo] = useState<PopupPlannerInfo>(cleanPlannerInfo)
    return (
        <>
            <FullCalendar
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
                events={eventsCollection}
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
                                fitterAssignedIds: thisStage.fitters,
                                fitterAvaliableIds: [1, 2, 3],
                            })
                        }, 10)
                }}
            />
            <PlannerStageDetails popupEventInfo={popupEventInfo} setPopupEventInfo={setPopupEventInfo} />
        </>
    )
}

export default Planner
