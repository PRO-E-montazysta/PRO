
import * as yup from 'yup'
import { FormInputProps } from '../types/form'

export const getInitValues = (formStructure: Array<FormInputProps>) => {
    if (formStructure.length == 0) return {}
    const resultObj: any = {}
    formStructure.forEach((f) => (resultObj[f.id] = f.initValue))
    return resultObj
}

export const getValidatinSchema = (formStructure: Array<FormInputProps>) => {
    const resultObj: any = {}
    formStructure.forEach((f) => {
        if (f.validation) resultObj[f.id] = f.validation
    })
    return yup.object(resultObj)
}
