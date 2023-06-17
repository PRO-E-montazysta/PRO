import FullCalendar from '@fullcalendar/react' // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid' // a plugin!
import { EventClickArg, EventInput } from '@fullcalendar/core'
import { useEffect, useLayoutEffect, useState } from 'react'
import CalendarEventDetails, { PopupEventInfo, cleanEventInfo } from './CalendarEventDetails'
import CalendarFilter, { CalendarFilters, calendarFilterDefaultValues } from './CalendarFilter'
import FilterAltIcon from '@mui/icons-material/FilterAlt'
import { Box, IconButton } from '@mui/material'
import moment from 'moment'
import { getOrderStageById } from '../../api/orderStage.api'
import { orderStatusName, typeOfUnavailabilityName } from '../../helpers/enum.helper'
import { getUnavailabilityDetails } from '../../api/unavailability.api'
import { useUnavailabilityListByMonth } from './hooks'
import { getOrderDetails } from '../../api/order.api'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getAboutMeInfo } from '../../api/employee.api'
import { useFormik } from 'formik'
import { Unavailability } from '../../types/model/Unavailability'

const Calendar = () => {
    const [popupEventInfo, setPopupEventInfo] = useState<PopupEventInfo>(cleanEventInfo)
    const [currentDate, setCurrentDate] = useState(moment())
    const unavailabilityListByMonth = useUnavailabilityListByMonth(currentDate.year(), currentDate.month() + 1)
    const [events, setEvents] = useState<EventInput[]>([])

    const aboutMeQuery = useQuery<any, AxiosError>(['about-me'], async () => getAboutMeInfo())

    useLayoutEffect(() => {
        if (unavailabilityListByMonth.data && aboutMeQuery.data) {
            console.log(unavailabilityListByMonth.data)
            const myEvents: Unavailability[] = unavailabilityListByMonth.data.filter(
                (d) => d.assignedToId == aboutMeQuery.data.userId,
            )
            const newEvents: EventInput[] = myEvents.map((d) => {
                return {
                    id: d.id.toString(),
                    groupId: d.typeOfUnavailability,
                    title:
                        d.typeOfUnavailability == 'BUSY' && d.orderStageName
                            ? d.orderStageName
                            : typeOfUnavailabilityName(d.typeOfUnavailability)
                            ? typeOfUnavailabilityName(d.typeOfUnavailability)
                            : 'INNE',
                    start: d.unavailableFrom,
                    end: d.unavailableTo,
                    display: 'block',
                    color: d.typeOfUnavailability == 'BUSY' ? 'blue' : 'red',
                }
            })

            setEvents(newEvents)
        }
    }, [unavailabilityListByMonth.data, aboutMeQuery.data])

    const handleEventClick = async (e: EventClickArg) => {
        const _def = e.event._def
        const eventType = e.event.groupId

        if (eventType == 'BUSY') {
            displayEventOrderStageDetails(_def.publicId)
        } else {
            displayEventUnavailabilityDetails(_def.publicId)
        }
    }

    const displayEventUnavailabilityDetails = async (id: string) => {
        const unavailabilityData = await getUnavailabilityDetails(id)
        if (unavailabilityData)
            setTimeout(() => {
                setPopupEventInfo({
                    isOpen: true,
                    title: typeOfUnavailabilityName(unavailabilityData.typeOfUnavailability),
                    link: '/unavailabilities/' + unavailabilityData.id,
                    infoArray: [
                        {
                            label: 'Data od',
                            value: moment(unavailabilityData.unavailableFrom).format('HH:mm DD-MM-YYYY'),
                        },
                        {
                            label: 'Data do',
                            value: moment(unavailabilityData.unavailableTo).format('HH:mm DD-MM-YYYY'),
                        },
                        {
                            label: 'Opis',
                            value: unavailabilityData.description,
                        },
                    ],
                })
            }, 10)
    }

    const displayEventOrderStageDetails = async (id: string) => {
        const stageData = await getOrderStageById(id)
        const orderData = await getOrderDetails(stageData.orderId.toString())

        if (stageData)
            setTimeout(() => {
                setPopupEventInfo({
                    isOpen: true,
                    title: stageData.name,
                    subtitle: orderData.name,
                    link: '/orders/' + stageData.orderId,
                    infoArray: [
                        {
                            label: 'Data od',
                            value: moment(stageData.startDate).format('HH:mm DD-MM-YYYY'),
                        },
                        {
                            label: 'Data do',
                            value: moment(stageData.endDate).format('HH:mm DD-MM-YYYY'),
                        },
                        {
                            label: 'Status',
                            value: orderStatusName(stageData.status),
                        },
                    ],
                })
            }, 10)
    }

    return (
        <>
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
                events={events}
                eventClick={handleEventClick}
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
