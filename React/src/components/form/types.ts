import { ChangeEvent, CSSProperties, HTMLInputTypeAttribute } from 'react'

export type FormInputParams = {
    readonly: boolean
    label?: string
    id: string
    formik: any
    firstChild?: boolean
    options?: Array<SelectMenuItemProps>
    style?: CSSProperties
    type?: HTMLInputTypeAttribute
}

export type FormLabelProps = {
    label: string
    formik: any
    id: string
}

export type SelectMenuItemProps = {
    key: number | string
    value: string
}
