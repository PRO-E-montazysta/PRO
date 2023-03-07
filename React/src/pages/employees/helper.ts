import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { Employee } from '../../types/model/Employee'

export const headCells: Array<HeadCell<Employee>> = [
    {
        type: 'string',
        id: 'firstName',
        label: 'Imię',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'lastName',
        label: 'Nazwisko',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'email',
        label: 'Email',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'status',
        label: 'Status',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'roles',
        label: 'Role',
        disablePadding: false,
        numeric: false,
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'fristName',
        value: '',
        label: 'FirstName',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'lastName',
        value: '',
        label: 'LastName',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'email',
        value: '',
        label: 'Email',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'status',
        value: '',
        label: 'Status',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'roles',
        value: '',
        label: 'Role',
        inputType: 'text',
        typeValue: 'string',
    },
]
