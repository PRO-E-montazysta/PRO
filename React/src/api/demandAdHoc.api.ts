import { DemandAdHoc } from '../types/model/DemandAdHoc'
import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllDemandsAdHoc = () => {
    return makeServiceCall('/demands-adhoc/filter', 'GET', {})
}

export const getFilteredDemandsAdHoc = (payload: PayloadProps) => {
    return makeServiceCall('/demands-adhoc/filter', 'GET', { ...payload })
}

export const getDemandAdHocDetails = (id?: string) => {
    return makeServiceCall(`/demands-adhoc/${id}`, 'GET', {})
}

export const updateDemandAdHoc = (data: DemandAdHoc) => {
    return makeServiceCall(`/demands-adhoc/${data.id}`, 'PUT', { body: data })
}

export const postDemandAdHoc = (data: DemandAdHoc) => {
    return makeServiceCall(`/demands-adhoc`, 'POST', { body: data })
}

export const deleteDemandAdHoc = (id: string | number) => {
    return makeServiceCall(`/demands-adhoc/${id}`, 'DELETE', {})
}
