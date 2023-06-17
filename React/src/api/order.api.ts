import { Order } from '../types/model/Order'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getFilteredOrders = (payload: PayloadProps) => {
    return makeServiceCall('/orders/filter', 'GET', { ...payload })
}

export const getOrderDetails = (id: string): Promise<Order> => {
    return makeServiceCall(`/orders/${id}`, 'GET', {})
}

export const updateOrder = (data: Order) => {
    return makeServiceCall(`/orders/${data.id}`, 'PUT', { body: data })
}

export const postOrder = (data: Order) => {
    return makeServiceCall(`/orders`, 'POST', { body: data })
}

export const deleteOrder = (id: string | number) => {
    return makeServiceCall(`/orders/${id}`, 'DELETE', {})
}

export const orderNextStatus = (id: string | number) => {
    return makeServiceCall(`/orders/nextStatus/${id}`, 'PUT', {})
}

export const orderPreviousStatus = (id: string | number) => {
    return makeServiceCall(`/orders/previousStatus/${id}`, 'PUT', {})
}
