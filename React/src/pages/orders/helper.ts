import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Order, OrderPriority, OrderStatus } from '../../types/model/Order'
import { SelectMenuItemProps } from '../../components/base/Multiselect'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { formatDate } from '../../helpers/format.helper'
import { priorityName, priorityOptions, statusName, statusOptions } from '../../helpers/enum.helper'

import * as yup from 'yup'

export const headCells: Array<HeadCell<Order>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'createdAt',
        label: 'Czas utworzenia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => (date ? formatDate(date) : ''),
    },
    {
        type: 'string',
        id: 'typeOfPriority',
        label: 'Priorytet',
        disablePadding: false,
        numeric: false,
        formatFn: (status: string) => priorityName(status),
    },
    {
        type: 'string',
        id: 'plannedStart',
        label: 'Planowany czas rozpoczęcia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => (date ? formatDate(date) : ''),
    },
    {
        type: 'string',
        id: 'plannedEnd',
        label: 'Planowany czas zakończenia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => (date ? date.slice(0, 16).replace('T', ' ') : ''),
    },
    {
        type: 'string',
        id: 'typeOfStatus',
        label: 'Status',
        disablePadding: false,
        numeric: false,
        formatFn: (status: string) => statusName(status),
    },
    {
        type: 'string',
        id: 'orderStages',
        label: 'Liczba etapów',
        disablePadding: false,
        numeric: false,
        formatFn: (orderStages) => orderStages.length,
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
    {
        id: 'createdAtMin',
        value: '',
        label: 'Czas utworzenia od',
        inputType: 'datetime-local',
        typeValue: 'date',
    },
    {
        id: 'createdAtMax',
        value: '',
        label: 'Czas utworzenia do',
        inputType: 'datetime-local',
        typeValue: 'date',
    },
    {
        id: 'typeOfStatus',
        value: ['PLANNED', 'IN_PROGRESS'],
        label: 'Status',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: statusOptions(),
    },
]

export const emptyForm = {
    id: -1,
    name: '',
    typeOfStatus: '',
    plannedStart: '',
    plannedEnd: '',
    createdAt: '',
    editedAt: '',
    typeOfPriority: '',
    companyId: -1,
    managerId: -1,
    foremanId: -1,
    specialistId: -1,
    salesRepresentativeId: -1,
    locationId: -1,
    clientId: -1,
    orderStages: [],
    attachments: [],
}

export const validationSchema = yup.object({
    name: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
    companyId: yup.number().min(0, 'Wybierz firmę'),
    typeOfStatus: yup.string().required('Wybierz status'),
    typeOfPriority: yup.string().required('Wybierz priorytet'),
})
