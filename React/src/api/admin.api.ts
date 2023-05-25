import { Employee } from '../types/model/Employee'
import { makeServiceCall } from './utils.api'

export const getAllAdmins = () => {
    return makeServiceCall('/company-admin/all', 'GET', {})
}

export const postAdmin = (data: Employee) => {
    return makeServiceCall(`/company-admin`, 'POST', { body: data })
}

export const updateAdmin = (data: Employee) => {
    return makeServiceCall(`/company-admin/${data.id}`, 'PUT', { body: data })
}

export const getAdminById = (id: string) => {
    return makeServiceCall(`/company-admin/${id}`, 'GET', {})
}
