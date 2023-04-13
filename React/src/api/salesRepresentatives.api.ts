import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllSalesRepresentatives = () => {
    return makeServiceCall('/sales-representatives/all', 'GET', {});
}

export const postSalesRepresentative = (data: Employee) => {
    return makeServiceCall(`/sales-representatives`, 'POST', { body: data })
}

export const updateSalesRepresentative = (data: Employee) => {
    return makeServiceCall(`/sales-representatives/${data.id}`, 'PUT', { body: data })
}

export const getSalesRepresentativeById = (id: string) => {
    return makeServiceCall(`/sales-representatives/${id}`, 'GET', {})
}
