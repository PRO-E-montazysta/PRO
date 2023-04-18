import * as yup from 'yup'
import { FormInputProps, PageMode } from '../types/form'

export const getInitValues = (formStructure: Array<FormInputProps>) => {
    if (formStructure.length == 0) return {}
    const resultObj: any = {}
    formStructure.forEach((f) => (resultObj[f.id] = f.initValue))
    return resultObj
}

export const getValidatinSchema = (formStructure: Array<FormInputProps>, mode?: PageMode) => {
    const resultObj: any = {}

    if(!mode) return yup.object(resultObj)
    if (mode == 'read') return yup.object(resultObj)
    formStructure.forEach((f) => {
        if (mode == 'edit' && f.validationOnUpdate == 'NO_VALIDATION_ON_UPDATE') return
        else if (mode == 'edit' && f.validationOnUpdate) resultObj[f.id] = f.validationOnUpdate
        else if (mode == 'edit' && !f.validationOnUpdate && f.validation) resultObj[f.id] = f.validation
        else if (mode == 'new' && f.validation) resultObj[f.id] = f.validation
    })
    return yup.object(resultObj)
}
