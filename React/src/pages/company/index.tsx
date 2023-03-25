import { Filter, FilterFormProps } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredCompanies } from '../../api/company.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { Company } from '../../types/model/Company'
import { useFormik } from 'formik'

const Companies = () => {
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()

    const queryCompanies = useQuery<Array<Company>, AxiosError>(['companies', filterParams], async () =>
        getFilteredCompanies({ queryParams: filterParams }),
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
            query={queryCompanies}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'companyName'}
            onClickRow={(e, row) => {
                navigation(`/companies/${row.id}`)
                console.log(row)
            }}
            pageHeader="Lista firm"
        />
    )
}

export default Companies
