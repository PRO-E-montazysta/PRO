import { Box, Grid } from "@mui/material"
import { margin } from "@mui/system"
import axios, { AxiosError } from "axios"
import { useState } from "react"
import { useQuery, UseQueryResult } from 'react-query'
import { useNavigate } from "react-router-dom"
import { getAllOrders } from "../../api/order.api"
import { OrderPriority, OrderStatus } from "../../types/model/Order"
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
    {
        type: 'string',
        id: 'typeOfPriority',
        label: 'Status',
        disablePadding: false,
        numeric: false,
        formatFn: (status: OrderPriority) => Object.values(OrderPriority)[Object.keys(OrderPriority).indexOf(status)]
    },
    {
        type: 'string',
        id: 'plannedStart',
        label: 'Planowany czas rozpoczęcia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => date ? date.slice(0, 19).replace('T', ' ') : ''
    },
    {
        type: 'string',
        id: 'plannedEnd',
        label: 'Planowany czas zakończenia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => date ? date.slice(0, 19).replace('T', ' ') : ''
    },
    {
        type: 'string',
        id: 'typeOfStatus',
        label: 'Status',
        disablePadding: false,
        numeric: false,
        formatFn: (status: OrderStatus) => Object.values(OrderStatus)[Object.keys(OrderStatus).indexOf(status)]
    },
    {
        type: 'string',
        id: 'orderStages',
        label: 'Liczba etapów',
        disablePadding: false,
        numeric: false,
        formatFn: (orderStages) => orderStages.length
    },
]

type BreaksTableParams = {
    query: UseQueryResult<any[], AxiosError>
    filterForm: FilterFormProps
}

const OrdersTable = (props: BreaksTableParams) => {
    const { query, filterForm } = props
    const navigation = useNavigate();

    return <div>
        <Box sx={{ flexGrow: 1, padding: 3 }}>
            <Grid container spacing={2}>
                <Grid item width={'300px'}>
                    <TableFilter {...filterForm} />
                </Grid>
                <Grid item width={'calc(100vw - 350px)'} margin={'0 auto'} >
                    {
                        query.isLoading ? <p>Ładowanie...</p>
                            : query.isError ? <p>{query.error.message}</p>
                                :
                                <SortedTable
                                    data={query.data}
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