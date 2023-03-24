import { Box, Grid, Typography } from '@mui/material'
import { AxiosError } from 'axios'
import { useEffect, useMemo } from 'react'
import { UseQueryResult } from 'react-query'
import useBreakpoints, { AppSize } from '../../hooks/useBreakpoints'
import TableFilter, { Filter, FilterFormProps } from './filter/TableFilter'
import SortedTable from './sort/SortedTable'
import { HeadCell } from './sort/SortedTableHeader'

type FatTableParams<T> = {
    query: UseQueryResult<T[], AxiosError>
    filterProps?: Filter
    filterForm?: FilterFormProps
    headCells: Array<HeadCell<T>>
    initOrderBy: keyof T
    onClickRow: (event: React.MouseEvent<unknown>, row: T) => void
    pageHeader: string
}

function FatTable<T>(props: FatTableParams<T>) {
    const { query, filterProps, headCells, initOrderBy, onClickRow, pageHeader } = props
    const appSize = useBreakpoints()

    const headCellsFiltered = useMemo(() => {
        switch (appSize.active) {
            case 'mobile':
                return headCells.filter((h) => h.visibleInMode && h.visibleInMode.indexOf(AppSize.mobile) > -1)
            case 'tablet':
                return headCells.filter((h) => h.visibleInMode && h.visibleInMode.indexOf(AppSize.tablet) > -1)
            case 'notebook':
                return headCells.filter((h) => h.visibleInMode && h.visibleInMode.indexOf(AppSize.notebook) > -1)
            case 'desktop':
                return headCells.filter((h) => h.visibleInMode && h.visibleInMode.indexOf(AppSize.desktop) > -1)
            default:
                return headCells
        }
    }, [appSize])

    return (
        <div>
            <Box sx={{ p: '20px 0' }}>
                <Typography variant="h4" fontWeight="bold" padding="5px" color={'white'}>
                    {pageHeader}
                </Typography>
                <Box sx={{ p: '20px 0' }}>{filterProps && <TableFilter {...filterProps} />}</Box>

                {
                    <SortedTable
                        query={query}
                        headCells={headCellsFiltered}
                        initOrderBy={initOrderBy}
                        onClickRow={onClickRow}
                    />
                }
            </Box>
        </div>
    )
}

export default FatTable
