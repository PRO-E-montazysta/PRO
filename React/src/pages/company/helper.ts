import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Company } from '../../types/model/Company'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { formatDate } from '../../helpers/format.helper'
import { companyStatusName, companyStatusOptions } from '../../helpers/enum.helper'

import * as yup from 'yup'

import { AppSize } from '../../hooks/useBreakpoints'
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
    id: null,
    companyName: '',
    createdAt: '',
    status: '',
    statusReason: '',
    warehouses: [],
    orders: [],
    clients: [],
    employments: [],
}

export const addCompanyForm = {
        "firstName": "string",  |not null |3-32 znaki
          "lastName": "string",  |not null |2-32 znaki
          "email": "string",  |not null |sprawdza format emaila
          "password": "string",  |not null  |5+ znaki |sprawdza format telefonu
          "username": "string",  |not null  |3+ znaki
          "phone": "string",
          "pesel": "string"  |not null |sprawdza format peselu
}

export const validationSchema = yup.object({
    companyName: yup.string().min(2, 'Nazwa musi zawierać co najmniej 2 znaki').required('Wprowadź nazwę'),
    status: yup.string().required('Wybierz status'),
})
