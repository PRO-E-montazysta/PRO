import { ElementInWarehouse } from '../types/model/ElementInWarehouse'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAttachments = (stageId: string) => {
    // return makeServiceCall(`/elementInWarehouse/element/${elementId}`, 'GET', { ...payload })
}

export const addAttachments = (formData: FormData) => {
    return makeServiceCall(`/attachments`, 'POST', { body: formData }, true)
}
