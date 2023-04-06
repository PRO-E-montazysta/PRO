import { Employee } from "../types/model/Employee";
import { makeServiceCall } from "./utils.api";




export const getAllWarehouseman = () => {
    return makeServiceCall('/warehousemen/all', 'GET', {});
}

export const postWarehouseman = (data: Employee) => {
    return makeServiceCall(`/warehousemen`, 'POST', { body: data })
}