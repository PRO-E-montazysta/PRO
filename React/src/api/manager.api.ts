import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllManagers = () => {
    return makeServiceCall('/manager/all', 'GET', {});
}

export const postManager = (data: Employee) => {
    return makeServiceCall(`/manager`, 'POST', { body: data })
} 