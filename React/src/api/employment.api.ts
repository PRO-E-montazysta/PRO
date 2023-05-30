import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllEmployeeEmployments = (id: string) => {
    return makeServiceCall(`/for-employee/${id}`, 'GET', {})
}
