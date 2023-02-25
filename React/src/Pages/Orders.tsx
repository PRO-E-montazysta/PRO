import { FilterFormProps, FilterInputType } from "../components/tables/filter/TableFilter";
import OrdersTable from "../components/tables/OrdersTable";
import { getFilterParams, setNewFilterValues } from "../components/tables/filter/helper";
import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { AxiosError } from "axios";
import { getAllOrders } from "../api/order.api";



const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'name',
        value: 'hahah',
        label: 'Nazwa',
        type: 'text',
        typeValue: 'string'
    },
    {
        id: 'createdAtMin',
        value: '',
        label: 'Czas utworzenia od',
        type: 'datetime-local',
        typeValue: 'date',
    },
    {
        id: 'createdAtMax',
        value: '',
        label: 'Czas utworzenia do',
        type: 'datetime-local',
        typeValue: 'date',
    },
    {
        id: 'typeOfStatus',
        value: '',
        label: 'Status',
        type: 'multiselect',
        typeValue: 'date',
    }
]



const Orders = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))


    const queryOrders = useQuery<Array<any>, AxiosError>(
        ['orders', filterParams],
        async () => getAllOrders({ queryParams: filterParams })
    )

    useEffect(() => {
        console.log(filterParams)
        setFilterParams(getFilterParams(filterStructure))
    }, [filterStructure])


    const handleOnSearch = (filterParams: Object) => {
        setFilterStructure(setNewFilterValues(filterParams, filterInitStructure))
    }


    const handleResetFilter = () => {
        setFilterStructure(filterInitStructure)
    }



    const filterForm: (FilterFormProps) = {
        filterStructure: filterStructure,
        onSearch: handleOnSearch,
        onResetFilter: handleResetFilter
    }




    return <OrdersTable
        query={queryOrders}
        filterForm={filterForm}
    />


}

export default Orders;