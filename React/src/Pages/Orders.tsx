import { FilterInputType } from "../components/tables/filter/TableFilter";
import OrdersTable from "../components/tables/OrdersTable";
import { setNewFilterValues } from "../components/tables/filter/helper";
import { useState } from "react";



const filterInitValues: Array<FilterInputType> = [
    {
        id: 'name',
        value: '',
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
]



const Orders = () => {
    const [filter, setFilter] = useState(filterInitValues)


    const handleOnSearch = (filterForm: Object) => {
        setFilter(setNewFilterValues(filterForm, filterInitValues))
    }

    const handleResetFilter = () => {
        setFilter(filterInitValues)
    }

    return <OrdersTable filter={filter} onSearch={handleOnSearch} onResetFilter={handleResetFilter} />


}

export default Orders;