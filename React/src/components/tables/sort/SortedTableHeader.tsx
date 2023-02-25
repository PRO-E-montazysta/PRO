import * as React from 'react';
import Box from '@mui/material/Box';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TableSortLabel, { tableSortLabelClasses } from '@mui/material/TableSortLabel';
import { visuallyHidden } from '@mui/utils';
import { Order } from './SortedTable';
import { v4 as uuidv4 } from 'uuid';
import styled from '@emotion/styled';
import { theme } from '../../../themes/baseTheme';


type HeadCellType = 'string' | 'avatar';

export interface HeadCell<T> {
    type: HeadCellType,
    disablePadding: boolean;
    id: keyof T;
    label: string;
    numeric: boolean;
    formatFn?: (value: any) => string;
}


export interface SortedTableHeadProps<T> {
    onRequestSort: (event: React.MouseEvent<unknown>, property: keyof T | undefined) => void;
    order: Order;
    orderBy: keyof T | undefined;
    headCells: HeadCell<T>[];
}


const StyledTableCell = styled(TableCell)(() => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.primary.light,
        color: theme.palette.primary.contrastText,
    },
}));

const StyledTableSortLabel = styled(TableSortLabel)(() => ({
    [`&.${tableSortLabelClasses.root}`]: {
        color: theme.palette.primary.contrastText
    },
    [`path`]: {
        color: theme.palette.primary.contrastText 
    },
}));



function SortedTableHeader<T>(props: SortedTableHeadProps<T>) {
    const { order, orderBy, onRequestSort, headCells } =
        props;
    const createSortHandler =
        (property: keyof T | undefined) => (event: React.MouseEvent<unknown>) => {
            onRequestSort(event, property);
        };

    return (
        <TableHead style={{
            
        }}>
            <TableRow>
                {headCells.map((headCell: HeadCell<T>, index: number) => (
                    <StyledTableCell
                        key={uuidv4()}
                        align={headCell.numeric ? 'right' : 'left'}
                        sx={{ padding: headCell.disablePadding ? '0 16px' : '', fontWeight: '600' }}
                        sortDirection={orderBy === headCell.id ? order : false}
                        color='white'
                    >
                        <StyledTableSortLabel
                            active={orderBy === headCell.id}
                            direction={orderBy === headCell.id ? order : 'asc'}
                            onClick={createSortHandler(headCell.id)}

                        >
                            {headCell.label}
                            {orderBy === headCell.id ? (
                                <Box component="span" sx={visuallyHidden}>
                                    {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                                </Box>
                            ) : null}
                        </StyledTableSortLabel>
                    </StyledTableCell>
                ))}
            </TableRow>
        </TableHead>
    );
}

export default SortedTableHeader;