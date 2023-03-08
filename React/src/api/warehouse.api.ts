import { makeServiceCall, PayloadProps } from "./utils.api";

export const getAllWarehouses = () => {
    return makeServiceCall('/warehouses/all', 'GET', {});
}

export const getFilteredWarehouses = (payload: PayloadProps) => {
    return makeServiceCall('/warehouses/filter', 'GET', { ...payload });
}

export const getWarehouseDetails = (id?: string) => {
    return makeServiceCall(`/warehouses/${id}`, 'GET', {});
}