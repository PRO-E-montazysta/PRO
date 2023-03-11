import { FilterInputType } from '../../components/table/filter/TableFilter'
import { ToolType } from '../../types/model/ToolType'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'

import * as yup from 'yup'

export const headCells: Array<HeadCell<ToolType>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'inServiceCount',
        label: 'Liczba narzędzi',
        disablePadding: false,
        numeric: true,
    },
    {
        type: 'string',
        id: 'availableCount',
        label: 'Liczba dostępnych',
        disablePadding: false,
        numeric: true,
    },
    {
        type: 'string',
        id: 'criticalNumber',
        label: 'Liczba krytyczna',
        disablePadding: false,
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
    inServiceCount: null,
    criticalNumber: null,
    availableCount: null,
    attachments: [],
    orderStages: [],
    tools: [],
}

export const validationSchema = yup.object({
    name: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
    criticalNumber: yup.number().typeError('Wprowadź liczbę krytyczną'),
})
