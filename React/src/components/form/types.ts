import { ChangeEvent, CSSProperties, HTMLInputTypeAttribute } from "react"
import { SelectMenuItemProps } from "../base/Multiselect"

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