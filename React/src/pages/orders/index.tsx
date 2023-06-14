import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredOrders } from '../../api/order.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { Order } from '../../types/model/Order'
import { Filter } from '../../components/table/filter/TableFilter'
import { useFormik } from 'formik'
import { Container } from '@mui/material'

const Orders = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()

    const queryOrders = useQuery<Array<Order>, AxiosError>(['orders', filterParams], async () =>
        getFilteredOrders({ queryParams: filterParams }),
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
            pageHeader="Lista zleceń"
            query={queryOrders}
            filterProps={filter}
            headCells={headCells}
            idPropName="id"
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/orders/${row.id}`)
            }}
        />
    )
}

export default Orders
