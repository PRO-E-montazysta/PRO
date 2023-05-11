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
import { useEffect, useState } from 'react'
import { OrderStage } from '../../types/model/OrderStage'
import { AxiosError } from 'axios'
import { UseQueryResult, useQuery } from 'react-query'
import { PlannedToolType } from '../../types/model/ToolType'
import { getPlannedToolTypesById } from '../../api/toolType.api'
import { CustomTextField } from '../../components/form/FormInput'

type OrderStageToolTypesTableType = {
    itemsArray: Array<any> | undefined
    plannedData: Array<{ numberOfTools: number; toolTypeId: string }>
    setPlannedData: React.Dispatch<
        React.SetStateAction<
            | {
                  numberOfTools: number
                  toolTypeId: string
              }[]
            | undefined
        >
    >
    isDisplayingMode: boolean
    toolTypesListIds: Array<number> | []
}

const OrderStageToolTypesTable = ({
    itemsArray,
    setPlannedData,
    plannedData,
    isDisplayingMode,
    toolTypesListIds,
}: OrderStageToolTypesTableType) => {
    const [tableRowIndex, setTableRowIndex] = useState(0)
    const [selectedItemId, setSelectedItemId] = useState('')
    const [selectedItemNumber, setSelectedItemNumber] = useState(0)
    const [orderStageData, setOrderStageData] = useState(plannedData)
    //start2

    const queryPlannedToolTypes = useQuery<Array<PlannedToolType>, AxiosError>(['toolsType-list'], async () => {
        return await Promise.all(
            toolTypesListIds.map(async (tool) => {
                return await getPlannedToolTypesById(tool)
            }),
        )
    })

    const handleItemNumberChange = (event: any) => {
        const { value: newValue } = event.target
        setSelectedItemNumber(event.target.value)
        const regExp = /^\d*$/ // regular expression to match only digits
        if (regExp.test(newValue)) {
            setSelectedItemNumber(Number(newValue))
        }
    }

    useEffect(() => {
        if (!plannedData || plannedData.length === 0) {
            setPlannedData([{ numberOfTools: 0, toolTypeId: 'toChange' }])
        }
    }, [])

    useEffect(() => {
        if (!!queryPlannedToolTypes.data) {
            const filteredData = queryPlannedToolTypes!.data!.map((tool) => {
                const data = { numberOfTools: tool.numberOfTools, toolTypeId: tool.toolType.id.toString() }
                return data
            })
            setPlannedData(filteredData)
        }
    }, [queryPlannedToolTypes])

    useEffect(() => {
        // if (!plannedData) {

        //     return setPlannedData([{ numberOfTools: selectedItemNumber, toolTypeId: selectedItemId }])
        // }
        // if (!!plannedData && plannedData.length === 0) {
        //     return setPlannedData([{ numberOfTools: selectedItemNumber, toolTypeId: selectedItemId }])
        // }
        if (!!plannedData && !!selectedItemId) {
            const tempArray = [...plannedData]
            tempArray[tableRowIndex] = { numberOfTools: selectedItemNumber, toolTypeId: selectedItemId }
            setPlannedData(() => tempArray)
        }
    }, [selectedItemId, selectedItemNumber])

    const handleDeleteItem = (rowIndex: number) => {
        const tempArray = [...plannedData]
        tempArray.splice(rowIndex, 1)
        setPlannedData(() => tempArray)
    }

    const getTableBody = () => {
        console.log('kupa', plannedData)

        // if (!plannedData) {
        //     console.log('kupa1111', plannedData)
        //     setPlannedData([{ numberOfTools: 0, toolTypeId: 'toChange' }])
        // }

        // if (!!plannedData && plannedData.length === 0 && isDisplayingMode) {
        //     console.log('here', plannedData.length)
        //     return <h4> Nie zostały dodane jeszcze żadne type narzędzi</h4>
        // }
        if (!!plannedData) {
            console.log('kupa222', plannedData)

            return plannedData.map((rowData, rowIndex) => (
                <TableRow key={rowIndex} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                    <TableCell component="th" scope="row">
                        <Box sx={{ minWidth: 120 }}>
                            <FormControl fullWidth>
                                <InputLabel id="demo-simple-select-label">Typ narzedzia</InputLabel>
                                <TableItemSelect
                                    label={'Typ narzedzia'}
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
                                    isDisplayingMode={isDisplayingMode}
                                />
                            </FormControl>
                        </Box>
                    </TableCell>
                    {isDisplayingMode && <TableCell align="right">bla</TableCell>}
                    {!isDisplayingMode && (
                        <TableCell align="right">
                            {rowIndex === plannedData.length - 1 && (
                                <Button
                                    color="primary"
                                    variant="contained"
                                    onClick={() => {
                                        setPlannedData((plannedData) => [
                                            ...plannedData!,
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
            ))
        }
    }

    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Typ narzedzia</TableCell>
                        <TableCell align="right">Planowana potrzebna ilosc</TableCell>
                        {isDisplayingMode && <TableCell align="right">Wydane narzedzie</TableCell>}
                        {!isDisplayingMode && <TableCell align="right">Akcja</TableCell>}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {!!plannedData &&
                        plannedData.map((rowData, rowIndex) => (
                            <TableRow key={rowIndex} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                <TableCell component="th" scope="row">
                                    <Box sx={{ minWidth: 120 }}>
                                        <FormControl fullWidth>
                                            <InputLabel id="demo-simple-select-label">Typ narzedzia</InputLabel>
                                            <TableItemSelect
                                                label={'Typ narzedzia'}
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
                                                isDisplayingMode={isDisplayingMode}
                                            />
                                        </FormControl>
                                    </Box>
                                </TableCell>
                                {isDisplayingMode && <TableCell align="right">bla</TableCell>}
                                {!isDisplayingMode && (
                                    <TableCell align="right">
                                        {rowIndex === plannedData.length - 1 && (
                                            <Button
                                                color="primary"
                                                variant="contained"
                                                onClick={() => {
                                                    setPlannedData((plannedData) => [
                                                        ...plannedData!,
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
}

export default OrderStageToolTypesTable

//select for table - so i can manage state for every row
const chooseSelectItemId = (
    defaultValue: string,
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
    // const [displayedSelectedItemName, setDisplaySelectedItemName] = useState('')

    return (
        <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={rowData.toolTypeId}
            label={label}
            onChange={(event: SelectChangeEvent) => {
                // setDisplaySelectedItemName(event.target.value as string)
                setTableRowIndex(rowIndex)
            }}
        >
            {chooseSelectItemId(rowData.toolTypeId, itemsArray, setSelectedItemId)}
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
        <CustomTextField
            readOnly={isDisplayingMode!}
            type="number"
            value={selectedItemNumber === 0 ? '' : selectedItemNumber}
            id="outlined-basic"
            label="Ilość"
            variant="outlined"
            onChange={(event) => {
                setTableRowIndex(rowIndex)
                handleItemNumberChange(event)
            }}
        />
    )
}
