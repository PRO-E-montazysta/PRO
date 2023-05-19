import { ElementInWarehouse } from '../types/model/ElementInWarehouse'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getElementInWarehouseCounts = (payload: PayloadProps, elementId: number) => {
    return makeServiceCall(`/elementInWarehouse/element/${elementId}`, 'GET', { ...payload })
}

export const getElementInWarehouseDetails = (id?: string) => {
    return makeServiceCall(`/elementInWarehouse/${id}`, 'GET', {})
}

export const updateElementInWarehouse = (data: ElementInWarehouse) => {
    return makeServiceCall(`/elementInWarehouse/${data.id}`, 'PUT', { body: data })
}
