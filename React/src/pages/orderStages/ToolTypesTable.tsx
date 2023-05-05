import { Box, Button, TextField,  } from '@mui/material'
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
    isDisplayingMode:boolean
}

const OrderStageToolTypesTable = ({
    itemsArray,
    setPlannedData,
    plannedData,
    isDisplayingMode,
}: OrderStageToolTypesTableType) => {
    const [tableRowIndex, setTableRowIndex] = useState(0)
    const [selectedItemId, setSelectedItemId] = useState('')
    const [selectedItemNumber, setSelectedItemNumber] = useState(0)

    // [{ numberOfTools: 0, toolTypeId: 'toChange' }]

    const handleItemNumberChange = (event: any) => {
        const { value: newValue } = event.target
        setSelectedItemNumber(event.target.value)
        const regExp = /^\d*$/ // regular expression to match only digits
        if (regExp.test(newValue)) {
            setSelectedItemNumber(Number(newValue))
        }
    }

    useEffect(() => {
        console.log('plannedData', plannedData)
        if (!plannedData || plannedData.length === 0) {
            setPlannedData([{ numberOfTools: 0, toolTypeId: 'toChange' }])
        }
    }, [])

    useEffect(() => {
        console.log(
            'useeffect',
            'plannedData ' +
                plannedData +
                ' ' +
                selectedItemId +
                ' row' +
                tableRowIndex +
                ' number' +
                selectedItemNumber,
        )

        // if (!plannedData) {
        //     console.log('jestem tu-------')

        //     return setPlannedData([{ numberOfTools: selectedItemNumber, toolTypeId: selectedItemId }])
        // }
        // if (!!plannedData && plannedData.length === 0) {
        //     console.log('jestem tu')
        //     return setPlannedData([{ numberOfTools: selectedItemNumber, toolTypeId: selectedItemId }])
        // }
        if (!!plannedData && !!selectedItemId) {
            console.log('jestem tu2')

            const tempArray = [...plannedData]
            tempArray[tableRowIndex] = { numberOfTools: selectedItemNumber, toolTypeId: selectedItemId }
            setPlannedData(() => tempArray)
        }
    }, [selectedItemId, selectedItemNumber])

    const handleDeleteItem = (rowIndex: number) => {
        console.log('tableData', plannedData)
        const tempArray = [...plannedData]
        // console.log('looking', tableData)
        // console.log('looking2', tempArray)
        console.log('row', rowIndex)
        console.log('tempArray', tempArray)
        tempArray.splice(rowIndex, 1)
        console.log('tempArray2', tempArray)
        setPlannedData(()=> tempArray)
        // console.log('dziala delete?', tempArray)
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
                console.log('hejehejehej', event)
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
}
const TableItemNumber = ({
    selectedItemNumber,
    setTableRowIndex,
    handleItemNumberChange,
    rowIndex,
}: TableItemNumberType) => {
    const [displayNumber, setDisplayNumber] = useState(0)

    return (
        <TextField
            type="number"
            value={selectedItemNumber}
            id="outlined-basic"
            label="Ilość"
            variant="outlined"
            onChange={(event) => {
                setTableRowIndex(rowIndex)
                handleItemNumberChange(event)
                setDisplayNumber(event.target.value as any)
            }}
        />
    )
}