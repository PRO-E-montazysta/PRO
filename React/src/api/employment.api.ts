import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllEmployeeEmployments = (id: string) => {
    return makeServiceCall(`/employments/for-employee/${id}`, 'GET', {})
}
