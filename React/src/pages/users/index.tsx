import { FilterFormProps } from "../../components/table/filter/TableFilter";
import FatTable from "../../components/table/FatTable";
import { useState } from "react";
import { useQuery } from "react-query";
import { AxiosError } from "axios";
import { getFilteredUsers } from "../../api/user.api";
import { filterInitStructure, headCells } from "./helper";
import { useNavigate } from "react-router-dom";
import { getFilterParams, setNewFilterValues } from "../../helpers/filter.helper";
import { AppUser } from "../../types/model/AppUser";

const Users = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const navigation = useNavigate();

    const queryUsers = useQuery<Array<AppUser>, AxiosError>(
        ['users', filterParams],
        async () => getFilteredUsers({ queryParams: filterParams })
    )

    const handleOnSearch = (filterParams: Object) => {
        setFilterStructure(setNewFilterValues(filterParams, filterInitStructure))
        setFilterParams(getFilterParams(filterStructure))
    }

    const handleResetFilter = () => {
        setFilterStructure(filterInitStructure)
    }

    const filterForm: (FilterFormProps) = {
        filterStructure: filterStructure,
        onSearch: handleOnSearch,
        onResetFilter: handleResetFilter
    }

    return <FatTable
        query={queryUsers}
        filterForm={filterForm}
        headCells={headCells}
        initOrderBy={'lastName'}
        onClickRow={(e, row) => {
            navigation(`/users/${row.id}`)
            console.log(row)
        }}
        pageHeader='Lista użytkowników'
    />
}

export default Users;
