import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import * as yup from 'yup'
import { AppSize } from '../../hooks/useBreakpoints'
import { FormInputProps } from '../../types/form'
import { Role } from '../../types/roleEnum'
import { ElementInWarehouseFilterDto } from '../../types/model/ElementInWarehouse'
import { useEffect, useState } from 'react'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getAllWarehouses } from '../../api/warehouse.api'
import { formatArrayToOptions } from '../../helpers/format.helper'
import { Warehouse } from '../../types/model/Warehouse'

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
        id: 'warehouseId',
        value: '',
        label: 'Magazyn',
        inputType: 'multiselect',
        typeValue: 'Array',
    },
    {
        id: 'minCount',
        value: '',
        label: 'Minimalna ilość',
        inputType: 'number',
        typeValue: 'number',
    },
]

export const useFilterStructure = () => {
    const [filterStructure, setFilterStructure] = useState<Array<FilterInputType>>(filterInitStructure)

    const queryWarehouse = useQuery<Array<Warehouse>, AxiosError>(['warehouse-list'], getAllWarehouses)

    useEffect(() => {
        if (queryWarehouse.isFetched) {
            const freshStructure: Array<any> = filterStructure.map((s) => {
                if (s.id == 'warehouseId')
                    s.options = formatArrayToOptions('id', (x: Warehouse) => x.name, queryWarehouse.data)
                return s
            })
            setFilterStructure(freshStructure)
        }
    }, [queryWarehouse.isFetched])

    return { filterStructure, setFilterStructure }
}

export const useFormStructure = (): Array<FormInputProps> => {
    return [
        {
            label: 'Ilość magazynowa',
            id: 'inWarehouseCount',
            initValue: '',
            type: 'input',
            validation: yup
                .number()
                .typeError('Podana wartość musi być liczbą')
                .min(0, 'Podana wartość musi być większa lub równa 0'),
            editPermissionRoles: [Role.WAREHOUSE_MANAGER, Role.WAREHOUSE_MAN],
        },
        {
            label: 'Regał',
            id: 'rack',
            initValue: '',
            type: 'input',
            editPermissionRoles: [Role.WAREHOUSE_MANAGER],
            validation: yup.string().min(1, 'Wprowadź co najmniej 1 znak').max(255, 'Wprowadź co najwyżej 255 znaków'),
        },
        {
            label: 'Półka',
            id: 'shelf',
            initValue: '',
            type: 'input',
            editPermissionRoles: [Role.WAREHOUSE_MANAGER],
            validation: yup.string().min(1, 'Wprowadź co najmniej 1 znak').max(255, 'Wprowadź co najwyżej 255 znaków'),
        },
    ]
}
