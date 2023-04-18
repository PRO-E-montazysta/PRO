import { Box, Typography } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { deleteOrder, getOrderDetails, postOrder, updateOrder } from '../../api/order.api'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Order } from '../../types/model/Order'

export const useAddOrder = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
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
        onError(error: any) {
            console.error(error)
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Błąd!',
                content: (
                    <Box>
                        {error.response && process.env.NODE_ENV === 'development'
                            ? error.response.data
                            : 'Wewnętrzny błąd serwera. Skontaktuj się z administratorem'}
                    </Box>
                ),
            })
        },
    })
}

export const useEditOrder = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
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
        onError(error: any) {
            console.error(error)
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Błąd!',
                content: (
                    <Box>
                        {error.response && process.env.NODE_ENV === 'development'
                            ? error.response.data
                            : 'Wewnętrzny błąd serwera. Skontaktuj się z administratorem'}
                    </Box>
                ),
            })
        },
    })
}
export const useDeleteOrder = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
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
        onError(error: any) {
            console.error(error)
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Błąd!',
                content: (
                    <Box>
                        {error.response && process.env.NODE_ENV === 'development'
                            ? error.response.data
                            : 'Wewnętrzny błąd serwera. Skontaktuj się z administratorem'}
                    </Box>
                ),
            })
        },
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
