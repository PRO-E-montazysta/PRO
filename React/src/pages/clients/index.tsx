import { Filter } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredClients } from '../../api/client.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getFormikMetadata, newFilterValues } from '../../helpers/filter.helper'
import { Client } from '../../types/model/Client'
import { useFormik } from 'formik'

const Clients = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs, validationSchema } = getFormikMetadata(filterInitStructure)
    const navigation = useNavigate()

    const queryClients = useQuery<Array<Client>, AxiosError>(['clients', filterParams], async () =>
        getFilteredClients({ queryParams: filterParams }),
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
            query={queryClients}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/clients/${row.id}`)
            }}
            pageHeader="Lista klientów"
        />
    )
}

export default Clients
