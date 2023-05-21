import { Tool } from '../types/model/Tool'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllTools = () => {
    return makeServiceCall('/tools/filter', 'GET', {})
}

export const getFilteredTools = (payload: PayloadProps) => {
    return makeServiceCall('/tools/filter', 'GET', { ...payload })
}

export const getToolDetails = (id?: string) => {
    return makeServiceCall(`/tools/${id}`, 'GET', {})
}

export const getToolsFromWarehouse = (payload: PayloadProps, warehouseId?: string) => {
    return makeServiceCall(`/tools/tools-from-warehouse/${warehouseId}`, 'GET', { ...payload })
}

export const updateTool = (data: Tool) => {
    return makeServiceCall(`/tools/${data.id}`, 'PUT', { body: data })
}

export const postTool = (data: Tool) => {
    return makeServiceCall(`/tools`, 'POST', { body: data })
}

export const deleteTool = (id: string | number) => {
    return makeServiceCall(`/tools/${id}`, 'DELETE', {})
}

export const getToolHistory = (id?: string) => {
    return makeServiceCall(`/tool-history/${id}`, 'GET', {})
}
