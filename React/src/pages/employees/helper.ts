import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { Employee, EmployeeStatus } from '../../types/model/Employee'

import { AppSize } from '../../hooks/useBreakpoints'
import { SelectMenuItemProps } from '../../components/form/types'
import * as yup from 'yup'
import { Role } from '../../types/roleEnum'
import { FormInputProps } from '../../types/form'
import { userRoleOptions } from '../../helpers/enum.helper'

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
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'password',
        label: 'Password',
        numeric: false,
    },
    {
        type: 'string',
        id: 'username',
        label: 'Username',
        numeric: false,
    },
    {
        type: 'string',
        id: 'status',
        label: 'Status',
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'roles',
        label: 'Role',
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'phone',
        label: 'Phone',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'pesel',
        label: 'Pesel',
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
    {
        id: 'phone',
        value: '',
        label: 'Phone',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'pesel',
        value: '',
        label: 'Pesel',
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
    phone: '',
    pesel: '',
}

export const validationSchema = yup.object({})

export const useFormStructure = (): Array<FormInputProps> => {
    return [
        {
            label: 'Imię',
            id: 'firstName',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(3, 'Imię musi zawierać co najmniej 3 znaki')
                .max(32, 'Imię może zawierać maksymalnie 32 znaki')
                .required('Wprowadź imię'),
        },
        {
            label: 'Nazwisko',
            id: 'lastName',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(2, 'Nazwisko musi zawierać co najmniej 2 znaki')
                .max(32, 'Nazwisko może zawierać maksymalnie 32 znaki')
                .required('Wprowadź nazwisko'),
        },
        {
            label: 'E-mail',
            id: 'email',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .max(72, 'Email może zawierać maksymalnie 72 znaki')
                .email('Wymagany jest poprawny email')
                .required('Wprowadź email'),
        },
        {
            label: 'Nazwa użytkownika',
            id: 'username',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(3, 'Nazwa musi zawierać co najmniej 3 znaki')
                .max(255, 'Nazwa użytkownika może zawierać maksymalnie 255 znaki')
                .required('Wprowadź nazwe'),
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role.ADMIN],
        },
        {
            label: 'Stanowisko',
            id: 'roles',
            initValue: '',
            type: 'select',
            validation: yup.string().required('Wybierz stanowisko'),
            options: userRoleOptions(),
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
        },
        {
            label: 'Telefon',
            id: 'phone',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .matches(new RegExp('^\\+48\\d{9}$'), 'Wymagany jest poprawny numer telefonu (format: +48 i 9 cyfr)')
                .required('Wprowadź numer telefonu'),
            placeholder: '+48123456789',
        },
        {
            label: 'Pesel',
            id: 'pesel',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .length(11, 'Wymagany jest poprawny pesel')
                .test(
                    'CheckDay',
                    'Wymagany jest poprawny pesel',
                    (val) => Number(val?.substring(4, 6)) >= 1 && Number(val?.substring(4, 6)) <= 31,
                )
                .test(
                    'CheckDay',
                    'Wymagany jest poprawny pesel',
                    (val) => Number(val?.substring(4, 6)) >= 1 && Number(val?.substring(4, 6)) <= 31,
                )
                .test('Checksum', 'Wymagany jest poprawny pesel', (val) => peselChecksum(String(val)))
                .required('Wprowadź pesel'),
            editPermissionRoles: [Role.ADMIN],
            viewPermissionRoles: [Role.ADMIN, Role.MANAGER],
            validationOnUpdate: yup.string().nullable(),
        },
        {
            label: 'Hasło',
            id: 'password',
            initValue: '',
            type: 'input',
            validation: yup.string().min(8, 'Hasło musi zawierać co najmniej 8 znaków').required('Wprowadź hasło'),
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role.NOBODY],
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            placeholder: 'POLE TYMCZASOWE',
        },
    ]
}

const peselChecksum = (pesel: string) => {
    let peselArray = pesel.split('').map(Number)
    let checksumHelper = 0

    checksumHelper += (peselArray[0] * 1) % 10
    checksumHelper += (peselArray[1] * 3) % 10
    checksumHelper += (peselArray[2] * 7) % 10
    checksumHelper += (peselArray[3] * 9) % 10
    checksumHelper += (peselArray[4] * 1) % 10
    checksumHelper += (peselArray[5] * 3) % 10
    checksumHelper += (peselArray[6] * 7) % 10
    checksumHelper += (peselArray[7] * 9) % 10
    checksumHelper += (peselArray[8] * 1) % 10
    checksumHelper += (peselArray[9] * 3) % 10

    checksumHelper = 10 - (checksumHelper % 10)

    return peselArray[10] == checksumHelper
}
