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

export const useAddOrderStage = (onSuccessCallback: (data: any) => void) => {
    const showError = useError()
    return useMutation({
        mutationFn: createOrderStage,
        onSuccess: onSuccessCallback,
        onError: showError,
    })
}

export const useUpdateOrderStage = (onSuccessCallback: (data: any) => void) => {
    const showError = useError()
    return useMutation({
        mutationFn: updateOrderStage,
        onSuccess: onSuccessCallback,
        onError: showError,
    })
}

export const useOrderStageNextStatus = (onSuccessCallback: () => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: orderStageNextStatus,
        onSuccess: (data) => {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 5,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Status został zmieniony</Box>,
                callback: (result) => {
                    onSuccessCallback()
                },
            })
        },
        onError: showError,
    })
}

export const useOrderStagePreviousStatus = (onSuccessCallback: () => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: orderStagePreviousStatus,
        onSuccess: (data) => {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 5,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Status został zmieniony</Box>,
                callback: (result) => {
                    onSuccessCallback()
                },
            })
        },
        onError: showError,
    })
}
