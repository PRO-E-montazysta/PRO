import { Filter, FilterFormProps } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { Element } from '../../types/model/Element'
import { getFilteredElements } from '../../api/element.api'
import { useFormik } from 'formik'

const Elements = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()

    const queryOrders = useQuery<Array<Element>, AxiosError>(['orders', filterParams], async () =>
        getFilteredElements({ queryParams: filterParams }),
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
            query={queryOrders}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/elements/${row.id}`)
            }}
            pageHeader="Lista elementów"
        />
    )
}

export default Elements
