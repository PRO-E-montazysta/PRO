import { Box, CircularProgress, Typography } from '@mui/material'
import { AxiosError } from 'axios'
import { useLayoutEffect, useRef } from 'react'
import { useQuery } from 'react-query'
import { useParams } from 'react-router-dom'
import { getAllOrderStagesForOrder } from '../../api/orderStage.api'
import { OrderStage } from '../../types/model/OrderStage'
import OrderStageCard from './OrderStageCard'

type OrderStagesDetailsProps = {
    addingNewStage: boolean
    setAddingNewStage: (value: boolean) => void
}

const OrderStagesDetails = ({ addingNewStage, setAddingNewStage }: OrderStagesDetailsProps) => {
    // const navigation = useNavigate()
    const params = useParams()
    const scrollerRef = useRef(null)
    // const { showDialog } = useContext(DialogGlobalContext)

    const queryOrderStages = useQuery<Array<OrderStage>, AxiosError>({
        queryKey: ['orderStageForOrder', { id: params.id }],
        queryFn: () => getAllOrderStagesForOrder(params.id!),
    })

    useLayoutEffect(() => {
        if (addingNewStage === true) handleScroll(scrollerRef)
    }, [addingNewStage])

    const handleScroll = (ref: any) => {
        setTimeout(() => {
            ref.current.scrollIntoView({
                behavior: 'smooth',
                block: 'nearest',
                inline: 'end',
            })
        }, 350)
    }

    // const { isLoading, isError, error, mutate } = useMutation({
    //     mutationFn: createOrderStage,
    //     onSuccess(data) {
    //         navigation('/', { replace: true })
    //     },
    //     onError() {
    //         showDialog({
    //             title: 'Błąd podczas dodawania etapu',
    //             btnOptions: [{ text: 'Ok', value: 0 }],
    //         })
    //     },
    // })

    return queryOrderStages.isLoading || queryOrderStages.isError ? (
        <Box
            sx={{
                display: 'flex',
                minHeight: '200px',
                justifyContent: 'center',
                alignItems: 'center',
            }}
        >
            {queryOrderStages.isLoading ? <CircularProgress /> : <Typography>Nie znaleziono zlecenia</Typography>}
        </Box>
    ) : (
        <Box>
            {queryOrderStages.data!.map((stage, index) => (
                <OrderStageCard
                    key={stage.id}
                    index={index.toString()}
                    stage={stage}
                    addingNewStag={false}
                    setAddingNewStage={setAddingNewStage}
                />
            ))}
            {addingNewStage && (
                <>
                    <OrderStageCard key={'new'} addingNewStag={addingNewStage} setAddingNewStage={setAddingNewStage} />
                    <div ref={scrollerRef}></div>
                </>
            )}
        </Box>
    )
}

export default OrderStagesDetails
