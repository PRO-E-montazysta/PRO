import { FilterInputType } from '../../components/table/filter/TableFilter'
import { ToolType } from '../../types/model/ToolType'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import * as yup from 'yup'
import { AppSize } from '../../hooks/useBreakpoints'
import { FormInputProps } from '../../types/form'
import { Role } from '../../types/roleEnum'

export const headCells: Array<HeadCell<ToolType>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'inServiceCount',
        label: 'Liczba narzędzi',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: true,
    },
    {
        type: 'string',
        id: 'availableCount',
        label: 'Liczba dostępnych',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: true,
    },
    {
        type: 'string',
        id: 'criticalNumber',
        label: 'Liczba krytyczna',
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: true,
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'name',
        value: '',
        label: 'Nazwa',
        inputType: 'text',
        typeValue: 'string',
    },
]

export const emptyForm = {
    attachments: [],
    orderStages: [],
    tools: [],
}

export const useFormStructure = (): Array<FormInputProps> => {
    return [
        {
            label: 'Nazwa typu narzędzia',
            id: 'name',
            initValue: '',
            type: 'input',
            validation: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
        },
        {
            label: 'Liczba krytyczna',
            id: 'criticalNumber',
            initValue: '',
            type: 'input',
            validation: yup.string().required('Wprowadź liczbę krytyczną'),
        },
        {
            label: 'Liczba narzędzi',
            id: 'inServiceCount',
            initValue: '',
            type: 'input',
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Liczba dostępnych',
            id: 'availableCount',
            initValue: '',
            type: 'input',
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
    ]
}
