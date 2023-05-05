import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import useError from '../../hooks/useError'
import { createOrderStage } from '../../api/orderStage.api'

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
