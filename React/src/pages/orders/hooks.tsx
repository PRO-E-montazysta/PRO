import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { deleteOrder, getOrderDetails, postOrder, updateOrder } from '../../api/order.api'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Order } from '../../types/model/Order'
import useError from '../../hooks/useError'
import { getLocationById, postLocation } from '../../api/location.api'

export const useAddOrder = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postOrder,
        onSuccess(data) {
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
                    if (data.id) navigate(`/orders/${data.id}`)
                    else navigate(`/orders`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditOrder = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateOrder,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w zleceniu zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteOrder = (onSuccess: () => void) => {
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
                    onSuccess()
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

export const useLocationData = (id: string | undefined) => {
    return useQuery<Location, AxiosError>(
        ['location', { id: id }],
        async () => getLocationById(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}

export const useAddLocation = (onSuccessCallback: (data: any) => void) => {
    const showError = useError()
    return useMutation({
        mutationFn: postLocation,
        onSuccess: onSuccessCallback,
        onError: showError,
    })
}
