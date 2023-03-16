import { Client } from '../types/model/Client'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllClients = () => {
    return makeServiceCall('/clients/all', 'GET', {})
}
export const getFilteredClients = (payload: PayloadProps) => {
    return makeServiceCall('/clients/all', 'GET', { ...payload })
}

export const getClientDetails = (id: string) => {
    return makeServiceCall(`/clients/${id}`, 'GET', {})
}

export const updateClient = (data: Client) => {
    return makeServiceCall(`/clients/${data.id}`, 'PUT', { body: data })
}

export const postClient = (data: Client) => {
    return makeServiceCall(`/clients`, 'POST', { body: data })
}

export const deleteClient = (id: string | number) => {
    return makeServiceCall(`/clients/${id}`, 'DELETE', {})
}
