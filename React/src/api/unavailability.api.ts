import { Unavailability } from '../types/model/Unavailability'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllUnavailabilities = () => {
    return makeServiceCall('/unavailabilities/all', 'GET', {})
}

export const getFilteredUnavailabilities = (payload: PayloadProps) => {
    return makeServiceCall('/unavailabilities/filter', 'GET', { ...payload })
}

export const getUnavailabilityDetails = (id: string) => {
    return makeServiceCall(`/unavailabilities/${id}`, 'GET', {})
}

export const updateUnavailability = (data: Unavailability) => {
    return makeServiceCall(`/unavailabilities/${data.id}`, 'PUT', { body: data })
}

export const postUnavailability = (data: Unavailability) => {
    return makeServiceCall(`/unavailabilities`, 'POST', { body: data })
}

export const deleteUnavailability = (id: string | number) => {
    return makeServiceCall(`/unavailabilities/${id}`, 'DELETE', {})
}
