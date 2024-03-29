import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import FatTable from '../../components/table/FatTable'
import { useFilterInitStructure, useHeadCells } from './helper'
import { getFilterParams, getFormikMetadata, newFilterValues } from '../../helpers/filter.helper'
import { Filter, FilterFormProps } from '../../components/table/filter/TableFilter'
import { Employee } from '../../types/model/Employee'
import { useFormik } from 'formik'
import { getFilteredEmployees } from '../../api/employee.api'

const Employees = () => {
    const [filterStructure, setFilterStructure] = useState(useFilterInitStructure())
    const [filterParams, setFilterParams] = useState(getFilterParams(useFilterInitStructure()))
    const { initialValues, inputs, validationSchema } = getFormikMetadata(useFilterInitStructure())
    const navigation = useNavigate()

    const queryData = useQuery<Array<Employee>, AxiosError>(['users', filterParams], async () =>
        getFilteredEmployees({ queryParams: filterParams }),
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
            query={queryData}
            filterProps={filter}
            headCells={useHeadCells()}
            initOrderBy={'firstName'}
            onClickRow={(e, row) => {
                navigation(`/employees/${row.id}`)
            }}
            pageHeader="Lista pracowników"
        />
    )
}

export default Employees
