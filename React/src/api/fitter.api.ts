import { Employee } from '../types/model/Employee'
import { PayloadProps, makeServiceCall } from './utils.api'

export const getAllFitter = () => {
    return makeServiceCall('/fitters/all', 'GET', {})
}

export const postFitter = (data: Employee) => {
    return makeServiceCall(`/fitters`, 'POST', { body: data })
}

export const updateFitter = (data: Employee) => {
    return makeServiceCall(`/fitters/${data.id}`, 'PUT', { body: data })
}

export const getFitterById = (id: string) => {
    return makeServiceCall(`/fitters/${id}`, 'GET', {})
}

export const getFittersAvailable = (data: { availableFrom: string; availableTo: string }) => {
    return makeServiceCall(`/fitters/available`, 'GET', { body: data })
}
