import { CreateOrderStage, OrderStage } from '../types/model/OrderStage'
import { makeServiceCall } from './utils.api'

export const createOrderStage = (data: CreateOrderStage) => {
    return makeServiceCall(`/order-stages`, 'POST', { body: data })
}

export const getAllOrderStages = (orderId: string) => {
    return makeServiceCall(`/order-stages/filter?order_Id=${orderId}`, 'GET', {})
}

export const getOrderStageById = (id: string) => {
    return makeServiceCall(`/order-stages/${id}`, 'GET', {})
}
