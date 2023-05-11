import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { Client } from '../../types/model/Client'
import * as yup from 'yup'

import { AppSize } from '../../hooks/useBreakpoints'
import { FormInputProps } from '../../types/form'
export const headCells: Array<HeadCell<Client>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'contactDetails',
        label: 'Dane kontaktowe',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'orders',
        label: 'Liczba zleceń',
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (orders) => orders.length,
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

export const useFormStructure = (): Array<FormInputProps> => {
    return [
        {
            label: 'Nazwa klienta',
            id: 'name',
            initValue: '',
            type: 'input',
            validation: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
        },
        {
            label: 'Dane kontaktowe',
            id: 'contactDetails',
            initValue: '',
            type: 'input',
            validation: yup.string().required('Wprowadź dane kontaktowe'),
        },
    ]
}
