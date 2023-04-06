import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllWarehouseManager = () => {
    return makeServiceCall('/warehouse-managers/all', 'GET', {});
}

export const postWarehouseManager = (data: Employee) => {
    return makeServiceCall(`/warehouse-managers`, 'POST', { body: data })
}