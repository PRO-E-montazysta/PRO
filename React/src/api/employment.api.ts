import { makeServiceCall } from './utils.api'

export const getAllEmployeeEmployments = (employeeId: string) => {
    return makeServiceCall(`/employments/for-employee/${employeeId}`, 'GET', {})
}
export const hireEmployee = (employeeId: string) => {
    return makeServiceCall(`/employments/${employeeId}`, 'POST', {})
}

export const dismissEmployee = (employeeId: string) => {
    return makeServiceCall(`/employments/${employeeId}`, 'PUT', {})
}
