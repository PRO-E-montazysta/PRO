import { SelectMenuItemProps } from '../components/form/types'
import { Role } from './roleEnum'
import * as yup from 'yup'

export type FormInputProps = {
    label: string
    id: string
    initValue: any
    type: 'input' | 'select' | 'date' | 'password' | 'date-time' | 'number'
    options?: Array<SelectMenuItemProps>
    validation?: yup.AnySchema

    //always readonly
    readonly?: boolean //deprecated

    //to implement
    dontIncludeInFormStructure?: boolean

    //if not provided then permitted
    editPermission?: Array<Role>
    viewPermission?: Array<Role>
    addNewPermission?: Array<Role>

    //if not provided then validation on update is take from validation property
    validationOnUpdate?: yup.AnySchema | 'NO_VALIDATION_ON_UPDATE'
}
export type PageMode = 'new' | 'edit' | 'read'
