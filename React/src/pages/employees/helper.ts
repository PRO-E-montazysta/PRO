import { SelectMenuItemProps } from '../../components/base/Multiselect'
import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { Employee, EmployeeStatus } from '../../types/model/Employee'

import { AppSize } from '../../hooks/useBreakpoints'
export const headCells: Array<HeadCell<Employee>> = [
    {
        type: 'string',
        id: 'firstName',
        label: 'Imię',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'lastName',
        label: 'Nazwisko',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'email',
        label: 'Email',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'status',
        label: 'Status',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'roles',
        label: 'Role',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
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
        value: ['AKTYWNY', 'NIEAKTYWNY'],
        label: 'Status',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: Object.entries(EmployeeStatus).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1],
            }
        }),
    },
    {
        id: 'roles',
        value: '',
        label: 'Role',
        inputType: 'text',
        typeValue: 'string',
    },
]
