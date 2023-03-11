import { FilterFormProps } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredWarehouses } from '../../api/warehouse.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, setNewFilterValues } from '../../helpers/filter.helper'
import { WarehouseFilterDto } from '../../types/model/Warehouse'

const Warehouses = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const navigation = useNavigate()

    const queryWarehouses = useQuery<Array<WarehouseFilterDto>, AxiosError>(['warehouses', filterParams], async () =>
        getFilteredWarehouses({ queryParams: filterParams }),
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
            query={queryWarehouses}
            filterForm={filterForm}
            headCells={headCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/warehouses/${row.id}`)
                console.log(row)
            }}
            pageHeader="Lista magazynów"
        />
    )
}

export default Warehouses
