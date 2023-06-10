import { Box } from '@mui/material'
import { useContext } from 'react'
import { useMutation, useQueryClient } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import useError from '../../hooks/useError'
import {
    createOrderStage,
    orderStageNextStatus,
    orderStagePreviousStatus,
    updateOrderStage,
} from '../../api/orderStage.api'

export const useAddOrderStage = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: createOrderStage,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowy etap utworzono pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(0)
                    else navigate(`/orders`)
                },
            })
        },
        onError: showError,
    })
}

export const useUpdateOrderStage = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateOrderStage,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Zedytowano etap pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(0)
                    else navigate(`/orders`)
                },
            })
        },
        onError: showError,
    })
}

export const useOrderStageNextStatus = (onSuccessCallback: () => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: orderStageNextStatus,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Status został zmieniony</Box>,
            })
            queryClient.invalidateQueries('orderStage-list')
            onSuccessCallback()
        },
        onError: showError,
    })
}

export const useOrderStagePreviousStatus = (onSuccessCallback: () => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: orderStagePreviousStatus,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Status został zmieniony</Box>,
            })
            queryClient.invalidateQueries('orderStage-list')
            onSuccessCallback()
        },
        onError: showError,
    })
}
