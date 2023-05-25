import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getElementInWarehouseDetails, updateElementInWarehouse } from '../../api/elementInWarehouse.api'
import useError from '../../hooks/useError'
import { ElementInWarehouse } from '../../types/model/ElementInWarehouse'

export const useEditElementInWarehouse = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateElementInWarehouse,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w stanie magazynowym zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}

export const useElementInWarehouseData = (id: string | undefined) => {
    return useQuery<ElementInWarehouse, AxiosError>(
        ['element-in-warehouse', { id: id }],
        async () => getElementInWarehouseDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
