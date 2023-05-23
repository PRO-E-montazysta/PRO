import { CreateOrderStage, OrderStage, UpdateOrderStage, UpdateOrderStage2 } from '../types/model/OrderStage'
import { makeServiceCall } from './utils.api'

export const createOrderStage = (data: CreateOrderStage) => {
    return makeServiceCall(`/order-stages`, 'POST', { body: data })
}

export const updateOrderStage = (data: UpdateOrderStage) => {
    return makeServiceCall(`/order-stages/${data.orderStageId}`, 'PUT', { body: data })
}

export const getAllOrderStages = (orderId: string) => {
    return makeServiceCall(`/order-stages/filter?order_Id=${orderId}`, 'GET', {})
}

export const getOrderStageById = (id: string) => {
    return makeServiceCall(`/order-stages/${id}`, 'GET', {})
}
