import { FilterFormProps } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredClients } from '../../api/client.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, setNewFilterValues } from '../../helpers/filter.helper'
import { Client } from '../../types/model/Client'

const Clients = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const navigation = useNavigate()

    const queryClients = useQuery<Array<Client>, AxiosError>(['clients', filterParams], async () =>
        getFilteredClients({ queryParams: filterParams }),
    )

    const handleOnSearch = (filterParams: Object) => {
        setFilterStructure(setNewFilterValues(filterParams, filterInitStructure))
        setFilterParams(getFilterParams(filterStructure))
    }

    const handleResetFilter = () => {
        setFilterStructure(filterInitStructure)
    }

    const filterForm: FilterFormProps = {
        filterStructure: filterStructure,
        onSearch: handleOnSearch,
        onResetFilter: handleResetFilter,
    }

    return (
        <FatTable
            query={queryClients}
            filterForm={filterForm}
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
