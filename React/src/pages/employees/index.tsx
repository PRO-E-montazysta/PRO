import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import FatTable from '../../components/table/FatTable'
import { filterInitStructure, headCells } from './helper'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { Filter, FilterFormProps } from '../../components/table/filter/TableFilter'
import { Employee } from '../../types/model/Employee'
import { getFilteredUsers } from '../../api/user.api'
import { useFormik } from 'formik'

const Employees = () => {
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()

    const queryData = useQuery<Array<Employee>, AxiosError>(['users', filterParams], async () =>
        getFilteredUsers({ queryParams: filterParams }),
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
            query={queryData}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'firstName'}
            onClickRow={(e, row) => {
                navigation(`/employees/${row.id}`)
            }}
            pageHeader="Lista pracowników"
        />
    )
}

export default Employees
