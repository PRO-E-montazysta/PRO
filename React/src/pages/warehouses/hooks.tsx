import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Warehouse } from '../../types/model/Warehouse'
import { deleteWarehouse, getWarehouseDetails, postWarehouse, updateWarehouse } from '../../api/warehouse.api'
import useError from '../../hooks/useError'

export const useAddWarehouse = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postWarehouse,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowy magazyn utworzony pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/warehouses/${data.id}`)
                    else navigate(`/warehouses`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditWarehouse = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateWarehouse,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w magazynie zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteWarehouse = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteWarehouse,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Magazyn został usunięty</Box>,
                callback: () => () => {
                    onSuccess()
                    navigate('/warehouses')
                },
            })
        },
        onError: showError,
    })
}

export const useWarehouseData = (id: string | undefined) => {
    return useQuery<Warehouse, AxiosError>(
        ['warehouse', { id: id }],
        async () => getWarehouseDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
