import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'

import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Client } from '../../types/model/Client'
import { deleteClient, getClientDetails, postClient, updateClient } from '../../api/client.api'
import useError from '../../hooks/useError'

export const useAddClient = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postClient,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowy klient utworzony pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/clients/${data.id}`)
                    else navigate(`/clients`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditClient = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateClient,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w kliencie zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteClient = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteClient,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Klient został usunięty</Box>,
                callback: () => () => {
                    onSuccess()
                    navigate('/clients')
                },
            })
        },
        onError: showError,
    })
}

export const useClientData = (id: string | undefined) => {
    return useQuery<Client, AxiosError>(
        ['client', { id: id }],
        async () => getClientDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
