import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Company } from '../../types/model/Company'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { formatDate } from '../../helpers/format.helper'
import { companyStatusName, companyStatusOptions } from '../../helpers/enum.helper'

import * as yup from 'yup'

import { AppSize } from '../../hooks/useBreakpoints'
import { FormInputProps } from '../../types/form'
import { Role } from '../../types/roleEnum'
export const headCells: Array<HeadCell<Company>> = [
    {
        type: 'string',
        id: 'companyName',
        label: 'Nazwa firmy',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'createdAt',
        label: 'Data utworzenia',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (date: string) => (date ? formatDate(date) : ''),
    },
    {
        type: 'string',
        id: 'status',
        label: 'Status',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (status: string) => companyStatusName(status),
    },
    {
        type: 'string',
        id: 'statusReason',
        label: 'Uzasadnienie statusu',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'companyName',
        value: '',
        label: 'Nazwa firmy',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'status',
        value: '',
        label: 'Status',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: companyStatusOptions(),
    },
]

export const emptyForm = {
    id: '',
    companyName: '',
    status: 'ACTIVE',
    statusReason: '',
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    username: '',
    phone: '',
    pesel: '',
}

export const addNewCompanyForm = {
    companyName: '',
    status: 'ACTIVE',
    statusReason: '',
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    username: '',
    phone: '',
    pesel: '',
}

export const validationSchemaPost = yup.object({
    companyName: yup.string().min(2, 'Nazwa musi zawierać co najmniej 2 znaki').required('Wprowadź nazwę'),
    firstName: yup.string().min(3, 'Imie musi zawierać co najmniej 3 znaki').required('Wprowadź imię'),
    lastName: yup.string().min(2, 'Nazwisko musi zawierać co najmniej 2 znaki').required('Wprowadź nazwisko'),
    email: yup.string().email().required('Wprowadź email'),
    password: yup.string().min(2, 'Hasło musi zawierać co najmniej 5 znaków').required('Wprowadź hasło'),
    username: yup
        .string()
        .min(2, 'Nazwa użytkownika musi zawierać co najmniej 3 znaki')
        .required('Wprowadź nazwę użytkownika'),
    phone: yup.string().required('Wprowadź numer telefonu'),
    pesel: yup.string().required('Wprowadź poprawny pesel'),
})

export const validationSchemaUpdate = yup.object({
    companyName: yup.string().min(2, 'Nazwa musi zawierać co najmniej 2 znaki').required('Wprowadź nazwę'),
})

export const useFormStructure = (): Array<FormInputProps> => {
    return [
        {
            label: 'Nazwa firmy',
            id: 'companyName',
            initValue: '',
            type: 'input',
            validation: yup.string().min(2, 'Nazwa musi zawierać co najmniej 2 znaki').required('Wprowadź nazwę'),
        },
        {
            label: 'Data utworzenia',
            id: 'createdAt',
            initValue: '',
            type: 'date',
            addNewPermission: [Role.NOBODY],
            editPermission: [Role.NOBODY],
            viewPermission: [Role['*']],
        },
        {
            label: 'Status',
            id: 'status',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Uzasadnienie statusu',
            id: 'statusReason',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Imię',
            id: 'firstName',
            initValue: '',
            type: 'input',
            validation: yup.string().min(3, 'Imie musi zawierać co najmniej 3 znaki').required('Wprowadź imię'),
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
        },
        {
            label: 'Nazwisko',
            id: 'lastName',
            initValue: '',
            type: 'input',
            validation: yup.string().min(2, 'Nazwisko musi zawierać co najmniej 2 znaki').required('Wprowadź nazwisko'),
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
        },
        {
            label: 'Email',
            id: 'email',
            initValue: '',
            type: 'input',
            validation: yup.string().email().required('Wprowadź email'),
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
        },
        {
            label: 'Hasło',
            id: 'password',
            initValue: '',
            type: 'password',
            validation: yup.string().min(5, 'Hasło musi zawierać co najmniej 5 znaków').required('Wprowadź hasło'),
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
        },
        {
            label: 'Nazwa użytkownika',
            id: 'username',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(2, 'Nazwa użytkownika musi zawierać co najmniej 3 znaki')
                .required('Wprowadź nazwę użytkownika'),
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
        },
        {
            label: 'Telefon',
            id: 'phone',
            initValue: '',
            type: 'input',
            validation: yup.string().required('Wprowadź numer telefonu'),
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
        },
        {
            label: 'Pesel',
            id: 'pesel',
            initValue: '',
            type: 'input',
            validation: yup.string().required('Wprowadź poprawny pesel'),
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
        },
    ]
}
