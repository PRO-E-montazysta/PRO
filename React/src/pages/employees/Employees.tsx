import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import FatTable from '../../components/table/FatTable'
import { filterInitStructure, headCells } from './helper'
import { getFilterParams, setNewFilterValues } from '../../helpers/filter.helper'
import { FilterFormProps } from '../../components/table/filter/TableFilter'
import { Employee } from '../../types/model/Employee'
import { getFilteredUsers } from '../../api/user.api'

const Employees = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const navigation = useNavigate()

    const queryData = useQuery<Array<Employee>, AxiosError>(['users', filterParams], async () =>
        getFilteredUsers({ queryParams: filterParams }),
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
            query={queryData}
            filterForm={filterForm}
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
