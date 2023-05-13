import { Box, Button, TextField } from '@mui/material'
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
import { forwardRef, useEffect, useState } from 'react'
import { getPlannedElementById } from '../../api/element.api'
import { v4 as uuidv4 } from 'uuid'
import { getPlannedToolTypesById } from '../../api/toolType.api'

type TestTestTestType = {
    itemsArray: Array<any> | undefined
    isDisplayingMode: boolean
    elementsListIds: Array<number> | []
    handleChange: (array: { numberOfTools: number; toolTypeId: string }[]) => void
}

const TestTestTest = forwardRef(
    ({ itemsArray, isDisplayingMode, elementsListIds, handleChange }: TestTestTestType, ref) => {
        const [tableRowIndex, setTableRowIndex] = useState(0)
        const [selectedItemId, setSelectedItemId] = useState('')
        const [selectedItemNumber, setSelectedItemNumber] = useState(0)
        const [tableData, setTableData] = useState<{ numberOfTools: number; toolTypeId: string }[]>([
            { numberOfTools: 0, toolTypeId: 'toChange' },
        ])

        const getElementsData = async () => {
            if (!!elementsListIds) {
                const check = await Promise.all(
                    elementsListIds.map(async (element) => {
                        return await getPlannedToolTypesById(element)
                    }),
                )
                console.log('what the f', check)
                if (!!check && check.length > 0) {
                    console.log('here1')
                    const filteredData = check.map((element) => {
                        const data = {
                            numberOfTools: element.numberOfTools,
                            toolTypeId: element.toolType.id.toString(),
                        }
                        return data
                    })
                    setTableData([...filteredData])
                }
            }
        }

        useEffect(() => {
            getElementsData()
        }, [])

        const handleItemNumberChange = (event: any) => {
            const { value: newValue } = event.target
            setSelectedItemNumber(event.target.value)
            const regExp = /^\d*$/ // regular expression to match only digits
            if (regExp.test(newValue)) {
                setSelectedItemNumber(Number(newValue))
            }
        }

        useEffect(() => {
            if (!!tableData && !!selectedItemId) {
                const tempArray = [...tableData]
                tempArray[tableRowIndex] = { numberOfTools: selectedItemNumber, toolTypeId: selectedItemId }
                setTableData(tempArray)
                handleChange(tempArray)
            }
        }, [selectedItemId, selectedItemNumber])

        const handleDeleteItem = (rowIndex: number) => {
            const tempArray = [...tableData]
            tempArray.splice(rowIndex, 1)
            setTableData(tempArray)
            handleChange(tempArray)
        }

        return (
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Typ narzędzia</TableCell>
                            <TableCell align="right">Planowana potrzebna ilość</TableCell>
                            {isDisplayingMode && <TableCell align="right">Wydane narzędzia - kody</TableCell>}
                            {!isDisplayingMode && <TableCell align="right">Akcja</TableCell>}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {!!tableData &&
                            tableData.map((rowData, rowIndex) => (
                                <TableRow key={rowIndex} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                    <TableCell component="th" scope="row">
                                        <Box sx={{ minWidth: 120 }}>
                                            <FormControl fullWidth>
                                                <InputLabel id="demo-simple-select-label">Typ narzędzia</InputLabel>
                                                <TableItemSelect
                                                    label={'Typ narzędzia'}
                                                    itemsArray={itemsArray}
                                                    rowData={rowData}
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
                                                <TableItemNumber
                                                    selectedItemNumber={rowData.numberOfTools}
                                                    setTableRowIndex={setTableRowIndex}
                                                    handleItemNumberChange={handleItemNumberChange}
                                                    rowIndex={rowIndex}
                                                />
                                            </FormControl>
                                        </Box>
                                    </TableCell>
                                    {isDisplayingMode && <TableCell align="right">bla</TableCell>}
                                    {!isDisplayingMode && (
                                        <TableCell align="right">
                                            {rowIndex === tableData.length - 1 && (
                                                <Button
                                                    color="primary"
                                                    variant="contained"
                                                    onClick={() => {
                                                        setTableData((tableData) => [
                                                            ...tableData!,
                                                            { numberOfTools: 0, toolTypeId: 'toChange' },
                                                        ])
                                                    }}
                                                >
                                                    Dodaj następne
                                                </Button>
                                            )}
                                            <Button
                                                color="error"
                                                variant="contained"
                                                onClick={() => {
                                                    handleDeleteItem(rowIndex)
                                                }}
                                            >
                                                Usuń
                                            </Button>
                                        </TableCell>
                                    )}
                                </TableRow>
                            ))}
                    </TableBody>
                </Table>
            </TableContainer>
        )
    },
)

export default TestTestTest

//select for table - so i can manage state for every row
const chooseSelectItemId = (
    itemsArray: Array<any> | undefined,
    setSelectedItemId: React.Dispatch<React.SetStateAction<string>>,
) => {
    if (itemsArray) {
        return itemsArray.map((item: any) => (
            <MenuItem
                key={uuidv4()}
                id={uuidv4()}
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
    rowData: { numberOfTools: number; toolTypeId: string }
    rowIndex: number
    setTableRowIndex: React.Dispatch<React.SetStateAction<number>>
    setSelectedItemId: React.Dispatch<React.SetStateAction<string>>
}

const TableItemSelect = ({
    label,
    itemsArray,
    rowData,
    rowIndex,
    setSelectedItemId,
    setTableRowIndex,
}: TableItemSelectTypes) => {
    return (
        <Select
            labelId="demo-simple-select-label"
            key={uuidv4()}
            defaultValue=""
            value={rowData.toolTypeId}
            label={label}
            onChange={(event: SelectChangeEvent) => {
                setTableRowIndex(rowIndex)
            }}
        >
            {chooseSelectItemId(itemsArray, setSelectedItemId)}
        </Select>
    )
}

type TableItemNumberType = {
    selectedItemNumber: number
    setTableRowIndex: React.Dispatch<React.SetStateAction<number>>
    handleItemNumberChange: (event: any) => void
    rowIndex: number
}
const TableItemNumber = ({
    selectedItemNumber,
    setTableRowIndex,
    handleItemNumberChange,
    rowIndex,
}: TableItemNumberType) => {
    return (
        <TextField
            type="number"
            value={selectedItemNumber}
            id={uuidv4()}
            label="Ilość"
            variant="outlined"
            onChange={(event) => {
                setTableRowIndex(rowIndex)
                handleItemNumberChange(event)
            }}
        />
    )
}
