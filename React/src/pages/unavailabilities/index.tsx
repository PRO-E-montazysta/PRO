import { Filter, FilterFormProps } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredUnavailabilities } from '../../api/unavailability.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { useFormik } from 'formik'
import { UnavailabilityFilter } from '../../types/model/Unavailability'

const Unavailabilities = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()

    const queryUnavailabilities = useQuery<Array<UnavailabilityFilter>, AxiosError>(
        ['unavailabilities', filterParams],
        async () => getFilteredUnavailabilities({ queryParams: filterParams }),
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
            query={queryUnavailabilities}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'assignedTo'}
            onClickRow={(e, row) => {
                navigation(`/unavailabilities/${row.id}`)
            }}
            pageHeader="Lista niedostępności"
        />
    )
}

export default Unavailabilities
