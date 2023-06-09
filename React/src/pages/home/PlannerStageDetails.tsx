import { Box, Button, ClickAwayListener, IconButton, Typography } from '@mui/material'
import { Moment } from 'moment'
import moment from 'moment'
import ForwardIcon from '@mui/icons-material/Forward'

export type PopupPlannerInfo = {
    isOpen: boolean
    title: string
    fromDate: Moment
    toDate: Moment
    fitterAssignedIds: number[]
    fitterAvaliableIds: number[]
}

export const cleanPlannerInfo = {
    isOpen: false,
    title: '',
    fromDate: moment(),
    toDate: moment(),
    fitterAssignedIds: [],
    fitterAvaliableIds: [],
}

type PlannerStageDetailsProps = {
    popupEventInfo: PopupPlannerInfo
    setPopupEventInfo: (popupEventInfo: PopupPlannerInfo) => void
}

const PlannerStageDetails = ({ popupEventInfo, setPopupEventInfo }: PlannerStageDetailsProps) => {
    return (
        <ClickAwayListener onClickAway={() => popupEventInfo.isOpen && setPopupEventInfo(cleanPlannerInfo)}>
            <Box
                sx={{
                    position: 'fixed',
                    top: '50vh',
                    left: '50vw',
                    width: '400px',
                    height: '500px',
                    transform: 'translate(-200px, -250px)',
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
                    onClick={() => setPopupEventInfo(cleanPlannerInfo)}
                >
                    x
                </Button>

                <Typography variant="h5" sx={{ mb: '15px' }}>
                    {popupEventInfo.title}
                </Typography>
                <Box
                    sx={{
                        display: 'grid',
                        gridTemplateColumns: '1fr 1fr',
                        gap: '10px 20px',
                    }}
                >
                    <Box>
                        <Typography variant="body2">Rozpoczęcie</Typography>
                        <Typography variant="body1">{popupEventInfo.fromDate.format('HH:mm DD-MM-YYYY')}</Typography>
                    </Box>
                    <Box>
                        <Typography variant="body2">Zakończenie</Typography>
                        <Typography variant="body1">{popupEventInfo.toDate.format('HH:mm DD-MM-YYYY')}</Typography>
                    </Box>
                    <FittersList
                        key={'signed'}
                        list={[1, 2, 3, 4, 5, 6, 7, 8, 9]}
                        title="Przypisani"
                        arrayType="right"
                    />
                    <FittersList key={'avaliable'} list={[11, 12, 13, 14, 15, 16]} title="Dostępni" arrayType="left" />
                </Box>
                <Box sx={{ mt: '10px', float: 'right', display: 'flex', gap: '10px' }}>
                    <Button variant="outlined" sx={{ color: 'black' }}>
                        Anuluj
                    </Button>
                    <Button variant="contained">Zapisz</Button>
                </Box>
            </Box>
        </ClickAwayListener>
    )
}

export default PlannerStageDetails

type FittersListProps = {
    title: string
    list: number[]
    arrayType: 'right' | 'left'
}
const FittersList = ({ list, title, arrayType }: FittersListProps) => {
    return (
        <Box>
            <Typography>{title}</Typography>
            <Box sx={{ border: '1px solid black', overflowY: 'scroll', height: '300px' }}>
                {list.map((f, i) => {
                    return (
                        <Box
                            key={i}
                            sx={{ p: '0 5px', alignItems: 'center', justifyContent: 'space-between', display: 'flex' }}
                        >
                            {arrayType == 'left' ? (
                                <IconButton size="small" sx={{ transform: 'rotate(180deg)' }}>
                                    <ForwardIcon />
                                </IconButton>
                            ) : null}
                            {f}
                            {arrayType == 'right' ? (
                                <IconButton size="small">
                                    <ForwardIcon />
                                </IconButton>
                            ) : null}
                        </Box>
                    )
                })}
            </Box>
        </Box>
    )
}
