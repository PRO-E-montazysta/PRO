import { InputDisplayMode } from '../components/form/FormStructure'
import { SelectMenuItemProps } from '../components/form/types'
import { Role } from './roleEnum'
import * as yup from 'yup'

export type FormInputProps = {
    label: string
    id: string
    initValue: any
    type: 'input' | 'select' | 'date' | 'password' | 'date-time' | 'number' | 'can-be-deleted'
    options?: Array<SelectMenuItemProps>
    validation?: yup.AnySchema
    formatFn?: (value: any) => string
    placeholder?: string

    //always readonly
    readonly?: boolean //deprecated

    dontIncludeInFormStructure?: boolean

    //if not provided then permitted
    editPermissionRoles?: Array<Role>
    viewPermissionRoles?: Array<Role>
    addNewPermissionRoles?: Array<Role>

    customPermission?: (inputValue: any) => InputDisplayMode | null

    //if not provided then validation on update is take from validation property
    validationOnUpdate?: yup.AnySchema | 'NO_VALIDATION_ON_UPDATE'
}

export type PageMode = 'new' | 'edit' | 'read'
