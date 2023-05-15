import { Box } from '@mui/material'
import { AxiosError } from 'axios'
import { useContext } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate } from 'react-router-dom'

import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Employee } from '../../types/model/Employee'
import { deleteEmployee, getEmployeeDetails, postEmployee, updateEmployee } from '../../api/employee.api'
import useError from '../../hooks/useError'
import { postAdmin, updateAdmin } from '../../api/admin.api'
import { postFitter, updateFitter } from '../../api/fitter.api'
import { postForeman, updateForeman } from '../../api/foreman.api'
import { postManager, updateManager } from '../../api/manager.api'
import { postSalesRepresentative, updateSalesRepresentative } from '../../api/salesRepresentatives.api'
import { postSpecialist, updateSpecialist } from '../../api/specialist.api'
import { postWarehouseman, updateWarehouseman } from '../../api/warehouseman.api'
import { postWarehouseManager, updateWarehouseManager } from '../../api/warehousemanager.api'
import { Role } from '../../types/roleEnum'

const sendRoleBasedPost = (data: Employee) => {
    const role = data.roles && data.roles
    data.roles = null

    switch (role) {
        case Role.ADMIN:
            return postAdmin(data)
        case Role.FITTER:
            return postFitter(data)
        case Role.FOREMAN:
            return postForeman(data)
        case Role.MANAGER:
            return postManager(data)
        case Role.SALES_REPRESENTATIVE:
            return postSalesRepresentative(data)
        case Role.SPECIALIST:
            return postSpecialist(data)
        case Role.WAREHOUSE_MAN:
            return postWarehouseman(data)
        case Role.WAREHOUSE_MANAGER:
            return postWarehouseManager(data)
        default:
            return postEmployee(data)
    }
}

export const useAddEmployee = () => {
    const navigate = useNavigate()
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: sendRoleBasedPost,
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

const sendRoleBasedUpdate = (data: Employee) => {
    const role = data.roles && data.roles[0]

    switch (role) {
        case Role.ADMIN:
            return updateAdmin(data)
        case Role.FITTER:
            return updateFitter(data)
        case Role.FOREMAN:
            return updateForeman(data)
        case Role.MANAGER:
            return updateManager(data)
        case Role.SALES_REPRESENTATIVE:
            return updateSalesRepresentative(data)
        case Role.SPECIALIST:
            return updateSpecialist(data)
        case Role.WAREHOUSE_MAN:
            return updateWarehouseman(data)
        case Role.WAREHOUSE_MANAGER:
            return updateWarehouseManager(data)
        default:
            return updateEmployee(data) // TODO: NS: Domyślnie powinno rzucać błedem o nieznanej roli
    }
}

export const useEditEmployee = (onSuccess: (data: any) => void) => {
    const { showDialog } = useContext(DialogGlobalContext)
    const showError = useError()
    return useMutation({
        mutationFn: sendRoleBasedUpdate,
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
