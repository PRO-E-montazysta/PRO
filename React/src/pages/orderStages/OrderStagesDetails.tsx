import { Box, CircularProgress, Typography } from '@mui/material'
import { useNavigate, useParams } from 'react-router-dom'
import { useContext, useEffect, useRef, useState } from 'react'
import { OrderStage } from '../../types/model/OrderStage'
import { useMutation, useQuery } from 'react-query'
import { AxiosError } from 'axios'
import OrderStageCard from './OrderStageCard'
import { createOrderStage, getAllOrderStages } from '../../api/orderStage.api'
import { v4 as uuidv4 } from 'uuid'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'

type OrderStagesDetailsProps = {
    isAddOrderStageVisible: boolean
}

const OrderStagesDetails = ({ isAddOrderStageVisible }: OrderStagesDetailsProps) => {
    const navigation = useNavigate()
    const params = useParams()
    const scrollerRef = useRef(null)
    const { showDialog } = useContext(DialogGlobalContext)


    const queryOrderStages = useQuery<Array<OrderStage>, AxiosError>(['orderStage-list'], () =>
        getAllOrderStages(params.id!),
    )

    useEffect(() => {
        if (isAddOrderStageVisible === true) {
            handleScroll(scrollerRef)
        }
    }, [isAddOrderStageVisible])

    const handleScroll = (ref: any) => {
        setTimeout(() => {
            ref.current.scrollIntoView({
                behavior: 'smooth',
                block: 'nearest',
                inline: 'end',
            })
        }, 350)
    }

    const { isLoading, isError, error, mutate } = useMutation({
        mutationFn: createOrderStage,
        onSuccess(data) {
            navigation('/', { replace: true })
        },
        onError() {
            showDialog({
                title: 'Błąd podczas dodawania etapu',
                btnOptions: [
                    { text: 'Ok', value: 0, },
                ],
            })
        },
    })

    const getListOfStages = () => {
        return (
            <>
                {queryOrderStages.data!.map((stage, index) => (
                    <OrderStageCard
                        key={uuidv4()}
                        index={index.toString()}
                        stage={stage}
                        isLoading={isLoading}
                        isDisplaying={true}
                    />
                ))}
                {isAddOrderStageVisible ? (
                    <>
                        <OrderStageCard
                            key={uuidv4()}
                            isLoading={isLoading}
                            isDisplaying={false}
                        />
                        <div ref={scrollerRef}></div>
                    </>
                ) : null}
            </>
        )
    }

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
        getListOfStages()
    )
}

export default OrderStagesDetails
