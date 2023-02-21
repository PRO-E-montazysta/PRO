import { Box, Grid } from "@mui/material"
import axios, { AxiosError } from "axios"
import { useState } from "react"
import { useQuery } from 'react-query'
import { useNavigate } from "react-router-dom"
import { getAllOrders } from "../../api/order.api"
import { removeEmptyFilterValues } from "./filter/helper"
import TableFilter, { FilterFormProps, FilterInputType } from "./filter/TableFilter"
import SortedTable from "./sort/SortedTable"
import { HeadCell } from "./sort/SortedTableHeader"



const headCells: Array<HeadCell<any>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'createdAt',
        label: 'Czas utworzenia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => date ? date.slice(0, 19).replace('T', ' ') : ''
    },
]

type BreaksTableParams = {
    filter: Array<FilterInputType>
    onSearch: (filters: any) => void
    onResetFilter: () => void
}

const OrdersTable = (props: BreaksTableParams) => {
    const { filter, onSearch, onResetFilter } = props
    const [filterParams, setFilterParams] = useState<Record<string, unknown>>({})
    const navigation = useNavigate();

    const { isLoading, isError, isSuccess, error, data } = useQuery<Array<any>, AxiosError>(
        ['orders', filterParams],
        async () => getAllOrders({ queryParams: filterParams })
    )

    const handleOnSearch = (filterForm: Object) => {
        setFilterParams(removeEmptyFilterValues(filterForm))
        onSearch(filterForm)
    }


    const filterForm: (FilterFormProps) = {
        form: filter,
        onSearch: handleOnSearch,
        onResetFilter: onResetFilter,
        submitBtnStyle: {
            // margin: '20px',
        },
        resetBtnStyle: {
            // margin: '20px',
        }
    }



    return <div>
        <Box sx={{ flexGrow: 1, padding: 3 }}>
            <Grid container spacing={2}>
                <Grid item xs={3}>
                    <TableFilter {...filterForm} />
                </Grid>
                <Grid item xs={9}>
                    {
                        isLoading ? <p>Ładowanie...</p>
                            : isError ? <p>{error.message}</p>
                                :
                                <SortedTable
                                    data={data ? data : []}
                                    headCells={headCells}
                                    initOrderBy={'name'}
                                    onClickRow={(e, row) => {
                                        navigation(`/orders/${row.id}`)
                                        console.log(row)
                                    }}
                                />
                    }
                </Grid>
            </Grid>
        </Box>

    </div>
}

export default OrdersTable;