import { Box, Button, ClickAwayListener, Typography } from '@mui/material'
import { NavLink } from 'react-router-dom'

export type PopupEventInfo = {
    isOpen: boolean
    title: string
    subtitle?: string
    infoArray: {
        label: string
        value: string
    }[]
    link: string
}

export const cleanEventInfo: PopupEventInfo = {
    isOpen: false,
    title: '',
    subtitle: '',
    infoArray: [],
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
                <NavLink to={popupEventInfo.link}>
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
                    {popupEventInfo.infoArray.map((row, i) => {
                        return (
                            <>
                                <Typography key={`label-${i}`} variant="body1" textAlign={'right'}>
                                    {row.label}
                                </Typography>
                                <Typography key={`value-${i}`} variant="body1">
                                    {row.value}
                                </Typography>
                            </>
                        )
                    })}
                </Box>
            </Box>
        </ClickAwayListener>
    )
}

export default CalendarEventDetails
