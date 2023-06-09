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
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
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
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
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

export const useFormStructure = (): Array<FormInputProps> => {
    return [
        {
            label: 'Nazwa firmy',
            id: 'companyName',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(1, 'Nazwa musi zawierać co najmniej 1 znak')
                .max(255, 'Nazwa może zawierać maksymalnie 255 znaki')
                .required('Wprowadź nazwę'),
        },
        {
            label: 'Data utworzenia',
            id: 'createdAt',
            initValue: '',
            type: 'date-time',
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Status',
            id: 'status',
            initValue: 'ACTIVE',
            type: 'select',
            validation: yup.string().required('Wybierz status firmy!'),
            options: companyStatusOptions(),
        },
        {
            label: 'Uzasadnienie statusu',
            id: 'statusReason',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(3, 'Uzasadnienie musi zawierać co najmniej 3 znaki')
                .max(255, 'Uzasadnienie może zawierać maksymalnie 255 znaki'),
        },
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
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
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
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
        },
        {
            label: 'Email',
            id: 'email',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .max(72, 'Email może zawierać maksymalnie 72 znaki')
                .email('Wymagany jest poprawny email')
                .required('Wprowadź email'),
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
            placeholder: 'POLE TYMCZASOWE',
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
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
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
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
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
            validationOnUpdate: 'NO_VALIDATION_ON_UPDATE',
            dontIncludeInFormStructure: true,
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
