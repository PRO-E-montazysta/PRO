import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery, useQueryClient } from 'react-query'
import { useNavigate } from 'react-router-dom'
import {
    deleteOrder,
    getOrderDetails,
    orderNextStatus,
    orderPreviousStatus,
    postOrder,
    updateOrder,
} from '../../api/order.api'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Order } from '../../types/model/Order'
import useError from '../../hooks/useError'
import { useAddLocation, useEditLocation, useLocationData } from '../../components/localization/hooks'

export const useAddOrder = (onSuccessCallback: (data: any) => void) => {
    const showError = useError()
    return useMutation({
        mutationFn: postOrder,
        onSuccess: onSuccessCallback,
        onError: showError,
    })
}

export const useEditOrder = (onSuccessCallback: (data: any) => void) => {
    const showError = useError()
    return useMutation({
        mutationFn: updateOrder,
        onSuccess: onSuccessCallback,
        onError: showError,
    })
}
export const useDeleteOrder = (onSuccessCallback: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteOrder,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zlecenie zostało usunięte</Box>,
                callback: () => () => {
                    onSuccessCallback()
                    navigate('/orders')
                },
            })
        },
        onError: showError,
    })
}

export const useOrderData = (id: string | undefined) => {
    return useQuery<Order, AxiosError>(
        ['order', { id: id }],
        async () => getOrderDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}

export const useAddOrderLocation = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    return useAddLocation((data) => {
        showDialog({
            btnOptions: [
                {
                    text: 'OK',
                    value: 0,
                },
            ],
            title: 'Sukces',
            content: <Box>Nowe zlecenie utworzono pomyślnie</Box>,
            callback: () => {
                if (data.orderId) navigate(`/orders/${data.orderId}`)
                else navigate(`/orders`)
            },
        })
    })
}

export const useEditOrderLocation = (onSuccessCallback: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    return useEditLocation((data) => {
        showDialog({
            btnOptions: [
                {
                    text: 'OK',
                    value: 0,
                },
            ],
            title: 'Sukces!',
            content: <Box>Zmiany w zleceniu zostały zapisane</Box>,
            callback: () => onSuccessCallback(data),
        })
    })
}

export const useOrderNextStatus = (onSuccessCallback: () => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: orderNextStatus,
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
            queryClient.invalidateQueries('orders')
            onSuccessCallback()
        },
        onError: showError,
    })
}

export const useOrderPreviousStatus = (onSuccessCallback: () => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    const queryClient = useQueryClient()
    return useMutation({
        mutationFn: orderPreviousStatus,
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
            queryClient.invalidateQueries('orders')
            onSuccessCallback()
        },
        onError: showError,
    })
}
