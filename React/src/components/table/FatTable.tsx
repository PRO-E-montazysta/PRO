import { Box, Grid, Typography } from "@mui/material"
import { AxiosError } from "axios"
import { UseQueryResult } from 'react-query'
import TableFilter, { FilterFormProps } from "./filter/TableFilter"
import SortedTable from "./sort/SortedTable"
import { HeadCell } from "./sort/SortedTableHeader"



type FatTableParams<T> = {
    query: UseQueryResult<T[], AxiosError>
    filterForm: FilterFormProps
    headCells: Array<HeadCell<T>>
    initOrderBy: keyof T
    onClickRow: (event: React.MouseEvent<unknown>, row: T) => void
    pageHeader: string
}

function FatTable<T>(props: FatTableParams<T>) {
    const { query, filterForm, headCells, initOrderBy, onClickRow, pageHeader } = props


    return <div>
        <Box sx={{ flexGrow: 1, padding: 3}}>
            <Typography variant="h4" fontWeight="bold" color='white' padding='5px'>{pageHeader}</Typography>
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
                                    initOrderBy={initOrderBy}
                                    onClickRow={onClickRow}
                                />
                    }
                </Grid>
            </Grid>
        </Box>

    </div>
}

export default FatTable;