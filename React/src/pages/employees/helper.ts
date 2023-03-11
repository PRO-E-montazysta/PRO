import { SelectMenuItemProps } from '../../components/base/Multiselect'
import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { Employee, EmployeeStatus } from '../../types/model/Employee'


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
        value: ['AKTYWNY','NIEAKTYWNY'],
        label: 'Status',
        inputType: 'text',
        typeValue: 'Array',
        options: Object.entries(EmployeeStatus).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1]
            }
        })
    },
    {
        id: 'roles',
        value: '',
        label: 'Role',
        inputType: 'text',
        typeValue: 'string',
    },
]
