import { Box } from '@mui/material'
import { useContext } from 'react'
import { useMutation } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import useError from '../../hooks/useError'
import { createOrderStage, updateOrderStage } from '../../api/orderStage.api'

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
