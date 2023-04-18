import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'

import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Employee } from '../../types/model/Employee'
import { deleteEmployee, getEmployeeDetails, postEmployee, updateEmployee } from '../../api/employee.api'
import useError from '../../hooks/useError'

export const useAddEmployee = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: postEmployee,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces',
                content: <Box>Nowy pracownik utworzony pomyślnie</Box>,
                callback: () => {
                    if (data.id) navigate(`/employees/${data.id}`)
                    else navigate(`/employees`)
                },
            })
        },
        onError: showError,
    })
}

export const useEditEmployee = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: updateEmployee,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Zmiany w pracowniku zostały zapisane</Box>,
                callback: () => onSuccess(data),
            })
        },
        onError: showError,
    })
}
export const useDeleteEmployee = (onSuccess: () => void) => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: deleteEmployee,
        onSuccess(data) {
            showDialog({
                btnOptions: [
                    {
                        text: 'OK',
                        value: 0,
                    },
                ],
                title: 'Sukces!',
                content: <Box>Pracownik został usunięty</Box>,
                callback: () => () => {
                    onSuccess()
                    navigate('/employees')
                },
            })
        },
        onError: showError,
    })
}

export const useEmployeeData = (id: string | undefined) => {
    return useQuery<Employee, AxiosError>(
        ['employee', { id: id }],
        async () => getEmployeeDetails(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}
