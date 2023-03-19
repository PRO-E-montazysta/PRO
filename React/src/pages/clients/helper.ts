import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { Client } from '../../types/model/Client'
import * as yup from 'yup'

export const headCells: Array<HeadCell<Client>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'contactDetails',
        label: 'Dane kontaktowe',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'orders',
        label: 'Liczba zleceń',
        disablePadding: false,
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

export const emptyForm = {
    id: null,
    name: '',
    contactDetails: '',
    companyId: '',
    orders: [],
}

export const validationSchema = yup.object({
    name: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
    contactDetails: yup.string().required('Wprowadź dane kontaktowe'),
})
