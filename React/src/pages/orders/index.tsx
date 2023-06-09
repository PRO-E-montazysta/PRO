import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredOrders } from '../../api/order.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getFormikMetadata, newFilterValues } from '../../helpers/filter.helper'
import { Order } from '../../types/model/Order'
import { Filter } from '../../components/table/filter/TableFilter'
import { useFormik } from 'formik'
import { Container } from '@mui/material'

const Orders = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs, validationSchema } = getFormikMetadata(filterInitStructure)
    const navigation = useNavigate()

    const queryOrders = useQuery<Array<Order>, AxiosError>(['orders', filterParams], async () =>
        getFilteredOrders({ queryParams: filterParams }),
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
            query={queryOrders}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/orders/${row.id}`)
            }}
            pageHeader="Lista zleceń"
        />
    )
}

export default Orders
