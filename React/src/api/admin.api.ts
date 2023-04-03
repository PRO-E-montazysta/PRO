import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllAdmins = () => {
    return makeServiceCall('/admin/all', 'GET', {});
}

export const postAdmin = (data: Employee) => {
    return makeServiceCall(`/admin`, 'POST', { body: data })
}