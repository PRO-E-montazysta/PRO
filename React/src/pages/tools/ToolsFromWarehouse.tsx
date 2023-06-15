import { Filter, FilterFormProps } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getToolsFromWarehouse } from '../../api/tool.api'
import { selectedFilterInitStructure, selectedHeadCells } from './helper'
import { useNavigate, useParams } from 'react-router-dom'
import { getFilterParams, getFormikMetadata, newFilterValues } from '../../helpers/filter.helper'
import { Tool } from '../../types/model/Tool'
import { useFormik } from 'formik'

const ToolsFromWarehouse = () => {
    const params = useParams()
    const [filterParams, setFilterParams] = useState(getFilterParams(selectedFilterInitStructure))
    const { initialValues, inputs, validationSchema } = getFormikMetadata(selectedFilterInitStructure)
    const navigation = useNavigate()

    const queryTools = useQuery<Array<Tool>, AxiosError>(['tools', filterParams], async () =>
        getToolsFromWarehouse({ queryParams: filterParams }, params.warehouseId),
    )

    const filter: Filter = {
        formik: useFormik({
            initialValues: initialValues,
            validationSchema: validationSchema,
            onSubmit: () => setFilterParams(filter.formik.values),
            onReset: () => filter.formik.setValues(initialValues),
        }),
        inputs: inputs,
    }

    return (
        <FatTable
            idPropName="id"
            query={queryTools}
            filterProps={filter}
            headCells={selectedHeadCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/tools/${row.id}`)
            }}
            pageHeader="Lista narzędzi"
        />
    )
}

export default ToolsFromWarehouse
