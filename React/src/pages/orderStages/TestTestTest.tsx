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
import { OrderStage } from '../../types/model/OrderStage'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { PlannedToolType } from '../../types/model/ToolType'
import { number } from 'yup'
import { Ref, forwardRef, useEffect, useState } from 'react'
import { PlannedElements } from '../../types/model/Element'
import { getPlannedElementById } from '../../api/element.api'
import { v4 as uuidv4 } from 'uuid'

type TestTestTestType = {
    itemsArray: Array<any> | undefined
    // plannedData: Array<{ numberOfElements: number; elementId: string }>
    // setPlannedData: React.Dispatch<
    //     React.SetStateAction<
    //         | {
    //               numberOfElements: number
    //               elementId: string
    //           }[]
    //         | undefined
    //     >
    // >
    isDisplayingMode: boolean
    elementsListIds: Array<number> | []
    handleChange: (array: { numberOfElements: number; elementId: string }[]) => void
    refValue: Ref<{ numberOfElements: number; elementId: string }[]>
}

const TestTestTest = forwardRef(
    ({ itemsArray, isDisplayingMode, elementsListIds, handleChange, refValue }: TestTestTestType, ref) => {
        const [tableRowIndex, setTableRowIndex] = useState(0)
        const [selectedItemId, setSelectedItemId] = useState('')
        const [selectedItemNumber, setSelectedItemNumber] = useState(0)
        // const [isInitialRender, setIsInitialRender] = useState(true)
        // const queryElements = useQuery<Array<PlannedElements>, AxiosError>(['planned-elements-list'], async () => {
        //     return await Promise.all(
        //         elementsListIds.map(async (element) => {
        //             return await getPlannedElementById(element)
        //         }),
        //     )
        // })

        //new2
        const [tableData, setTableData] = useState<{ numberOfElements: number; elementId: string }[]>([
            { numberOfElements: 0, elementId: 'toChange' },
        ])

        // useEffect(() => {
        //     console.log('initial', preparedPlannedData)
        //     setIsInitialRender(false)
        // }, [])

        // useEffect(() => {
        //     // setPlannedData(tableData)
        //     //przekazać funkcję ktora zmienia stan komponentu
        //     // preparedPlannedData = [...tableData]
        //     // console.log('gowno', tableData)
        //     // ref!.current! = tableData
        //     // if (!isInitialRender) {
        //     handleChange(tableData)
        //     // setIsInitialRender(true)
        //     // }
        // }, [tableData])

        const getElementsData = async () => {
            if (!!elementsListIds) {
                const check = await Promise.all(
                    elementsListIds.map(async (element) => {
                        return await getPlannedElementById(element)
                    }),
                )
                if (!!check && check.length > 0) {
                    const filteredData = check.map((element) => {
                        const data = {
                            numberOfElements: element.numberOfElements,
                            elementId: element.element.id.toString(),
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

        // useEffect(() => {
        //     if (!plannedData || plannedData.length === 0) {
        //         setPlannedData([{ numberOfElements: 0, elementId: 'toChange' }])
        //     }
        // }, [])

        // useEffect(()=>{
        //     if (!!tableData) {
        //         const filteredData = tableData!.map((element) => {
        //             const data = { numberOfElements: element.numberOfElements, elementId: element.id.toString() }
        //             return data
        //         })
        //         setPlannedData(filteredData)
        //     }
        // },[])

        useEffect(() => {
            // if (!plannedData) {

            //     return setPlannedData([{ numberOfTools: selectedItemNumber, toolTypeId: selectedItemId }])
            // }
            // if (!!plannedData && plannedData.length === 0) {
            //     return setPlannedData([{ numberOfTools: selectedItemNumber, toolTypeId: selectedItemId }])
            // }
            // if (!!plannedData && !!selectedItemId) {
            //     const tempArray = [...plannedData]
            //     tempArray[tableRowIndex] = { numberOfElements: selectedItemNumber, elementId: selectedItemId }
            //     setPlannedData(tempArray)
            // }
            if (!!tableData && !!selectedItemId) {
                const tempArray = [...tableData]
                tempArray[tableRowIndex] = { numberOfElements: selectedItemNumber, elementId: selectedItemId }
                setTableData(tempArray)
                handleChange(tableData)
            }
        }, [selectedItemId, selectedItemNumber])

        const handleDeleteItem = (rowIndex: number) => {
            const tempArray = [...tableData]
            tempArray.splice(rowIndex, 1)
            setTableData(tempArray)
        }

        return (
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Element</TableCell>
                            <TableCell align="right">Planowana potrzebna ilosc</TableCell>
                            {isDisplayingMode && <TableCell align="right">Wydana ilosć elementy</TableCell>}
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
                                                <InputLabel id="demo-simple-select-label">Element</InputLabel>
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
                                                    selectedItemNumber={rowData.numberOfElements}
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
                                                            { numberOfElements: 0, elementId: 'toChange' },
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
    rowData: { numberOfElements: number; elementId: string }
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
            key={uuidv4()}
            defaultValue=""
            value={rowData.elementId}
            label={label}
            onChange={(event: SelectChangeEvent) => {
                // setDisplaySelectedItemName(event.target.value as string)
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
    const [displayNumber, setDisplayNumber] = useState(0)

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
                setDisplayNumber(event.target.value as any)
            }}
        />
    )
}
