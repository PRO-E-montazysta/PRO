import { Client } from '../types/model/Client'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllClients = () => {
    return makeServiceCall('/clients/filter', 'GET', {})
}

export const getAllClientsDeleted = () => {
    return makeServiceCall('/clients/deleted', 'GET', {})
}
export const getFilteredClients = (payload: PayloadProps) => {
    return makeServiceCall('/clients/filter', 'GET', { ...payload })
}

export const getClientDetails = (id: string): Promise<Client> => {
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
