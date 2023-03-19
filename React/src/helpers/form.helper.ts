import { FormInputProps } from '../pages/orders/helper'

export const getEmptyForm = (formStructure: Array<FormInputProps>) => {
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
    console.log(resultObj)
    return resultObj
}
