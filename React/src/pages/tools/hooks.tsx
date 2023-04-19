import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Tool } from '../../types/model/Tool'
import { deleteTool, getToolDetails, postTool, updateTool } from '../../api/tool.api'
import useError from '../../hooks/useError'

export const useAddTool = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postTool,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowe narzędzie utworzone pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/tools/${data.id}`)
                    else navigate(`/tools`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditTool = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateTool,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w narzędziu zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteTool = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteTool,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Narzędzie zostało usunięte</Box>,
                callback: () => () => {
                    onSuccess()
                    navigate('/tools')
                },
            })
        },
        onError: showError,
    })
}

export const useToolData = (id: string | undefined) => {
    return useQuery<Tool, AxiosError>(['tool', { id: id }], async () => getToolDetails(id && id != 'new' ? id : ''), {
        enabled: !!id && id != 'new',
    })
}
