import { CreateOrderStage, OrderStage, UpdateOrderStage, UpdateOrderStage2 } from '../types/model/OrderStage'
import { makeServiceCall } from './utils.api'

export const createOrderStage = (data: CreateOrderStage) => {
    return makeServiceCall(`/order-stages`, 'POST', { body: data })
}

export const updateOrderStage = (data: UpdateOrderStage) => {
    console.log(data)
    return makeServiceCall(`/order-stages/${data.orderStageId}`, 'PUT', { body: data })
}

export const getAllOrderStagesForOrder = (orderId: string) => {
    return makeServiceCall(`/order-stages/filter?order_Id=${orderId}`, 'GET', {})
}

export const getAllOrderSages = () => {
    return makeServiceCall('/order-stages/filter', 'GET', {})
}

export const getOrderStageById = (id: string): Promise<OrderStage> => {
    return makeServiceCall(`/order-stages/${id}`, 'GET', {})
}

export const orderStageNextStatus = (id: string | number) => {
    return makeServiceCall(`/order-stages/nextStatus/${id}`, 'PUT', {})
}

export const orderStagePreviousStatus = (id: string | number) => {
    return makeServiceCall(`/order-stages/previousStatus/${id}`, 'PUT', {})
}

export const updateOrderStageFitters = (data: { fitters: number[]; stageId: number }) => {
    return makeServiceCall(`/order-stages/${data.stageId}`, 'PUT', {
        body: {
            fitters: data.fitters,
        },
    })
}
