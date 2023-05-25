import { Location } from '../types/model/Location'
import { makeServiceCall } from './utils.api'

export const getAllLocations = () => {
    return makeServiceCall('/locations/all', 'GET', {})
}

export const getLocationById = (id: string | number) => {
    return makeServiceCall(`/locations/${id}`, 'GET', {})
}

export const updateLocation = (data: Location) => {
    return makeServiceCall(`/locations/${data.id}`, 'PUT', { body: data })
}

export const postLocation = (data: Location) => {
    return makeServiceCall(`/locations`, 'POST', { body: data })
}

export const deleteLocation = (id: string | number) => {
    return makeServiceCall(`/locations/${id}`, 'DELETE', {})
}
