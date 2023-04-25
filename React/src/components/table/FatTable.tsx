import { Box, Grid, Typography } from '@mui/material'
import { AxiosError } from 'axios'
import { useEffect, useMemo } from 'react'
import { UseQueryResult } from 'react-query'
import useBreakpoints, { AppSize } from '../../hooks/useBreakpoints'
import TableFilter, { Filter, FilterFormProps } from './filter/TableFilter'
import SortedTable from './sort/SortedTable'
import { HeadCell } from './sort/SortedTableHeader'


import { theme } from '../../themes/baseTheme'

type FatTableParams<T> = {
    query: UseQueryResult<T[], AxiosError>
    filterProps?: Filter
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
        <Box sx={{ p: appSize.isMobile || appSize.isTablet ? '10px' : '20px', maxWidth: '1200px', m: 'auto' }}>
            <Typography
                variant="h4"
                fontWeight="bold"
                padding="5px"
                color={theme.palette.primary.contrastText}
                fontSize={appSize.isMobile || appSize.isTablet ? '22px' : '32px'}
            >
                {pageHeader}
            </Typography>
            {filterProps && (
                <Box sx={{ p: appSize.isMobile || appSize.isTablet ? '10px 0' : '20px 0' }}>
                    {<TableFilter {...filterProps} />}
                </Box>
            )}

            {
                <SortedTable
                    query={query}
                    headCells={headCellsFiltered}
                    initOrderBy={initOrderBy}
                    onClickRow={onClickRow}
                />
            }
        </Box>
    )
}

export default FatTable
