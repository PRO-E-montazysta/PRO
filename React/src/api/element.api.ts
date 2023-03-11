import { makeServiceCall, PayloadProps } from './utils.api'

export const getFilteredElements = (payload: PayloadProps) => {
    return makeServiceCall('/elements/filter', 'GET', { ...payload })
}

export const getElementDetails = (id?: string) => {
    return makeServiceCall(`/elements/${id}`, 'GET', {})
}
