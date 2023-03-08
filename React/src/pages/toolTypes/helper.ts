import { FilterInputType } from "../../components/table/filter/TableFilter";
import { ToolType } from "../../types/model/ToolType";
import { HeadCell } from "../../components/table/sort/SortedTableHeader";


export const headCells: Array<HeadCell<ToolType>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'inServiceCount',
        label: 'Liczba w serwisie',
        disablePadding: false,
        numeric: true,
    },
    {
        type: 'string',
        id: 'availableCount',
        label: 'Liczba dostępnych',
        disablePadding: false,
        numeric: true,
    },
    {
        type: 'string',
        id: 'criticalNumber',
        label: 'Liczba krytyczna',
        disablePadding: false,
        numeric: true,
    }
]



export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'name',
        value: '',
        label: 'Nazwa',
        inputType: 'text',
        typeValue: 'string'
    }
]

