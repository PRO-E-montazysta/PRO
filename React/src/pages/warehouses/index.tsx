import { Filter, FilterFormProps } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredWarehouses } from '../../api/warehouse.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getFormikMetadata, newFilterValues } from '../../helpers/filter.helper'
import { WarehouseFilterDto } from '../../types/model/Warehouse'
import { useFormik } from 'formik'

const Warehouses = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs, validationSchema } = getFormikMetadata(filterInitStructure)
    const navigation = useNavigate()

    const queryWarehouses = useQuery<Array<WarehouseFilterDto>, AxiosError>(['warehouses', filterParams], async () =>
        getFilteredWarehouses({ queryParams: filterParams }),
    )

    const filter: Filter = {
        formik: useFormik({
            initialValues: initialValues,
            validationSchema: validationSchema,
            onSubmit: () => {
                setFilterStructure(newFilterValues(filter.formik.values, filterStructure))
                setFilterParams(getFilterParams(filterStructure))
            },
            onReset: () => filter.formik.setValues(initialValues),
        }),
        inputs: inputs,
    }

    return (
        <FatTable
            idPropName="id"
            query={queryWarehouses}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/warehouses/${row.id}`)
            }}
            pageHeader="Lista magazynów"
        />
    )
}

export default Warehouses
