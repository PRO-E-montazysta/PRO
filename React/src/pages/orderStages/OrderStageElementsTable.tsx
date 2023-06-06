import { Box, Button, TextField, Typography } from '@mui/material'
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
import { OrderStageSimpleElementRelease } from '../../types/model/OrderStage'

type OrderStageElementsTableType = {
    itemsArray: Array<any> | undefined
    isDisplayingMode: boolean
    elementsListIds: Array<number> | []
    handleChange: (array: { numberOfElements: number; elementId: string }[]) => void
    elementsRef: React.MutableRefObject<
        {
            numberOfElements: number
            elementId: string
        }[]
    >
    releasedReturnedElementsInfo?: OrderStageSimpleElementRelease | []
}

const OrderStageElementsTable = forwardRef(
    (
        {
            itemsArray,
            isDisplayingMode,
            elementsListIds,
            handleChange,
            elementsRef,
            releasedReturnedElementsInfo,
        }: OrderStageElementsTableType,
        ref,
    ) => {
        const [tableRowIndex, setTableRowIndex] = useState(0)
        const [selectedItemId, setSelectedItemId] = useState('')
        const [selectedItemNumber, setSelectedItemNumber] = useState(0)
        const [tableData, setTableData] = useState<{ numberOfElements: number; elementId: string }[]>([
            { numberOfElements: 0, elementId: 'toChange' },
        ])

        const getElementsData = async () => {
            let filteredData = [{ numberOfElements: 0, elementId: 'toChange' }]

            if (!!elementsListIds) {
                const check = await Promise.all(
                    elementsListIds.map(async (element) => {
                        return await getPlannedElementById(element)
                    }),
                )
                if (!!check && check.length > 0) {
                    filteredData = check.map((element) => {
                        const data = {
                            numberOfElements: element.numberOfElements,
                            elementId: element.element.id.toString(),
                        }
                        return data
                    })
                }
                prepareDataForTable(filteredData)
            }
        }

        const prepareDataForTable = (
            filteredData: {
                numberOfElements: number
                elementId: string
            }[],
        ) => {
            if (!releasedReturnedElementsInfo) {
                return setTableData([...filteredData])
            }

            const releasedToolsThatWereNotPlanned = releasedReturnedElementsInfo.filter(
                (released) =>
                    !filteredData.some((planned) => {
                        return planned.elementId == released.elementId
                    }),
            )
            const preparedReleasedToolsThatWereNotPlanned = releasedToolsThatWereNotPlanned.map((element) => {
                const data = {
                    numberOfElements: 0,
                    elementId: element.elementId,
                }
                return data
            })
            if (preparedReleasedToolsThatWereNotPlanned.length > 0 && filteredData[0].elementId == 'toChange') {
                filteredData.pop()
            }
            const finalPreparedTable = filteredData.concat(preparedReleasedToolsThatWereNotPlanned)
            setTableData([...finalPreparedTable])
        }

        useEffect(() => {
            if (elementsRef.current.length > 0) {
                return setTableData([...elementsRef.current])
            }
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
                tempArray[tableRowIndex] = {
                    numberOfElements: tableData[tableRowIndex].numberOfElements,
                    elementId: selectedItemId,
                }
                setTableData(tempArray)
                handleChange(tempArray)
            }
        }, [selectedItemId])

        useEffect(() => {
            if (!!tableData && !!selectedItemId) {
                const tempArray = [...tableData]
                tempArray[tableRowIndex] = {
                    numberOfElements: selectedItemNumber,
                    elementId: tableData[tableRowIndex].elementId,
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
                return setTableData([{ numberOfElements: 0, elementId: 'toChange' }])
            }
            setTableData(tempArray)
        }

        const displayReleasedReturnedData = (plannedElementId: string) => {
            if (!releasedReturnedElementsInfo || releasedReturnedElementsInfo.length < 1) {
                return
            }
            const filteredElemetDetails = releasedReturnedElementsInfo.filter(
                (element) => element.elementId == plannedElementId,
            )

            if (filteredElemetDetails.length == 0) {
                return <Typography>0/0</Typography>
            }
            return (
                <Typography>
                    {filteredElemetDetails[0].releasedQuantity}/{filteredElemetDetails[0].returnedQuantity}
                </Typography>
            )
        }

        return (
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Element</TableCell>
                            <TableCell align="left">Planowana ilość</TableCell>
                            {isDisplayingMode && <TableCell align="right">Wydano/Zwrócono</TableCell>}
                            {!isDisplayingMode && (
                                <TableCell align="right">
                                    <Button
                                        sx={{ marginRight: '10px' }}
                                        color="primary"
                                        variant="contained"
                                        onClick={() => {
                                            setTableData((tableData) => [
                                                ...tableData!,
                                                { numberOfElements: 0, elementId: 'toChange' },
                                            ])
                                        }}
                                    >
                                        Dodaj
                                    </Button>
                                </TableCell>
                            )}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {!!isDisplayingMode &&
                        !!tableData &&
                        (tableData.length === 1 && tableData[0].elementId) == 'toChange' ? (
                            <Typography sx={{ padding: '20px' }}>Brak zaplanowanych lub wydanych elementów</Typography>
                        ) : (
                            tableData.map((rowData, rowIndex) => (
                                <TableRow key={rowIndex} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                    <TableCell component="th" scope="row">
                                        <Box sx={{ minWidth: 120 }}>
                                            <FormControl fullWidth>
                                                <InputLabel id="demo-simple-select-label">Element</InputLabel>
                                                <TableItemSelect
                                                    label={'Typ narzedzia'}
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
                                                    selectedItemNumber={rowData.numberOfElements}
                                                    setTableRowIndex={setTableRowIndex}
                                                    handleItemNumberChange={handleItemNumberChange}
                                                    rowIndex={rowIndex}
                                                    isDisplayingMode={isDisplayingMode}
                                                />
                                            </FormControl>
                                        </Box>
                                    </TableCell>
                                    {isDisplayingMode && (
                                        <TableCell align="right">
                                            {displayReleasedReturnedData(rowData.elementId)}
                                        </TableCell>
                                    )}
                                    {!isDisplayingMode && (
                                        <TableCell align="right">
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
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        )
    },
)

export default OrderStageElementsTable

const chooseSelectItemId = (
    itemsArray: Array<any> | undefined,
    setSelectedItemId: React.Dispatch<React.SetStateAction<string>>,
) => {
    if (itemsArray) {
        return itemsArray.map((item: any, index) => (
            <MenuItem
                key={uuidv4()}
                id={'elementsTable' + index}
                value={item.id}
                onClick={() => {
                    setSelectedItemId(item.id)
                }}
            >
                {item.name}
            </MenuItem>
        ))
    }
    return <MenuItem>Brak elementów</MenuItem>
}

type TableItemSelectTypes = {
    label: string
    itemsArray: Array<any> | undefined
    rowData: { numberOfElements: number; elementId: string }
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
            disabled={isDisplayingMode}
            value={rowData.elementId}
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
            InputProps={{
                inputProps: { min: 0 },
            }}
            onChange={(event) => {
                setTableRowIndex(rowIndex)
                handleItemNumberChange(event)
            }}
        />
    )
}
