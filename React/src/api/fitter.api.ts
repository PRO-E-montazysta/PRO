import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllFitter = () => {
    return makeServiceCall('/fitters/all', 'GET', {});
}

export const postFitter = (data: Employee) => {
    return makeServiceCall(`/fitters`, 'POST', { body: data })
}

export const updateFitter = (data: Employee) => {
    return makeServiceCall(`/fitters/${data.id}`, 'PUT', { body: data })
}