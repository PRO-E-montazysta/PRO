import { FilterInputType } from "../../components/table/filter/TableFilter";
import { Order, OrderPriority, OrderStatus } from "../../types/model/Order";
import { SelectMenuItemProps } from "../../components/base/Multiselect";
import { HeadCell } from "../../components/table/sort/SortedTableHeader";


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
        formatFn: (date: string) => date ? date.slice(0, 16).replace('T', ' ') : ''
    },
    {
        type: 'string',
        id: 'typeOfPriority',
        label: 'Priorytet',
        disablePadding: false,
        numeric: false,
        formatFn: (status: string) => Object.values(OrderPriority)[Object.keys(OrderPriority).indexOf(status)],
    },
    {
        type: 'string',
        id: 'plannedStart',
        label: 'Planowany czas rozpoczęcia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => date ? date.slice(0, 16).replace('T', ' ') : ''
    },
    {
        type: 'string',
        id: 'plannedEnd',
        label: 'Planowany czas zakończenia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => date ? date.slice(0, 16).replace('T', ' ') : ''
    },
    {
        type: 'string',
        id: 'typeOfStatus',
        label: 'Status',
        disablePadding: false,
        numeric: false,
        formatFn: (status: string) => Object.values(OrderStatus)[Object.keys(OrderStatus).indexOf(status)]
    },
    {
        type: 'string',
        id: 'orderStages',
        label: 'Liczba etapów',
        disablePadding: false,
        numeric: false,
        formatFn: (orderStages) => orderStages.length
    },
]



export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'name',
        value: '',
        label: 'Nazwa',
        inputType: 'text',
        typeValue: 'string'
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
        options: Object.entries(OrderStatus).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1]
            }
        })
    }
]

