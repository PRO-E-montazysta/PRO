import { makeServiceCall, PayloadProps } from "./utils.api";




export const getAllOrders = (payload: PayloadProps) => {
    console.log(payload)
    return makeServiceCall('/orders/all', 'GET', { ...payload });
}

export const getOrderDetails = (id?: string) => {
    return makeServiceCall(`/orders/${id}`, 'GET', {});
}