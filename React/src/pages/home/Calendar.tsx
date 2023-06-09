import FullCalendar from '@fullcalendar/react' // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid' // a plugin!
import { EventClickArg, EventInput } from '@fullcalendar/core'
import { useEffect, useState } from 'react'
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

const Calendar = () => {
    const [popupEventInfo, setPopupEventInfo] = useState<PopupEventInfo>(cleanEventInfo)
    const [filterOpen, setFilterOpen] = useState<boolean>(false)

    const [currentDate, setCurrentDate] = useState(moment())
    const unavailabilityListByMonth = useUnavailabilityListByMonth(currentDate.year(), currentDate.month() + 1)
    const [events, setEvents] = useState<EventInput[]>([])

    const aboutMeQuery = useQuery<any, AxiosError>(['about-me'], async () => getAboutMeInfo())

    const formikFilter = useFormik<CalendarFilters>({
        initialValues: calendarFilterDefaultValues,
        onSubmit: (data: any) => {
            console.log(data)
        },
    })

    useEffect(() => {
        if (aboutMeQuery.data && aboutMeQuery.data.userId)
            formikFilter.setFieldValue('unavilibilityPersonCollection', [aboutMeQuery.data.userId])
    }, [aboutMeQuery.data])

    useEffect(() => {
        if (unavailabilityListByMonth.data) {
            console.log(unavailabilityListByMonth.data)
            const newEvents: EventInput[] = unavailabilityListByMonth.data
                .filter((d) => {
                    let order = false,
                        orderStatus = true,
                        employee = false,
                        unavalibility = false
                    if (formikFilter.values.orderIdCollection.length == 0) order = true
                    if (formikFilter.values.unavilibilityPersonCollection.length == 0) employee = true

                    if (
                        d.typeOfUnavailability == 'BUSY' &&
                        d.orderId &&
                        formikFilter.values.orderIdCollection.indexOf(d.orderId) > -1
                    )
                        order = true
                    if (
                        d.typeOfUnavailability != 'BUSY' &&
                        d.assignedToId &&
                        formikFilter.values.unavilibilityPersonCollection.indexOf(d.assignedToId) > -1
                    )
                        employee = true

                    //TODO filter by order status - potrzebne w zwracanej liście

                    if (formikFilter.values.unavilibilityTypeCollection.length == 0) unavalibility = true
                    if (formikFilter.values.unavilibilityTypeCollection.indexOf(d.typeOfUnavailability) > -1)
                        unavalibility = true

                    return (order || employee) && orderStatus && unavalibility
                })
                .map((d) => {
                    return {
                        id: d.id.toString(),
                        groupId: d.typeOfUnavailability,
                        title:
                            d.typeOfUnavailability == 'BUSY' && d.orderStageName
                                ? d.orderStageName
                                : typeOfUnavailabilityName(d.typeOfUnavailability),
                        start: d.unavailableFrom,
                        end: d.unavailableTo,
                        display: 'block',
                        color: d.typeOfUnavailability == 'BUSY' ? 'blue' : 'red',
                    }
                })
            console.log(newEvents)
            setEvents(newEvents)
        }
    }, [unavailabilityListByMonth.data, formikFilter.values])

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
            <Box>
                <IconButton onClick={() => setFilterOpen(!filterOpen)}>
                    <FilterAltIcon />
                </IconButton>
            </Box>
            <Box sx={{ display: filterOpen ? '' : 'none' }}>
                <CalendarFilter formikFilter={formikFilter} />
            </Box>
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
