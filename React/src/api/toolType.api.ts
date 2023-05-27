import { ToolType } from '../types/model/ToolType'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllToolTypes = () => {
    return makeServiceCall('/tooltype/filter', 'GET', {})
}

export const getFilteredToolTypes = (payload: PayloadProps) => {
    return makeServiceCall('/tooltype/filter', 'GET', { ...payload })
}

export const getToolTypeDetails = (id?: string) => {
    return makeServiceCall(`/tooltype/${id}`, 'GET', {})
}

export const updateToolType = (data: ToolType) => {
    return makeServiceCall(`/tooltype/${data.id}`, 'PUT', { body: data })
}

export const postToolType = (data: ToolType) => {
    return makeServiceCall(`/tooltype`, 'POST', { body: data })
}

export const deleteToolType = (id: string | number) => {
    return makeServiceCall(`/tooltype/${id}`, 'DELETE', {})
}


export const getPlannedToolTypesById = (id: string | number) => {
    return makeServiceCall(`/tools-planned/${id}`, 'GET', {})
}