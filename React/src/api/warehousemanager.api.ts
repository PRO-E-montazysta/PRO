import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllWarehouseManager = () => {
    return makeServiceCall('/warehousemanager/all', 'GET', {});
}

export const postWarehouseManager = (data: Employee) => {
    return makeServiceCall(`/warehousemanager`, 'POST', { body: data })
}