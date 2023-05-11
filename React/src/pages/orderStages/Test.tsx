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
import { OrderStage } from '../../types/model/OrderStage'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { PlannedToolType } from '../../types/model/ToolType'
import { number } from 'yup'
import { useEffect, useState } from 'react'
import { PlannedElements } from '../../types/model/Element'
import { getPlannedElementById } from '../../api/element.api'
import { v4 as uuidv4 } from 'uuid'

type OrderStageDetailsElementsTableType = {
    itemsArray: Array<any>
    plannedData: Array<{ numberOfElements: number; elementId: string }>
    setPlannedData: React.Dispatch<
        React.SetStateAction<
            | {
                  numberOfElements: number
                  elementId: string
              }[]
            | undefined
        >
    >
    isDisplayingMode: boolean
    elementsListIds: Array<number> | []
}

//PRZEBUDOWAĆ TO TAK ZEBY GUZIK DODAJ ELEMENT ROBIL SETPLANNEDDATA. OD POCZATKU ZROBIC TABELE BAZUJAC NA TYCH ELEMENTACH

const TestE = ({
    itemsArray,
    setPlannedData,
    plannedData,
    isDisplayingMode,
    elementsListIds,
}: OrderStageDetailsElementsTableType) => {
    const [tableRowIndex, setTableRowIndex] = useState(0)
    const [selectedItemId, setSelectedItemId] = useState('')
    const [selectedItemNumber, setSelectedItemNumber] = useState(0)
    //nowe:
    const [tableData, setTableData] = useState<{ numberOfElements: number; elementId: string }[]>([
        { numberOfElements: 0, elementId: 'toChange' },
    ])

    // const [stageElementsData, setStageElementsData] = useState()

    useEffect(() => {
        // console.log('idki ktore musze pobrac', elementsListIds)
        // console.log('dane tabeli', tableData)
        getElementsData()
    }, [])

    useEffect(() => {
        // console.log('idki ktore musze pobrac', elementsListIds)
        console.log('dane tabeli', tableData)
    }, [tableData])

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
                setTableData(filteredData)
            }
        }
    }

    //1. Wyświetlmy tabelę elementów, tak żeby widać działało dla każdego OrderStage
    //Zobaczmy co przychodzi do nas jako plannedData - nic puste - to jest do formika
    //w elementsListIds przychodza do pobrania elementy
    //2. To teraz stwórzmy tabele, która będzie pokazywać konkretne elementy. Musi mieć ona długość queryElements.data
    //i pozniej trzeba ja bedzie moc zwiekszac/zmniejszac przy dodawaniu. Zrobię tu useState, ktory sie ustawi na dlugość
    //queryELements
    //3. To queryElements bylo problemem,

    // <Typography component="span" variant="h3">
    //     Hello
    // </Typography>

    const handleDeleteItem = (rowIndex: number) => {
        const tempArray = [...tableData]
        tempArray.splice(rowIndex, 1)
        setTableData(tempArray)
    }

    return (
        <Paper component="span">
            <TableContainer key={uuidv4()} component={Paper}>
                <Table key={uuidv4()} sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Element</TableCell>
                            <TableCell align="right">Planowana potrzebna ilosc</TableCell>
                            {isDisplayingMode && <TableCell align="right">Wydana ilosć elementy</TableCell>}
                            {!isDisplayingMode && <TableCell align="right">Akcja</TableCell>}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {tableData.map((elementData, rowIndex) => {
                            return (
                                <TableRow key={rowIndex} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                                    <TableCell component="th" scope="row">
                                        <Box sx={{ minWidth: 120 }}>
                                            <FormControl fullWidth>
                                                <InputLabel id={uuidv4()}>Element</InputLabel>
                                                {/* <Typography component="span" variant="h3">
                                                    Hello
                                                </Typography> */}
                                                <TableItemSelect
                                                    label={'Element'}
                                                    elementDataName={elementData.elementId}
                                                    itemsArray={itemsArray}
                                                    setSelectedItemId={setSelectedItemId}
                                                    selectedItemId={selectedItemId}
                                                />
                                            </FormControl>
                                        </Box>
                                    </TableCell>
                                    <TableCell align="right">
                                        <Box sx={{ minWidth: 120 }}>
                                            <FormControl fullWidth>
                                                <TableItemNumber
                                                    setSelectedItemNumber={setSelectedItemNumber}
                                                    selectedItemNumber={elementData.numberOfElements}
                                                    // setTableRowIndex={setTableRowIndex}
                                                    // handleItemNumberChange={handleItemNumberChange}
                                                    // rowIndex={rowIndex}
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
                                                    Dodaj element
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
                            )
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
        </Paper>
    )
}

export default TestE

// const chooseSelectItemId = (
//     defaultValue: string,
//     itemsArray: Array<any> | undefined,
//     setSelectedItemId: React.Dispatch<React.SetStateAction<string>>,
// ) => {
//     if (itemsArray) {
//         return itemsArray.map((item: any) => (
//             <MenuItem
//                 key={uuidv4()}
//                 value={item.id}
//                 onClick={() => {
//                     setSelectedItemId(item.id)
//                 }}
//             >
//                 {item.name}
//             </MenuItem>
//         ))
//     }
//     return <MenuItem>Brak typow narzedzi</MenuItem>
// }

type TableItemSelectTypes = {
    label: string
    itemsArray: Array<any>
    elementDataName: string
    // rowIndex: number
    // setTableRowIndex: React.Dispatch<React.SetStateAction<number>>
    setSelectedItemId: React.Dispatch<React.SetStateAction<string>>
    selectedItemId: string
}

const TableItemSelect = ({
    label,
    elementDataName,
    itemsArray,
    setSelectedItemId,
    selectedItemId,
}: TableItemSelectTypes) => {
    const [displayedSelectedItemName, setDisplaySelectedItemName] = useState()

    useEffect(() => {
        if(!!displayedSelectedItemName){
        setSelectedItemId(displayedSelectedItemName)
        }
    }, [displayedSelectedItemName])

    const handleChange = (event: any) => {
        console.log('event', event.target.value)
        setDisplaySelectedItemName(event.target.value)
    }
    return (
        // <Select
        //     labelId="demo-simple-select-label"
        //     id={uuidv4()}
        //     value={elementData.elementId}
        //     label={label}
        //     onChange={(event: SelectChangeEvent) => {
        //         // setDisplaySelectedItemName(event.target.value as string)
        //         setTableRowIndex(rowIndex)
        //     }}
        // >
        //     {chooseSelectItemId(elementData.elementId, itemsArray, setSelectedItemId)}
        // </Select>
        <Select key={uuidv4()} value={displayedSelectedItemName} label={label} onChange={handleChange}>
            {!!itemsArray &&
                itemsArray.map((item) => {
                    return (
                        <MenuItem key={uuidv4()} value={item.id}>
                            {item.name}
                        </MenuItem>
                    )
                })}
        </Select>
    )
}

//number
type TableItemNumberType = {
    selectedItemNumber: number
    setSelectedItemNumber: React.Dispatch<React.SetStateAction<number>>
    // handleItemNumberChange: (event: any) => void
    // rowIndex: number
}

const TableItemNumber = ({
    selectedItemNumber,
    setSelectedItemNumber,
}: // setTableRowIndex,
// handleItemNumberChange,
// rowIndex,
TableItemNumberType) => {
    const [displayNumber, setDisplayNumber] = useState(selectedItemNumber)

    useEffect(() => {
        setSelectedItemNumber(displayNumber)
    }, [displayNumber])

    const handleChange = (event: any) => {
        setDisplayNumber(Number(event.target.value))
    }
    return (
        <TextField
            type="number"
            value={displayNumber}
            id={uuidv4()}
            label="Ilość"
            variant="outlined"
            onChange={(event) => {
                handleChange(event)
            }}
            // setTableRowIndex(rowIndex)
            // handleItemNumberChange(event)
            //     console.log(event.target.value)
            //     // setDisplayNumber(event.target.value as any)
            //     setSelectedItemNumber(Number(event.target.value))
            // }}
        />
    )
}
