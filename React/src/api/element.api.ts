import { Element } from '../types/model/Element'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllElements = () => {
    return makeServiceCall('/elements/all', 'GET', {})
}

export const getFilteredElements = (payload: PayloadProps) => {
    return makeServiceCall('/elements/filter', 'GET', { ...payload })
}

export const getElementDetails = (id?: string) => {
    return makeServiceCall(`/elements/${id}`, 'GET', {})
}

export const updateElement = (data: Element) => {
    return makeServiceCall(`/elements/${data.id}`, 'PUT', { body: data })
}

export const postElement = (data: Element) => {
    return makeServiceCall(`/elements`, 'POST', { body: data })
}

export const deleteElement = (id: string | number) => {
    return makeServiceCall(`/elements/${id}`, 'DELETE', {})
}
