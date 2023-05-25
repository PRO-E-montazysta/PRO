import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Warehouse } from '../../types/model/Warehouse'
import { deleteWarehouse, getWarehouseDetails, postWarehouse, updateWarehouse } from '../../api/warehouse.api'
import useError from '../../hooks/useError'
import { useAddLocation, useEditLocation } from '../../components/localization/hooks'

export const useAddWarehouse = (onSuccessCallback: (data: any) => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postWarehouse,
        onSuccess: onSuccessCallback,
        onError: showError,
    })
}

export const useEditWarehouse = (onSuccessCallback: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateWarehouse,
        onSuccess: onSuccessCallback,
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

export const useAddWarehouseLocation = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    return useAddLocation((data) => {
        showDialog({
            btnOptions: [
                {
                    text: 'OK',
                    value: 0,
                },
            ],
            title: 'Sukces',
            content: <Box>Nowy magazyn utworzono pomyślnie</Box>,
            callback: () => {
                if (data.warehouseId) navigate(`/warehouses/${data.warehouseId}`)
                else navigate(`/warehouses`)
            },
        })
    })
}

export const useEditWarehouseLocation = (onSuccessCallback: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    return useEditLocation((data) => {
        showDialog({
            btnOptions: [
                {
                    text: 'OK',
                    value: 0,
                },
            ],
            title: 'Sukces!',
            content: <Box>Zmiany w magazynie zostały zapisane</Box>,
            callback: () => onSuccessCallback(data),
        })
    })
}
