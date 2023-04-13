import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllSpecialists = () => {
    return makeServiceCall('/specialists/all', 'GET', {});
}

export const postSpecialist = (data: Employee) => {
    return makeServiceCall(`/specialists`, 'POST', { body: data })
}

export const updateSpecialist = (data: Employee) => {
    return makeServiceCall(`/specialists/${data.id}`, 'PUT', { body: data })
}