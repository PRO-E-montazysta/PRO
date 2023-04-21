import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { ToolType } from '../../types/model/ToolType'
import { deleteToolType, getToolTypeDetails, postToolType, updateToolType } from '../../api/toolType.api'
import useError from '../../hooks/useError'

export const useAddToolType = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postToolType,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowy typ narzędzia utworzony pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/tooltypes/${data.id}`)
                    else navigate(`/tooltypes`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditToolType = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateToolType,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w typie nadzędzia zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteToolType = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteToolType,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Typ narzędzia został usunięty</Box>,
                callback: () => () => {
                    onSuccess()
                    navigate('/tooltypes')
                },
            })
        },
        onError: showError,
    })
}

export const useToolTypeData = (id: string | undefined) => {
    return useQuery<ToolType, AxiosError>(
        ['tooltype', { id: id }],
        async () => getToolTypeDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
