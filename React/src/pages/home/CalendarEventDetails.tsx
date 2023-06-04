import FullCalendar from '@fullcalendar/react' // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid' // a plugin!
import timeGridPlugin from '@fullcalendar/timegrid'
import { Box, Button, ClickAwayListener, Paper, Typography } from '@mui/material'
import { EventInput, EventSourceInput } from '@fullcalendar/core'
import { useMemo, useState } from 'react'
import { Moment } from 'moment'
import moment from 'moment'
import { NavLink, Navigate } from 'react-router-dom'
import { events, stages } from './data'

export type PopupEventInfo = {
    isOpen: boolean
    title: string
    subtitle?: string
    fromDate: Moment
    toDate: Moment
    status: string
    link: string
}

export const cleanEventInfo = {
    isOpen: false,
    title: '',
    subtitle: '',
    fromDate: moment(),
    toDate: moment(),
    status: '',
    link: '',
}
type CalendarEventDetailsProps = {
    popupEventInfo: PopupEventInfo
    setPopupEventInfo: (popupEventInfo: PopupEventInfo) => void
}
const CalendarEventDetails = ({ popupEventInfo, setPopupEventInfo }: CalendarEventDetailsProps) => {
    return (
        <ClickAwayListener onClickAway={() => popupEventInfo.isOpen && setPopupEventInfo(cleanEventInfo)}>
            <Box
                sx={{
                    position: 'fixed',
                    top: '50vh',
                    left: '50vw',
                    width: '400px',
                    height: '200px',
                    transform: 'translate(-200px, -100px)',
                    zIndex: 100,
                    backgroundColor: 'white',
                    border: '1px solid black',
                    display: popupEventInfo.isOpen ? '' : 'none',
                    borderRadius: '5px',
                    padding: '15px',
                }}
            >
                <Button
                    sx={{
                        float: 'right',
                        p: 0,
                        minWidth: '30px',
                        color: 'black',
                    }}
                    onClick={() => setPopupEventInfo(cleanEventInfo)}
                >
                    x
                </Button>
                {popupEventInfo.subtitle && (
                    <Typography variant="body1" sx={{ mb: '-5px' }}>
                        {popupEventInfo.subtitle}
                    </Typography>
                )}
                <NavLink
                    to={popupEventInfo.link}
                    // className={({ isActive, isPending }) => (isPending ? 'pending' : isActive ? 'active' : '')}
                >
                    <Typography
                        variant="h5"
                        sx={{ mb: '20px', textDecoration: 'underline', cursor: 'pointer', color: 'black' }}
                    >
                        {popupEventInfo.title}
                    </Typography>
                </NavLink>
                <Box
                    sx={{
                        display: 'grid',
                        gridTemplateColumns: '175px 175px',
                        gap: '10px 20px',
                    }}
                >
                    <Typography variant="body1" textAlign={'right'}>
                        Od
                    </Typography>
                    <Typography variant="body1">{popupEventInfo.fromDate.format('HH:mm DD-MM-YYYY')}</Typography>
                    <Typography variant="body1" textAlign={'right'}>
                        Do
                    </Typography>
                    <Typography variant="body1">{popupEventInfo.toDate.format('HH:mm DD-MM-YYYY')}</Typography>
                    <Typography variant="body1" textAlign={'right'}>
                        Status
                    </Typography>
                    <Typography variant="body1">{popupEventInfo.status}</Typography>
                </Box>
            </Box>
        </ClickAwayListener>
    )
}

export default CalendarEventDetails
