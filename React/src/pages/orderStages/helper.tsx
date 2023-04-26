import { Box, Button, TextField, Typography } from '@mui/material'
import IconButton, { IconButtonProps } from '@mui/material/IconButton'
import { styled } from '@mui/material/styles'
import * as React from 'react'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import Paper from '@mui/material/Paper'
import InputLabel from '@mui/material/InputLabel'
import MenuItem from '@mui/material/MenuItem'
import FormControl from '@mui/material/FormControl'
import Select, { SelectChangeEvent } from '@mui/material/Select'
import { UseQueryResult } from 'react-query'
import { OrderStage } from '../../types/model/OrderStage'
import { AxiosError } from 'axios'
import { useEffect, useState } from 'react'

type TabPanelProps = {
    children?: React.ReactNode
    index: number
    value: number
}

export const TabPanel = (props: TabPanelProps) => {
    const { children, value, index, ...other } = props

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    )
}

interface ExpandMoreProps extends IconButtonProps {
    expand: boolean
}

export const ExpandMore = styled((props: ExpandMoreProps) => {
    const { expand, ...other } = props
    return <IconButton {...other} />
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}))

///orderDetails Tables

type OrderStageDetailsTableType = {
    itemsArray: Array<any> | undefined
    setItem: React.Dispatch<
        React.SetStateAction<
            | [
                  {
                      numberOfTools: number
                      toolTypeId: string
                  },
              ]
            | undefined
        >
    >
}

const OrderStageDetailsTable = ({ itemsArray, setItem }: OrderStageDetailsTableType) => {
    const [tableData, setTableData] = useState([{ numberOfTools: 0, toolTypeId: 'toChange' }])
    const [tableRow, setTableRow] = useState([{}])
    const [tableRowIndex, setTableRowIndex] = useState(0)
    const [selectedItemId, setSelectedItemId] = useState('')
    const [selectedItemNumber, setSelectedItemNumber] = useState(0)

    //useEffect, ktory odpali sie gdy zmieni sie stan i ustawi TableData odpowiednio
    // const handleChange = () => {
    //     if (index === 0) {
    //         setTableData([{ index: index, numberOfTools: 0, toolTypeId: 'sada' }])
    //     }
    //     setTableData((prevData) => [...prevData, { index: index, numberOfTools: 1, toolTypeId: 'sada' }])
    // }

    const handleItemNumberChange = (event: any) =>{
        const { value: newValue } = event.target;
        setSelectedItemNumber(event.target.value)
        const regExp = /^\d*$/; // regular expression to match only digits
        if (regExp.test(newValue) ) {
            setSelectedItemNumber(newValue);
        }
    }

    useEffect(() => {
        console.log('useeffect', selectedItemId + ' row' + tableRowIndex + ' number' + selectedItemNumber)
    }, [selectedItemId, selectedItemNumber])

    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Typ narzedzia</TableCell>
                        <TableCell align="right">Planowana potrzebna ilosc</TableCell>
                        <TableCell align="right">Wydane narzedzie</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {tableRow.map((row, rowIndex) => (
                        <TableRow key={rowIndex} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                            <TableCell component="th" scope="row">
                                <Box sx={{ minWidth: 120 }}>
                                    <FormControl fullWidth>
                                        <InputLabel id="demo-simple-select-label">Typ narzedzia</InputLabel>
                                        <TableItemSelect
                                            label={'Typ narzedzia'}
                                            itemsArray={itemsArray}
                                            rowIndex={rowIndex}
                                            setSelectedItemId={setSelectedItemId}
                                            setTableRowIndex={setTableRowIndex}
                                        />
                                    </FormControl>
                                </Box>
                            </TableCell>
                            <TableCell align="right">
                                <Box sx={{ minWidth: 120 }}>
                                    <FormControl fullWidth>
                                        <TextField type='number' value={selectedItemNumber} id="outlined-basic" label="Ilość" variant="outlined" onChange={(event)=>{
                                            setTableRowIndex(rowIndex)
                                            handleItemNumberChange(event)
                                        }}
                                        />
                                        {/* <TableItemSelect
                                            label={'Ilość'}
                                            itemsArray={itemsArray}
                                            rowIndex={rowIndex}
                                            setSelectedItemNumber={setSelectedItemNumber}
                                            setTableRowIndex={setTableRowIndex}
                                        /> */}
                                    </FormControl>
                                </Box>
                            </TableCell>
                            <TableCell align="right">bla</TableCell>
                            <Button
                                color="primary"
                                variant="contained"
                                onClick={() => {
                                    setTableRow((tableData) => [...tableData, { new: 'new' }])
                                }}
                            >
                                Add
                            </Button>
                            <Button
                                color="primary"
                                variant="contained"
                                onClick={() => {
                                    setTableRow((tableData) => [...tableData, { new: 'new' }])
                                }}
                            >
                                Delete
                            </Button>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default OrderStageDetailsTable

//select for table - so i can manage state for every row
const chooseSelectItemId = (
    itemsArray: Array<any> | undefined,
    setSelectedItemId: React.Dispatch<React.SetStateAction<string>>,
) => {
    if (itemsArray) {
        return itemsArray.map((item: any) => (
            <MenuItem
                value={item.id}
                onClick={() => {
                    setSelectedItemId(item.id)
                }}
            >
                {item.name}
            </MenuItem>
        ))
    }
    return <MenuItem>Brak typow narzedzi</MenuItem>
}

type TableItemSelectTypes = {
    label: string
    itemsArray: Array<any> | undefined
    rowIndex: number
    setTableRowIndex: React.Dispatch<React.SetStateAction<number>>
    setSelectedItemId: React.Dispatch<React.SetStateAction<string>>
}

const TableItemSelect = ({
    label,
    itemsArray,
    rowIndex,
    setSelectedItemId,
    setTableRowIndex,
}: TableItemSelectTypes) => {
    const [displayedSelectedItemName, setDisplaySelectedItemName] = useState('')

    return (
        <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={displayedSelectedItemName}
            label={label}
            onChange={(event: SelectChangeEvent) => {
                console.log('hejehejehej', event)
                setDisplaySelectedItemName(event.target.value as string)
                setTableRowIndex(rowIndex)
                // setTableData((prevData) => [...prevData, { index: index, numberOfTools: 1, toolTypeId: 'sada' }])
            }}
        >
            {chooseSelectItemId(itemsArray, setSelectedItemId)}
        </Select>
    )
}
