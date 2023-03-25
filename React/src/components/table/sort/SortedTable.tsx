import * as React from 'react'
import Box from '@mui/material/Box'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TablePagination from '@mui/material/TablePagination'
import TableRow from '@mui/material/TableRow'
import Paper from '@mui/material/Paper'

import { useEffect, useState } from 'react'
import SortedTableHeader, { HeadCell } from './SortedTableHeader'
import { v4 as uuidv4 } from 'uuid'
import { Avatar, CircularProgress, Typography } from '@mui/material'
import { green } from '@mui/material/colors'
import { UseQueryResult } from 'react-query'
import { AxiosError } from 'axios'
import useBreakpoints from '../../../hooks/useBreakpoints'

function descendingComparator<T>(a: T, b: T, orderBy: keyof T | undefined, order: Order) {
    if (orderBy === undefined) return 0
    //gdy jedna z wartości jest nullem, odrzuca
    //zapewnie nie wyświetlanie pustej listy gdy sortowanie desc a brak wartości
    if (a[orderBy] != null && b[orderBy] == null) return order == 'asc' ? 1 : -1
    if (a[orderBy] == null && b[orderBy] != null) return order == 'asc' ? -1 : 1

    if (b[orderBy] < a[orderBy]) {
        return -1
    }
    if (b[orderBy] > a[orderBy] || b[orderBy] === 0) {
        return 1
    }
    return 0
}

export type Order = 'asc' | 'desc'

function getComparator<T>(order: Order, orderBy: keyof T | undefined): (a: T, b: T) => number {
    return order === 'desc'
        ? (a, b) => descendingComparator<T>(a, b, orderBy, order)
        : (a, b) => -descendingComparator<T>(a, b, orderBy, order)
}

interface SortedTableProps<T> {
    headCells: HeadCell<T>[]
    query: UseQueryResult<T[], AxiosError>
    initOrderBy: keyof T
    initOrderByDesc?: boolean
    onClickRow: (event: React.MouseEvent<unknown>, row: T) => void
}

export default function SortedTable<T>(props: SortedTableProps<T>) {
    const { headCells, query, initOrderBy, onClickRow } = props
    const [order, setOrder] = useState<Order>(props.initOrderByDesc ? 'desc' : 'asc')
    const [orderBy, setOrderBy] = useState<keyof T>()
    const [page, setPage] = useState(0)
    const [rowsPerPage, setRowsPerPage] = useState(10)
    const appSize = useBreakpoints()

    useEffect(() => {
        setOrderBy(initOrderBy)
    }, [initOrderBy])

    const handleRequestSort = (event: React.MouseEvent<unknown>, property: keyof T | undefined) => {
        const isAsc = orderBy === property && order === 'asc'
        setOrder(isAsc ? 'desc' : 'asc')
        setOrderBy(property)
        console.log(isAsc ? 'desc' : 'asc')
    }

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage)
    }

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10))
        setPage(0)
    }

    const handleClickRow = (event: React.MouseEvent<unknown>, row: T) => onClickRow(event, row)

    // Avoid a layout jump when reaching the last page with empty rows.
    const emptyRows = page > 0 && query.data ? Math.max(0, (1 + page) * rowsPerPage - query.data.length) : 0

    return (
        <Box sx={{ width: '100%', minWidth: '280px' }}>
            <Paper sx={{ width: '100%', borderRadius: '10px 10px 5px 5px' }}>
                {query.isLoading || query.isError ? (
                    <Box
                        sx={{
                            display: 'flex',
                            minHeight: '200px',
                            justifyContent: 'center',
                            alignItems: 'center',
                        }}
                    >
                        {query.isLoading ? <CircularProgress /> : <Typography>Brak danych do wyświetlenia</Typography>}
                    </Box>
                ) : (
                    <TableContainer
                        sx={{
                            borderRadius: '5px',
                        }}
                    >
                        <Table style={{ tableLayout: 'fixed' }} aria-labelledby="tableTitle">
                            <SortedTableHeader
                                order={order}
                                orderBy={orderBy}
                                onRequestSort={handleRequestSort}
                                headCells={headCells}
                            />
                            <TableBody>
                                {query.data &&
                                    query.data
                                        .sort(getComparator<T>(order, orderBy))
                                        .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                        .map((row: T, index) => {
                                            return (
                                                <TableRow
                                                    hover
                                                    onClick={(event) => handleClickRow(event, row)}
                                                    tabIndex={-1}
                                                    key={uuidv4()}
                                                    style={{
                                                        height: 53,
                                                    }}
                                                >
                                                    {headCells.map((headCell: HeadCell<T>, index: number) => {
                                                        let cellValue = row[headCell.id] as any
                                                        if (headCell.formatFn) cellValue = headCell.formatFn(cellValue)
                                                        return (
                                                            <TableCell
                                                                key={uuidv4()}
                                                                align={headCell.numeric ? 'right' : 'left'}
                                                                sx={{
                                                                    width: '100px',
                                                                    padding:
                                                                        appSize.isMobile || appSize.isTablet
                                                                            ? '8px'
                                                                            : '16px',
                                                                }}
                                                            >
                                                                {headCell.type === 'string' ? (
                                                                    cellValue
                                                                ) : (
                                                                    <Avatar
                                                                        sx={{
                                                                            width: 40,
                                                                            height: 40,
                                                                            bgcolor: green[500],
                                                                        }}
                                                                        variant="rounded"
                                                                        src={cellValue}
                                                                    />
                                                                )}
                                                            </TableCell>
                                                        )
                                                    })}
                                                </TableRow>
                                            )
                                        })}
                                {emptyRows > 0 && (
                                    <TableRow
                                        style={{
                                            height: 53 * emptyRows,
                                        }}
                                    >
                                        <TableCell colSpan={headCells.length} />
                                    </TableRow>
                                )}
                            </TableBody>
                        </Table>

                        <TablePagination
                            rowsPerPageOptions={[5, 10, 20, 50]}
                            component="div"
                            labelRowsPerPage={appSize.isMobile ? '' : 'Na stronie'}
                            labelDisplayedRows={({ from, to, count }) => {
                                return `${from}–${to} z ${count !== -1 ? count : `więcej niż ${to}`}`
                            }}
                            count={query.data ? query.data.length : 0}
                            rowsPerPage={rowsPerPage}
                            page={page}
                            onPageChange={handleChangePage}
                            onRowsPerPageChange={handleChangeRowsPerPage}
                        />
                    </TableContainer>
                )}
            </Paper>
        </Box>
    )
}
