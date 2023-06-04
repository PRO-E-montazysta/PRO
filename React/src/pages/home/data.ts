import { EventInput, EventSourceInput } from '@fullcalendar/core'
import { OrderStage } from './Home'
import moment from 'moment'

export const stages: OrderStage[] = [
    {
        eventId: 1,
        id: 1,
        orderId: 1,
        name: 'Test OrderStage 1',
        orderName: 'Test Order 1',
        status: 'PLANNING',
        startDate: moment('2023-05-23T08:00'),
        endDate: moment('2023-05-23T16:00'),
        plannedFittersNumber: 5,
        fitters: [],
    },
    {
        eventId: 2,
        id: 2,
        orderId: 2,
        name: 'Test OrderStage 2',
        orderName: 'Test Order 1',
        status: 'PLANNING',
        startDate: moment('2023-05-24T08:00'),
        endDate: moment('2023-05-24T12:00'),
        plannedFittersNumber: 5,
        fitters: [],
    },
]

export const events: EventInput[] = [
    { title: 'urlop', start: '2023-05-08', end: '2023-05-12', backgroundColor: 'red' },
    { title: 'urlopp', start: '2023-05-08', end: '2023-05-10', backgroundColor: 'red' },
    // { title: 'Robota u radka', start: '2023-05-01', end: '2023-05-06', backgroundColor: 'blue' },

    {
        title: 'Spotkanie komitetu planowania imprez',
        // backgroundColor: 'red',
        start: '2023-05-12 15:00',
        end: '2023-05-12 16:00',
        color: 'blue',
        // textColor: 'white',
        display: 'block',
    },
    {
        id: '11',
        title: 'z1 e1',
        start: '2023-05-15 08:00',
        end: '2023-05-15 14:00',
        color: 'blue',
        display: 'block',
    },
    {
        id: '12',
        title: 'z1 e2',
        start: '2023-05-15 14:00',
        end: '2023-05-15 17:00',
        color: 'blue',
        display: 'block',
    },
    {
        id: '13',
        title: 'z1 e3',
        start: '2023-05-16 08:00',
        end: '2023-05-16 16:00',
        color: 'blue',
        display: 'block',
    },
    {
        id: '21',
        title: 'z2 e1',
        start: '2023-05-15 08:00',
        end: '2023-05-15 13:00',
        color: 'purple',
        display: 'block',
    },
    {
        id: '22',
        title: 'z2 e2',
        start: '2023-05-15 13:00',
        end: '2023-05-15 16:00',
        color: 'purple',
        display: 'block',
    },
    {
        id: '23',
        title: 'z2 e3',
        start: '2023-05-16 08:00',
        end: '2023-05-16 16:00',
        color: 'purple',
        display: 'block',
    },
]
