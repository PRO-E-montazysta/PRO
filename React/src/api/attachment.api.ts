import { Attachment } from '../types/model/Attachment'
import { ElementInWarehouse } from '../types/model/ElementInWarehouse'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAttachment = (id: string): Promise<Attachment> => {
    return makeServiceCall(`/attachments/${id}`, 'GET', {})
}

export const addAttachments = (formData: FormData) => {
    return makeServiceCall(`/attachments`, 'POST', { body: formData }, true)
}

export const deleteAttachment = (id: string) => {
    return makeServiceCall(`/attachments/${id}`, 'DELETE', {})
}
