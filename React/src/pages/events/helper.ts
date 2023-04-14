import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Event } from '../../types/model/Event'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import * as yup from 'yup'
import { AppSize } from '../../hooks/useBreakpoints'
import { formatDate } from '../../helpers/format.helper'
import { eventStatusName, eventStatusOptions, eventTypeName, eventTypeOptions } from '../../helpers/enum.helper'

export const headCells: Array<HeadCell<Event>> = [
    {
        type: 'string',
        id: 'itemName',
        label: 'Nazwa przedmiotu',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'eventType',
        label: 'Typ przedmiotu',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (status: string) => eventTypeName(status),
    },
    {
        type: 'string',
        id: 'status',
        label: 'Status',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (status: string) => eventStatusName(status),
    },
    {
        type: 'string',
        id: 'eventDate',
        label: 'Data zgłoszenia',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (date: string) => formatDate(date),
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'itemName',
        value: '',
        label: 'Nazwa przedmiotu',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'typeOfStatus',
        value: '',
        label: 'Status',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: eventStatusOptions(),
    },
    {
        id: 'eventType',
        value: '',
        label: 'Typ przedmiotu',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: eventTypeOptions(),
    },
    {
        id: 'eventDateMin',
        value: '',
        label: 'Czas utworzenia od',
        inputType: 'datetime-local',
        typeValue: 'date',
    },
    {
        id: 'eventDateMax',
        value: '',
        label: 'Czas utworzenia do',
        inputType: 'datetime-local',
        typeValue: 'date',
    },
]

export const toolEventEmptyForm = {
    id: null,
    description: '',
    status: 'CREATED',
    toolId: '',
}

export const toolEventValidationSchema = yup.object({
    toolId: yup.string().required('Wybierz narzędzie!'),
})

export const elementEventEmptyForm = {
    id: null,
    description: '',
    status: 'CREATED',
    quantity: '',
    elementId: '',
}

export const elementEventValidationSchema = yup.object({
    elementId: yup.string().required('Wybierz element!'),
    quantity: yup.number().required('Wprowadź ilość!'),
})
