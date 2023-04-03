import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllFitter = () => {
    return makeServiceCall('/fitter/all', 'GET', {});
}

export const postFitter = (data: Employee) => {
    return makeServiceCall(`/fitter`, 'POST', { body: data })
}