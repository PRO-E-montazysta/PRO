import { SelectMenuItemProps } from "../components/form/types"

export type FormInputProps = {
    label: string
    id: string
    initValue: any
    type: 'input' | 'select' | 'date'
    options?: Array<SelectMenuItemProps>
    validation?: any
    readonly?: boolean
}