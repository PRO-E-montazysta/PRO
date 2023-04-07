import { makeServiceCall, PayloadProps } from './utils.api'

export const getFilteredEvents = (payload: PayloadProps) => {
    return makeServiceCall('/events/filter', 'GET', { ...payload })
}

export const getToolEventDetails = (id?: string) => {
    return makeServiceCall(`/toolEvent/${id}`, 'GET', {})
}

export const updateToolEvent = (data: Element) => {
    return makeServiceCall(`/toolEvent/${data.id}`, 'PUT', { body: data })
}

export const postToolEvent = (data: Element) => {
    return makeServiceCall(`/toolEvent`, 'POST', { body: data })
}

export const deleteToolEvent = (id: string | number) => {
    return makeServiceCall(`/toolEvent/${id}`, 'DELETE', {})
}

export const getElementEventDetails = (id?: string) => {
    return makeServiceCall(`/elementEvent/${id}`, 'GET', {})
}

export const updateElementEvent = (data: Element) => {
    return makeServiceCall(`/elementEvent/${data.id}`, 'PUT', { body: data })
}

export const postElementEvent = (data: Element) => {
    return makeServiceCall(`/elementEvent`, 'POST', { body: data })
}

export const deleteElementEvent = (id: string | number) => {
    return makeServiceCall(`/elementEvent/${id}`, 'DELETE', {})
}
