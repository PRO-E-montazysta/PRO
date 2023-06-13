import { Box, Button, ClickAwayListener, IconButton, Typography } from '@mui/material'
import { Moment } from 'moment'
import moment from 'moment'
import ForwardIcon from '@mui/icons-material/Forward'
import { useQuery } from 'react-query'
import { OrderStage } from '../../types/model/OrderStage'
import { getOrderStageById } from '../../api/orderStage.api'
import { AxiosError } from 'axios'
import { useEffect, useState } from 'react'
import { getAllFitter, getFittersAvailable } from '../../api/fitter.api'
import { useOrderStageFittersMutation, useOrderStageQuery } from './hooks'
import FittersList, { PopupPlannerInfo, cleanPlannerInfo } from './FittersList'
import PlannerStageDetails from './PlannerStageDetails'

type PlannerStagePopupProps = {
    popupEventInfo: PopupPlannerInfo
    setPopupEventInfo: (popupEventInfo: PopupPlannerInfo) => void
    readonly: boolean
}

const PlannerStagePopup = ({ popupEventInfo, setPopupEventInfo, readonly }: PlannerStagePopupProps) => {
    const [assignedFitters, setAssignedFitters] = useState<number[]>([])
    const [avaliableFitters, setAvaliableFitters] = useState<number[]>([])

    const orderStageQuery = useOrderStageQuery(popupEventInfo.stageId)
    const updateOrderStageFitters = useOrderStageFittersMutation((data) => {
        console.log(data)
    })
    console.log('toto', orderStageQuery.data?.plannedStartDate)

    const avaliableFittersQuery = useQuery<any[], AxiosError>(
        ['fitters', { id: popupEventInfo.stageId }],
        () => getFittersAvailable(orderStageQuery.data?.plannedStartDate!, orderStageQuery.data?.plannedEndDate!),
        {
            enabled:
                orderStageQuery.data?.plannedStartDate != undefined &&
                orderStageQuery.data?.plannedEndDate != undefined,
        },
    )

    useEffect(() => {
        if (avaliableFittersQuery.data) {
            console.log(avaliableFittersQuery.data)
            setAvaliableFitters(avaliableFittersQuery.data.map((d) => d.id))
        }
    }, [avaliableFittersQuery.data])

    useEffect(() => {
        if (orderStageQuery.data) {
            console.log(orderStageQuery.data)
            setAssignedFitters(orderStageQuery.data.fitters)
        }
    }, [orderStageQuery.data])

    const addFitter = (id: number) => {
        setAssignedFitters([...assignedFitters, id])
        setAvaliableFitters(avaliableFitters.filter((f) => f != id))
    }
    const removeFitter = (id: number) => {
        setAvaliableFitters([...avaliableFitters, id])
        setAssignedFitters(assignedFitters.filter((f) => f != id))
    }

    const saveChanges = () => {
        const currentData = orderStageQuery.data
        const id = popupEventInfo.stageId ? parseInt(popupEventInfo.stageId) : null
        if (currentData && id)
            updateOrderStageFitters.mutate({
                stageId: id,
                fitters: assignedFitters,
            })
    }

    return (
        <ClickAwayListener onClickAway={() => popupEventInfo.isOpen && setPopupEventInfo(cleanPlannerInfo)}>
            <Box
                sx={{
                    position: 'fixed',
                    top: '50vh',
                    left: '50vw',
                    width: '600px',
                    height: '500px',
                    transform: 'translate(-300px, -250px)',
                    zIndex: 2000,
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
                    {orderStageQuery.data?.name}
                </Typography>
                <PlannerStageDetails
                    dateFrom={orderStageQuery.data?.plannedStartDate}
                    dateTo={orderStageQuery.data?.plannedEndDate}
                    fitters={assignedFitters}
                    setFitters={(fitters) => setAssignedFitters(fitters)}
                    readonly={readonly}
                    orderStageId={popupEventInfo.stageId}
                />
                <Box sx={{ mt: '10px', float: 'right', display: 'flex', gap: '10px' }}>
                    <Button
                        variant="outlined"
                        sx={{ color: 'black' }}
                        onClick={() => setPopupEventInfo({ ...popupEventInfo, isOpen: false })}
                    >
                        Anuluj
                    </Button>
                    <Button variant="contained" onClick={saveChanges}>
                        Zapisz
                    </Button>
                </Box>
            </Box>
        </ClickAwayListener>
    )
}

export default PlannerStagePopup
