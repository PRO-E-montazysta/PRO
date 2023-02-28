import { FilterInputType } from "../../components/table/filter/TableFilter";
import { Warehouse } from "../../types/model/Warehouse";
import { HeadCell } from "../../components/table/sort/SortedTableHeader";


export const headCells: Array<HeadCell<Warehouse>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'description',
        label: 'Opis',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'openingHours',
        label: 'Godziny otwarcia',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'locationId',
        label: 'Adres',
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
        id: 'description',
        value: '',
        label: 'Opis',
        inputType: 'text',
        typeValue: 'string'
    },
    {
        id: 'zipCode',
        value: '',
        label: 'Kod pocztowy',
        inputType: 'text',
        typeValue: 'string'
    }
]

