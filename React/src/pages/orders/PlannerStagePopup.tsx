import { Box, Button, ClickAwayListener, Typography } from '@mui/material'
import { useContext, useLayoutEffect, useState } from 'react'
import { useOrderStageFittersMutation, useOrderStageQuery } from './hooks'
import { PopupPlannerInfo, cleanPlannerInfo } from './FittersList'
import PlannerStageDetails from './PlannerStageDetails'
import { useUpdateOrderStage } from '../orderStages/hooks'
import { UpdateOrderStage } from '../../types/model/OrderStage'
import { useQueryClient } from 'react-query'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'

type PlannerStagePopupProps = {
    popupEventInfo: PopupPlannerInfo
    setPopupEventInfo: (popupEventInfo: PopupPlannerInfo) => void
    readonly: boolean
}

const PlannerStagePopup = ({ popupEventInfo, setPopupEventInfo, readonly }: PlannerStagePopupProps) => {
    const [assignedFitters, setAssignedFitters] = useState<number[]>([])
    const { showDialog } = useContext(DialogGlobalContext)

    const orderStageQuery = useOrderStageQuery(popupEventInfo.stageId)
    const queryClient = useQueryClient()
    const updateOrderStage = useUpdateOrderStage(() => {
        queryClient.refetchQueries({
            queryKey: ['orderStageForOrder', { id: orderStageQuery.data?.orderId.toString() }],
        })
        queryClient.refetchQueries({
            queryKey: ['orderStage', { id: popupEventInfo.stageId }],
        })
        showDialog({
            title: 'Sukces',
            content: 'Montażyści zapisani',
            btnOptions: [
                {
                    text: 'Ok',
                    value: 0,
                },
            ],
            callback: () => setPopupEventInfo({ ...popupEventInfo, isOpen: false }),
        })
    })

    useLayoutEffect(() => {
        if (orderStageQuery.data) {
            console.log(orderStageQuery.data)
            setAssignedFitters(orderStageQuery.data.fitters)
        }
    }, [orderStageQuery.status, popupEventInfo.isOpen])

    const saveChanges = () => {
        const currentData = orderStageQuery.data
        const id = popupEventInfo.stageId ? parseInt(popupEventInfo.stageId) : null
        if (currentData && id) {
            const uos: UpdateOrderStage = {
                ...currentData,
                fitters: assignedFitters,
                orderStageId: id.toString(),
                orderId: currentData.orderId.toString(),
            }
            updateOrderStage.mutate(uos)
        }
    }

    const handleCancel = () => {
        setPopupEventInfo({ ...popupEventInfo, isOpen: false })
    }

    return (
        <ClickAwayListener onClickAway={() => popupEventInfo.isOpen && setPopupEventInfo(cleanPlannerInfo)}>
            <Box
                sx={{
                    position: 'fixed',
                    top: '50vh',
                    left: '50vw',
                    width: '600px',
                    transform: 'translate(-300px, -250px)',
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
                {!readonly && (
                    <Box sx={{ mt: '10px', float: 'right', display: 'flex', gap: '10px' }}>
                        <Button variant="outlined" sx={{ color: 'black' }} onClick={handleCancel}>
                            Anuluj
                        </Button>
                        <Button variant="contained" onClick={saveChanges}>
                            Zapisz
                        </Button>
                    </Box>
                )}
            </Box>
        </ClickAwayListener>
    )
}

export default PlannerStagePopup
