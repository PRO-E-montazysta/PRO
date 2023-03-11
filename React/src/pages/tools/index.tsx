import { FilterFormProps, FilterInputType } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useEffect, useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredTools } from '../../api/tool.api'
import { headCells, useFilterStructure } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, setNewFilterValues } from '../../helpers/filter.helper'
import { Tool } from '../../types/model/Tool'

const Tools = () => {
    const { filterStructure, setFilterStructure } = useFilterStructure()
    const [filterParams, setFilterParams] = useState(getFilterParams(filterStructure))

    const navigation = useNavigate()

    const queryTools = useQuery<Array<Tool>, AxiosError>(['tools', filterParams], async () =>
        getFilteredTools({ queryParams: filterParams }),
    )

    const handleOnSearch = (filterParams: Object) => {
        setFilterStructure(setNewFilterValues(filterParams, filterStructure))
        setFilterParams(getFilterParams(filterStructure))
    }

    const handleResetFilter = () => {
        setFilterStructure(filterStructure)
    }

    const filterForm: FilterFormProps = {
        filterStructure: filterStructure,
        onSearch: handleOnSearch,
        onResetFilter: handleResetFilter,
    }

    return (
        <FatTable
            query={queryTools}
            filterForm={filterForm}
            headCells={headCells}
            initOrderBy={'name'}
            onClickRow={(e, row) => {
                navigation(`/tools/${row.id}`)
                console.log(row)
            }}
            pageHeader="Lista narzędzi"
        />
    )
}

export default Tools
