import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllForemans = () => {
    return makeServiceCall('/foremen/all', 'GET', {});
}

export const postForeman = (data: Employee) => {
    return makeServiceCall(`/foremen`, 'POST', { body: data })
}

export const updateForeman = (data: Employee) => {
    return makeServiceCall(`/foremen/${data.id}`, 'PUT', { body: data })
}

export const getForemanById = (id: string) => {
    return makeServiceCall(`/foremen/${id}`, 'GET', {})
}