import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Element, TypeOfUnit } from '../../types/model/Element'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'

export const headCells: Array<HeadCell<Element>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'code',
        label: 'Kod',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'typeOfUnit',
        label: 'Jednostka',
        disablePadding: false,
        numeric: false,
        formatFn: (status: string) => Object.values(TypeOfUnit)[Object.keys(TypeOfUnit).indexOf(status)],
    },
    {
        type: 'string',
        id: 'quantityInUnit',
        label: 'Ilość w jednostce',
        disablePadding: false,
        numeric: false,
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
    {
        id: 'code',
        value: '',
        label: 'Kod',
        inputType: 'text',
        typeValue: 'string',
    },
]
