import { Filter } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { useFormik } from 'formik'
import { ElementInWarehouseFilterDto } from '../../types/model/ElementInWarehouse'
import { getElementInWarehouseCounts } from '../../api/elementInWarehouse.api'

type ElementInWarehouseViewParams = {
    elementId: number
}

const ElementInWarehouseView = (params: ElementInWarehouseViewParams) => {
    const { elementId } = params

    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()

    const queryElementInWarehouse = useQuery<Array<ElementInWarehouseFilterDto>, AxiosError>(
        ['elements-in-warehouse', { id: params.elementId }, filterParams],
        async () => getElementInWarehouseCounts({ queryParams: filterParams }, elementId),
    )

    const filter: Filter = {
        formik: useFormik({
            initialValues: initialValues,
            // validationSchema={{}}
            onSubmit: () => {
                setFilterStructure(setNewFilterValues(filter.formik.values, filterStructure))
                setFilterParams(getFilterParams(filterStructure))
            },
            onReset: () => filter.formik.setValues(initialValues),
        }),
        inputs: inputs,
    }

    return (
        <FatTable
            idPropName="id"
            query={queryElementInWarehouse}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'inWarehouseCount'}
            onClickRow={(e, row) => {
                navigation(`/element-in-warehouse/${row.id}`)
            }}
        />
    )
}

export default ElementInWarehouseView
