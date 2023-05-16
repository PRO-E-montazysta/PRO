import { CreateOrderStage, OrderStage, UpdateOrderStage, UpdateOrderStage2 } from '../types/model/OrderStage'
import { makeServiceCall } from './utils.api'

export const createOrderStage = (data: CreateOrderStage) => {
    console.log('json', data)

    return makeServiceCall(`/order-stages`, 'POST', { body: data })
}

export const updateOrderStage = (data: UpdateOrderStage) => {
    // const id = data.orderStageId
    // const {orderStageId, ...restData} = data
    return makeServiceCall(`/order-stages/${data.orderStageId}`, 'PUT', { body: data });
}

export const getAllOrderStages = (orderId: string) => {
    return makeServiceCall(`/order-stages/filter?order_Id=${orderId}`, 'GET', {})
}

export const getOrderStageById = (id: string) => {
    return makeServiceCall(`/order-stages/${id}`, 'GET', {})
}
