import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'

import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Company } from '../../types/model/Company'
import { deleteCompany, getCompanyDetails, postCompany, updateCompany } from '../../api/company.api'
import useError from '../../hooks/useError'

export const useAddCompany = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postCompany,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowa firma utworzona pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/companies/${data.id}`)
                    else navigate(`/companies`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditCompany = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateCompany,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w firmie zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteCompany = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteCompany,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Firma została usunięta</Box>,
            })
            navigate('/companies')
        },
        onError: showError,
    })
}

export const useCompanyData = (id: string | undefined) => {
    return useQuery<Company, AxiosError>(
        ['company', { id: id }],
        async () => getCompanyDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
