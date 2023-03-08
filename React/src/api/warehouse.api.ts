import { makeServiceCall, PayloadProps } from "./utils.api";

export const getFilteredWarehouses = (payload: PayloadProps) => {
    return makeServiceCall('/warehouses/filter', 'GET', { ...payload });
}

export const getWarehouseDetails = (id?: string) => {
    return makeServiceCall(`/warehouses/${id}`, 'GET', {});
}