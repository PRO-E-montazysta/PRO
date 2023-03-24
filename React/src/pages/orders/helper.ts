import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Order } from '../../types/model/Order'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { formatDate } from '../../helpers/format.helper'
import { priorityName, statusName, statusOptions } from '../../helpers/enum.helper'

import * as yup from 'yup'
import { AppSize } from '../../hooks/useBreakpoints'

export const headCells: Array<HeadCell<Order>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'createdAt',
        label: 'Czas utworzenia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => (date ? formatDate(date) : ''),
        visibleInMode: [AppSize.desktop],
    },
    {
        type: 'string',
        id: 'typeOfPriority',
        label: 'Priorytet',
        disablePadding: false,
        numeric: false,
        formatFn: (status: string) => priorityName(status),

        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'plannedStart',
        label: 'Planowany czas rozpoczęcia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => (date ? formatDate(date) : ''),
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'plannedEnd',
        label: 'Planowany czas zakończenia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => formatDate(date),
        visibleInMode: [AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'typeOfStatus',
        label: 'Status',
        disablePadding: false,
        numeric: false,
        formatFn: (status: string) => statusName(status),
        visibleInMode: [AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'orderStages',
        label: 'Liczba etapów',
        disablePadding: false,
        numeric: false,
        formatFn: (orderStages) => orderStages.length,
        visibleInMode: [AppSize.desktop],
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'typeOfStatus',
        value: ['PLANNED', 'IN_PROGRESS'],
        label: 'Status',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: statusOptions(),
    },
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
]

export const emptyForm = {
    id: null,
    name: '',
    typeOfStatus: '',
    plannedStart: '',
    plannedEnd: '',
    createdAt: '',
    editedAt: '',
    typeOfPriority: '',
    companyId: null,
    managerId: null,
    foremanId: null,
    specialistId: null,
    salesRepresentativeId: null,
    locationId: null,
    clientId: null,
    orderStages: [],
    attachments: [],
}

export const validationSchema = yup.object({
    name: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
    companyId: yup.number().typeError('Wybierz firmę'),
    typeOfStatus: yup.string().required('Wybierz status'),
    typeOfPriority: yup.string().required('Wybierz priorytet'),
    plannedStart: yup.date().required('Wybierz datę'),
    plannedEnd: yup.date().required('Wybierz datę'),
})
