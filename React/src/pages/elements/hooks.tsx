import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'

import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Element } from '../../types/model/Element'
import { deleteElement, getElementDetails, postElement, updateElement } from '../../api/element.api'
import useError from '../../hooks/useError'

export const useAddElement = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postElement,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowy element utworzony pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/elements/${data.id}`)
                    else navigate(`/elements`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditElement = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateElement,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w elemencie zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteElement = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteElement,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Element został usunięty</Box>,
            })
            navigate('/elements')
        },
        onError: showError,
    })
}

export const useElementData = (id: string | undefined) => {
    return useQuery<Element, AxiosError>(
        ['element', { id: id }],
        async () => getElementDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
