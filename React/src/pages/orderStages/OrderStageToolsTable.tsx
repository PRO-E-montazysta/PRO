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
import Select from '@mui/material/Select'
import { forwardRef, useEffect, useState } from 'react'
import { v4 as uuidv4 } from 'uuid'
import { getPlannedToolTypesById } from '../../api/toolType.api'

type OrderStageToolsTableType = {
    itemsArray: Array<any> | undefined
    isDisplayingMode: boolean
    toolsTypeListIds: Array<number> | []
    handleChange: (array: { numberOfTools: number; toolTypeId: string }[]) => void
    toolsRef: React.MutableRefObject<
        {
            numberOfTools: number
            toolTypeId: string
        }[]
    >
}

const OrderStageToolsTable = forwardRef(
    ({ itemsArray, isDisplayingMode, toolsTypeListIds, handleChange, toolsRef }: OrderStageToolsTableType, ref) => {
        const [tableRowIndex, setTableRowIndex] = useState(0)
        const [selectedItemId, setSelectedItemId] = useState('')
        const [selectedItemNumber, setSelectedItemNumber] = useState(0)
        const [tableData, setTableData] = useState<{ numberOfTools: number; toolTypeId: string }[]>([
            { numberOfTools: 0, toolTypeId: 'toChange' },
        ])

        const getToolsTypeData = async () => {
            if (!!toolsTypeListIds) {
                const check = await Promise.all(
                    toolsTypeListIds.map(async (tool) => {
                        return await getPlannedToolTypesById(tool)
                    }),
                )
                if (!!check && check.length > 0) {
                    const filteredData = check.map((tool) => {
                        const data = {
                            numberOfTools: tool.numberOfTools,
                            toolTypeId: tool.toolType.id.toString(),
                        }
                        return data
                    })
                    setTableData([...filteredData])
                }
            }
        }

        useEffect(() => {
            if (toolsRef.current.length > 0) {
                return setTableData([...toolsRef.current])
            }
            getToolsTypeData()
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
                tempArray[tableRowIndex] = {
                    numberOfTools: tableData[tableRowIndex].numberOfTools,
                    toolTypeId: selectedItemId,
                }
                setTableData(tempArray)
                handleChange(tempArray)
            }
        }, [selectedItemId])

        useEffect(() => {
            if (!!tableData && !!selectedItemId) {
                const tempArray = [...tableData]
                tempArray[tableRowIndex] = {
                    numberOfTools: selectedItemNumber,
                    toolTypeId: tableData[tableRowIndex].toolTypeId,
                }
                setTableData(tempArray)
                handleChange(tempArray)
            }
        }, [selectedItemNumber])

        const handleDeleteItem = (rowIndex: number) => {
            const tempArray = [...tableData]
            tempArray.splice(rowIndex, 1)
            handleChange(tempArray)
            if (tempArray.length === 0) {
                return setTableData([{ numberOfTools: 0, toolTypeId: 'toChange' }])
            }
            setTableData(tempArray)
        }

        return (
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Typ narzędzia</TableCell>
                            <TableCell align="right">Planowana ilość</TableCell>
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
                                                    isDisplayingMode={isDisplayingMode}
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
                                                    isDisplayingMode={isDisplayingMode}
                                                />
                                            </FormControl>
                                        </Box>
                                    </TableCell>
                                    {isDisplayingMode && <TableCell align="right"></TableCell>}
                                    {!isDisplayingMode && (
                                        <TableCell align="right">
                                            {rowIndex === tableData.length - 1 && (
                                                <Button
                                                    sx={{ marginRight: '10px' }}
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

export default OrderStageToolsTable

const chooseSelectItemId = (
    itemsArray: Array<any> | undefined,
    setSelectedItemId: React.Dispatch<React.SetStateAction<string>>,
) => {
    if (itemsArray) {
        return itemsArray.map((item: any, index) => (
            <MenuItem
                key={uuidv4()}
                id={'toolstableItem' + index}
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
    isDisplayingMode: boolean
}

const TableItemSelect = ({
    label,
    itemsArray,
    rowData,
    rowIndex,
    setSelectedItemId,
    setTableRowIndex,
    isDisplayingMode,
}: TableItemSelectTypes) => {
    return (
        <Select
            sx={{
                '& .MuiInputBase-input.Mui-disabled': {
                    WebkitTextFillColor: '#000000',
                },
            }}
            labelId="demo-simple-select-label"
            key={uuidv4()}
            value={rowData.toolTypeId}
            disabled={isDisplayingMode}
            label={label}
            onChange={() => {
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
    isDisplayingMode: boolean
}
const TableItemNumber = ({
    selectedItemNumber,
    setTableRowIndex,
    handleItemNumberChange,
    rowIndex,
    isDisplayingMode,
}: TableItemNumberType) => {
    return (
        <TextField
            sx={{
                '& .MuiInputBase-input.Mui-disabled': {
                    WebkitTextFillColor: '#000000',
                },
            }}
            type="number"
            value={selectedItemNumber}
            disabled={isDisplayingMode}
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
