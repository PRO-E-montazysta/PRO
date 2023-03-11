import { Warehouse } from '../types/model/Warehouse'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllWarehouses = () => {
    return makeServiceCall('/warehouses/all', 'GET', {});
}

export const getFilteredWarehouses = (payload: PayloadProps) => {
    return makeServiceCall('/warehouses/filter', 'GET', { ...payload })
}

export const getWarehouseDetails = (id?: string) => {
    return makeServiceCall(`/warehouses/${id}`, 'GET', {})
}

export const updateWarehouse = (data: Warehouse) => {
    return makeServiceCall(`/warehouses/${data.id}`, 'PUT', { body: data })
}

export const postWarehouse = (data: Warehouse) => {
    return makeServiceCall(`/warehouses`, 'POST', { body: data })
}

export const deleteWarehouse = (id: string | number) => {
    return makeServiceCall(`/warehouses/${id}`, 'DELETE', {})
}
