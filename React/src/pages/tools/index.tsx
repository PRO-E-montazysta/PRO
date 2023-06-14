import { Filter, FilterFormProps, FilterInputType } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useEffect, useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredTools } from '../../api/tool.api'
import { headCells, useFilterStructure } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { Tool } from '../../types/model/Tool'
import { useFormik } from 'formik'

const Tools = () => {
    const { filterStructure, setFilterStructure } = useFilterStructure()
    const [filterParams, setFilterParams] = useState(getFilterParams(filterStructure))

    const { initialValues, inputs } = getInputs(filterStructure)
    const navigation = useNavigate()

    const queryTools = useQuery<Array<Tool>, AxiosError>(['tools', filterParams], async () =>
        getFilteredTools({ queryParams: filterParams }),
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
            query={queryTools}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/tools/${row.id}`)
            }}
            pageHeader="Lista narzędzi"
        />
    )
}

export default Tools
