import { makeServiceCall, PayloadProps } from "./utils.api";

export const getFilteredWarehouses = (payload: PayloadProps) => {
    return makeServiceCall('/warehouses/all', 'GET', { ...payload });
}

export const getWarehouseDetails = (id?: string) => {
    return makeServiceCall(`/warehouses/${id}`, 'GET', {});
}