import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllForemans = () => {
    return makeServiceCall('/foremen/all', 'GET', {});
}

export const postForeman = (data: Employee) => {
    return makeServiceCall(`/foremen`, 'POST', { body: data })
}