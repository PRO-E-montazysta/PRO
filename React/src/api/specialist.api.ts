import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllSpecialists = () => {
    return makeServiceCall('/specialist/all', 'GET', {});
}

export const postSpecialist = (data: Employee) => {
    return makeServiceCall(`/specialist`, 'POST', { body: data })
}