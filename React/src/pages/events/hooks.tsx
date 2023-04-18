import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'

import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { ElementEvent } from '../../types/model/ElementEvent'
import useError from '../../hooks/useError'
import { deleteElementEvent, getElementEventDetails, postElementEvent, updateElementEvent } from '../../api/event.api'

export const useAddElementEvent = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postElementEvent,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowe zgłoszenie utworzone pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/elementevent/${data.id}`)
                    else navigate(`/events`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditElementEvent = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateElementEvent,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w zgłoszeniu zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteElementEvent = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteElementEvent,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zgłoszenie zostało usunięte</Box>,
                callback: () => () => {
                    onSuccess()
                    navigate('/events')
                },
            })
        },
        onError: showError,
    })
}

export const useElementEventData = (id: string | undefined) => {
    return useQuery<ElementEvent, AxiosError>(
        ['element-event', { id: id }],
        async () => getElementEventDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
