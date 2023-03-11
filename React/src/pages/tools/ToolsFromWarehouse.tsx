import { FilterFormProps } from "../../components/table/filter/TableFilter";
import FatTable from "../../components/table/FatTable";
import { useState } from "react";
import { useQuery } from "react-query";
import { AxiosError } from "axios";
import { getToolsFromWarehouse } from "../../api/tool.api";
import { selectedFilterInitStructure, selectedHeadCells } from "./helper";
import { useNavigate, useParams } from "react-router-dom";
import { getFilterParams, setNewFilterValues } from "../../helpers/filter.helper";
import { Tool } from "../../types/model/Tool";




const ToolsFromWarehouse = () => {
    const params = useParams();
    const [filterStructure, setFilterStructure] = useState(selectedFilterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(selectedFilterInitStructure))
    const navigation = useNavigate();

    const queryTools = useQuery<Array<Tool>, AxiosError>(
        ['tools', filterParams],
        async () => getToolsFromWarehouse({ queryParams: filterParams }, params.warehouseId )
    )

    const handleOnSearch = (filterParams: Object) => {
        setFilterStructure(setNewFilterValues(filterParams, selectedFilterInitStructure))
        setFilterParams(getFilterParams(filterStructure))
    }


    const handleResetFilter = () => {
        setFilterStructure(selectedFilterInitStructure)
    }



    const filterForm: (FilterFormProps) = {
        filterStructure: filterStructure,
        onSearch: handleOnSearch,
        onResetFilter: handleResetFilter
    }



    return <FatTable
        query={queryTools}
        filterForm={filterForm}
        headCells={selectedHeadCells}
        initOrderBy={'name'}
        onClickRow={(e, row) => {
            navigation(`/tools/${row.id}`)
            console.log(row)
        }}
        pageHeader='Lista narzędzi'
    />


}

export default ToolsFromWarehouse;