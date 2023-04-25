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
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'warehouse',
        label: 'Magazyn',
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

export const emptyForm = {
    id: null,
    name: '',
    createdAt: '',
    code: '',
    toolReleases: [],
    warehouseId: null,
    toolEvents: [],
    toolTypeId: null,
}

export const validationSchema = yup.object({
    name: yup.string().min(2, 'Nazwa musi zawierać co najmniej 2 znaki').required('Wprowadź nazwę'),
    warehouseId: yup.number().typeError('Wybierz magazyn'),
    toolTypeId: yup.number().typeError('Wybierz typ narzędzia'),
})
