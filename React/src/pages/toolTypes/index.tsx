import { Filter, FilterFormProps } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredToolTypes } from '../../api/toolType.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { ToolType } from '../../types/model/ToolType'
import { useFormik } from 'formik'

const ToolTypes = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()

    const queryToolTypes = useQuery<Array<ToolType>, AxiosError>(['tooltype', filterParams], async () =>
        getFilteredToolTypes({ queryParams: filterParams }),
    )

    const filter: Filter = {
        formik: useFormik({
            initialValues: initialValues,

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
            query={queryToolTypes}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/tooltypes/${row.id}`)
            }}
            pageHeader="Lista typów narzędzi"
        />
    )
}

export default ToolTypes
