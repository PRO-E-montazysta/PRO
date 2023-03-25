
import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { Employee, EmployeeStatus } from '../../types/model/Employee'

import { AppSize } from '../../hooks/useBreakpoints'
import { SelectMenuItemProps } from '../../components/form/types'
import * as yup from 'yup'

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
        id: 'password',
        label: 'Password',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'username',
        label: 'Username',
        disablePadding: false,
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

export const emptyForm = {
    id: null,
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    username: '',
    status: '',
    roles: [],
}

export const validationSchema = yup.object({
    firstName: yup.string().min(2, 'Imię musi zawierać co najmniej 2 znaki').required('Wprowadź imię'),
    lastName: yup.string().min(2, 'Nazwisko musi zawierać co najmniej 2 znaki').required('Wprowadź nazwisko'),
    password: yup.string().min(8, 'Hasło musi zawierać co najmniej 8 znaków').required('Wprowadź hasło'),
    username: yup.string().min(3, 'Nazwa musi zawierać co najmniej 3 znaki').required('Wprowadź nazwe'),
    email: yup.string().email('Wymagany jest poprawny email').required('Wprowadź email')
})
