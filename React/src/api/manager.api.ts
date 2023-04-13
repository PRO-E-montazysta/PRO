import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllManagers = () => {
    return makeServiceCall('/managers/all', 'GET', {});
}

export const postManager = (data: Employee) => {
    return makeServiceCall(`/managers`, 'POST', { body: data })
}

export const updateManager = (data: Employee) => {
    return makeServiceCall(`/managers/${data.id}`, 'PUT', { body: data })
}

export const getManagerById = (id: string) => {
    return makeServiceCall(`/managers/${id}`, 'GET', {})
}