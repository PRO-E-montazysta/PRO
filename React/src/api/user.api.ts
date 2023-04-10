import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllUsers = () => {
    return makeServiceCall('/users/filter', 'GET', {})
}

export const getFilteredUsers = (payload: PayloadProps) => {
    return makeServiceCall('/users/filter', 'GET', { ...payload })
}

export const getUserById = (id: string) => {
    return makeServiceCall(`/users/${id}`, 'GET', {})
}
