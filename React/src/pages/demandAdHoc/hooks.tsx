import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { DemandAdHoc } from '../../types/model/DemandAdHoc'
import { deleteDemandAdHoc, getDemandAdHocDetails, postDemandAdHoc, updateDemandAdHoc } from '../../api/demandAdHoc.api'
import useError from '../../hooks/useError'

export const useAddDemandAdHoc = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postDemandAdHoc,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowe zapotrzebowanie utworzone pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/demands-adhoc/${data.id}`)
                    else navigate(`/demands-adhoc`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditDemandAdHoc = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateDemandAdHoc,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w zapotrzebowaniu zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteDemandAdHoc = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteDemandAdHoc,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zapotrzebowanie zostało usunięte</Box>,
                callback: () => () => {
                    onSuccess()
                    navigate('/demands-adhoc')
                },
            })
        },
        onError: showError,
    })
}

export const useDemandAdHocData = (id: string | undefined) => {
    return useQuery<DemandAdHoc, AxiosError>(
        ['demandadhoc', { id: id }],
        async () => getDemandAdHocDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
