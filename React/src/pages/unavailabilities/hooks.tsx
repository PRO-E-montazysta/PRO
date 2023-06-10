import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import {
    deleteUnavailability,
    getUnavailabilityDetails,
    updateUnavailability,
    postUnavailability,
} from '../../api/unavailability.api'
import useError from '../../hooks/useError'
import { Unavailability } from '../../types/model/Unavailability'

export const useAddUnavailability = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postUnavailability,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowa nieobecność utworzona pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/unavailabilities/${data.id}`)
                    else navigate(`/unavailabilities`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditUnavailability = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateUnavailability,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w niedostępności zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteUnavailability = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteUnavailability,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Nieobecność została usunięta</Box>,
            })
            navigate('/unavailabilities')
        },
        onError: showError,
    })
}

export const useUnavailabilityData = (id: string | undefined) => {
    return useQuery<Unavailability, AxiosError>(
        ['unavailability', { id: id }],
        async () => getUnavailabilityDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
