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
    const [filterParams, setFilterParams] = useState(getFilterParams([]))
    const { initialValues, inputs } = getInputs([])
    const navigation = useNavigate()

    const queryTools = useQuery<Array<Tool>, AxiosError>(['tools', filterParams], async () =>
        getFilteredTools({ queryParams: filterParams }),
    )

    const filter: Filter = {
        formik: useFormik({
            initialValues: initialValues,
            // validationSchema={{}}
            onSubmit: () => setFilterParams(filter.formik.values),
            onReset: () => filter.formik.setValues(initialValues),
        }),
        inputs: inputs,
    }

    return (
        <FatTable
            query={queryTools}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/tools/${row.id}`)
                console.log(row)
            }}
            pageHeader="Lista narzędzi"
        />
    )
}

export default Tools
