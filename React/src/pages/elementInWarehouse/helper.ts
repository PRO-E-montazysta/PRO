import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import * as yup from 'yup'
import { AppSize } from '../../hooks/useBreakpoints'
import { FormInputProps } from '../../types/form'
import { Role } from '../../types/roleEnum'
import { ElementInWarehouseFilterDto } from '../../types/model/ElementInWarehouse'

export const headCells: Array<HeadCell<ElementInWarehouseFilterDto>> = [
    {
        type: 'string',
        id: 'warehouse',
        label: 'Magazyn',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'inWarehouseCount',
        label: 'Ilość',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'rack',
        label: 'Regał',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'shelf',
        label: 'Półka',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'minCount',
        value: '',
        label: 'Minimalna ilość',
        inputType: 'number',
        typeValue: 'number',
    },
    {
        id: 'warehouseId',
        value: '',
        label: 'Magazyn',
        inputType: 'multiselect',
        typeValue: 'Array',
    },
]

export const useFormStructure = (): Array<FormInputProps> => {
    return [
        {
            label: 'Ilość magazynowa',
            id: 'inWarehouseCount',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Ilość w jednostce',
            id: 'inUnitCount',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Regał',
            id: 'rack',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Półka',
            id: 'shelf',
            initValue: '',
            type: 'input',
        },
    ]
}
