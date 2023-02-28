import { FilterInputType } from "../../components/table/filter/TableFilter";
import { SelectMenuItemProps } from "../../components/base/Multiselect";
import { HeadCell } from "../../components/table/sort/SortedTableHeader";
import { Tool } from "../../types/model/Tool";

export const headCells: Array<HeadCell<Tool>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'code',
        label: 'Kod',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'warehouseId',
        label: 'Magazyn',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'toolTypeId',
        label: 'Typ narzędzia',
        disablePadding: false,
        numeric: false,
    }
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
        id: 'code',
        value: '',
        label: 'Kod',
        inputType: 'text',
        typeValue: 'string'
    },
    {
        id: 'warehouseId',
        value: '',
        label: 'Magazyn',
        inputType: 'multiselect',
        typeValue: 'Array',
        /*options: Object.entries(OrderStatus).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1]
            }
        })*/
    },
    {
        id: 'toolTypeId',
        value: '',
        label: 'Typ narzędzia',
        inputType: 'multiselect',
        typeValue: 'Array',
        /*options: Object.entries(OrderStatus).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1]
            }
        })*/
    }
]

export const selectedHeadCells: Array<HeadCell<Tool>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'code',
        label: 'Kod',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'toolTypeId',
        label: 'Typ narzędzia',
        disablePadding: false,
        numeric: false,
    }
]

export const selectedFilterInitStructure: Array<FilterInputType> = [
    {
        id: 'name',
        value: '',
        label: 'Nazwa',
        inputType: 'text',
        typeValue: 'string'
    },
    {
        id: 'code',
        value: '',
        label: 'Kod',
        inputType: 'text',
        typeValue: 'string'
    },
    {
        id: 'toolTypeId',
        value: '',
        label: 'Typ narzędzia',
        inputType: 'multiselect',
        typeValue: 'Array',
        /*options: Object.entries(OrderStatus).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1]
            }
        })*/
    }
]

