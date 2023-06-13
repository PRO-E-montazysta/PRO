import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { Tool } from '../../types/model/Tool'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { Warehouse } from '../../types/model/Warehouse'
import { ToolType } from '../../types/model/ToolType'
import { getAllWarehouses } from '../../api/warehouse.api'
import { getAllToolTypes } from '../../api/toolType.api'
import { formatArrayToOptions } from '../../helpers/format.helper'
import { useEffect, useState } from 'react'

import * as yup from 'yup'
import { AppSize } from '../../hooks/useBreakpoints'
import { Role } from '../../types/roleEnum'
import { FormInputProps } from '../../types/form'

const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'name',
        value: '',
        label: 'Nazwa',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'code',
        value: '',
        label: 'Kod',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'warehouse_Id',
        value: '',
        label: 'Magazyn',
        inputType: 'multiselect',
        typeValue: 'Array',
    },
    {
        id: 'toolType_Id',
        value: '',
        label: 'Typ narzędzia',
        inputType: 'multiselect',
        typeValue: 'Array',
    },
]

export const useFilterStructure = () => {
    const [filterStructure, setFilterStructure] = useState<Array<FilterInputType>>(filterInitStructure)

    const queryWarehouse = useQuery<Array<Warehouse>, AxiosError>(['warehouse-list'], getAllWarehouses)
    const queryToolType = useQuery<Array<ToolType>, AxiosError>(['tooltype-list'], getAllToolTypes, {
        staleTime: 10 * 1000,
    })

    useEffect(() => {
        if (queryWarehouse.isFetched) {
            const freshStructure: Array<any> = filterStructure.map((s) => {
                if (s.id == 'warehouse_Id')
                    s.options = formatArrayToOptions('id', (x: Warehouse) => x.name, queryWarehouse.data)
                return s
            })
            setFilterStructure(freshStructure)
        }
    }, [queryWarehouse.isFetched])

    useEffect(() => {
        if (queryToolType.isFetched) {
            const freshStructure: Array<any> = filterStructure.map((s) => {
                if (s.id == 'toolType_Id')
                    s.options = formatArrayToOptions('id', (x: Warehouse) => x.name, queryToolType.data)
                return s
            })
            setFilterStructure(freshStructure)
        }
    }, [queryToolType.isFetched])

    return { filterStructure, setFilterStructure }
}

export const headCells: Array<HeadCell<Tool>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'code',
        label: 'Kod',
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'warehouse',
        label: 'Magazyn',
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'toolType',
        label: 'Typ narzędzia',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
]

export const selectedHeadCells: Array<HeadCell<Tool>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'code',
        label: 'Kod',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'toolType',
        label: 'Typ narzędzia',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
]

export const selectedFilterInitStructure: Array<FilterInputType> = [
    {
        id: 'name',
        value: '',
        label: 'Nazwa',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'code',
        value: '',
        label: 'Kod',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'toolType_Id',
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
    },
]

export const useFormStructure = (): Array<FormInputProps> => {
    const queryWarehouse = useQuery<Array<Warehouse>, AxiosError>(['warehouse-list'], getAllWarehouses, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    const queryToolType = useQuery<Array<ToolType>, AxiosError>(['toolType-list'], getAllToolTypes, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })

    return [
        {
            label: 'Nazwa narzędzia',
            id: 'name',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(3, 'Nazwa musi zawierać co najmniej 3 znaki')
                .max(255, 'Nazwa może zawierać maksymalnie 255 znaki')
                .required('Wprowadź nazwę'),
        },
        {
            label: 'Kod',
            id: 'code',
            initValue: '',
            type: 'input',
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Typ narzędzia',
            id: 'toolTypeId',
            initValue: null,
            type: 'select',
            validation: yup.number().typeError('Wybierz typ narzędzia'),
            options: formatArrayToOptions('id', (x: ToolType) => x.name, queryToolType.data),
        },
        {
            label: 'Magazyn',
            id: 'warehouseId',
            initValue: null,
            type: 'select',
            validation: yup.number().typeError('Wybierz magazyn'),
            options: formatArrayToOptions('id', (x: Warehouse) => x.name, queryWarehouse.data),
        },
        {
            label: 'Data utworzenia',
            id: 'createdAt',
            initValue: '',
            type: 'date-time',
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
    ]
}
