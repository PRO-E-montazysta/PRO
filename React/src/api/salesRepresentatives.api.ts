import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllSalesRepresentatives = () => {
    return makeServiceCall('/sales-representatives/all', 'GET', {});
}

export const postSalesRepresentative = (data: Employee) => {
    return makeServiceCall(`/sales-representatives`, 'POST', { body: data })
}