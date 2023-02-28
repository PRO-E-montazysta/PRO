import { makeServiceCall, PayloadProps } from "./utils.api";

export const getFilteredOrders = (payload: PayloadProps) => {
    return makeServiceCall('/orders/filter', 'GET', { ...payload });
}

export const getOrderDetails = (id?: string) => {
    return makeServiceCall(`/orders/${id}`, 'GET', {});
}