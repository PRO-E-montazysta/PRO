import { FilterInputType } from '../../components/table/filter/TableFilter'
import { ToolType } from '../../types/model/ToolType'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'

import * as yup from 'yup'

import { AppSize } from '../../hooks/useBreakpoints'
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
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
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
    id: null,
    name: '',
    inServiceCount: '',
    criticalNumber: '',
    availableCount: '',
    attachments: [],
    orderStages: [],
    tools: [],
}

export const validationSchema = yup.object({
    name: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
    criticalNumber: yup.string().required('Wprowadź liczbę krytyczną'),
})
