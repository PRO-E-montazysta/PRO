import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllWarehouseman = () => {
    return makeServiceCall('/warehouseman/all', 'GET', {});
}

export const postWarehouseman = (data: Employee) => {
    return makeServiceCall(`/warehouseman`, 'POST', { body: data })
}