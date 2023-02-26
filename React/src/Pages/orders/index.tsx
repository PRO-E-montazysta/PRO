import { FilterFormProps } from "../../components/table/filter/TableFilter";
import FatTable from "../../components/table/FatTable";
import { useState } from "react";
import { useQuery } from "react-query";
import { AxiosError } from "axios";
import { getAllOrders } from "../../api/order.api";
import { filterInitStructure, headCells } from "./helper";
import { useNavigate } from "react-router-dom";
import { getFilterParams, setNewFilterValues } from "../../helpers/filter.helper";




const Orders = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const navigation = useNavigate();

    const queryOrders = useQuery<Array<any>, AxiosError>(
        ['orders', filterParams],
        async () => getAllOrders({ queryParams: filterParams })
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
        query={queryOrders}
        filterForm={filterForm}
        headCells={headCells}
        initOrderBy={'name'}
        onClickRow={(e, row) => {
            navigation(`/orders/${row.id}`)
            console.log(row)
        }}
    />


}

export default Orders;