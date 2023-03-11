import { FilterFormProps } from "../../components/table/filter/TableFilter";
import FatTable from "../../components/table/FatTable";
import { useState } from "react";
import { useQuery } from "react-query";
import { AxiosError } from "axios";
import { getFilteredCompanies } from "../../api/company.api";
import { filterInitStructure, headCells } from "./helper";
import { useNavigate } from "react-router-dom";
import { getFilterParams, setNewFilterValues } from "../../helpers/filter.helper";
import { Company } from "../../types/model/Company";

const Companies = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const navigation = useNavigate();

    const queryCompanies = useQuery<Array<Company>, AxiosError>(
        ['companies', filterParams],
        async () => getFilteredCompanies({ queryParams: filterParams })
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
        query={queryCompanies}
        filterForm={filterForm}
        headCells={headCells}
        initOrderBy={'companyName'}
        onClickRow={(e, row) => {
            navigation(`/companies/${row.id}`)
            console.log(row)
        }}
        pageHeader='Lista firm'
    />
}

export default Companies;