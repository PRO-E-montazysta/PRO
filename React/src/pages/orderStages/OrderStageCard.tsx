import { Grid, Paper, Box, Button, Tabs, Tab, Typography, CardActions } from '@mui/material'
import dayjs, { Dayjs } from 'dayjs'
import { useFormik } from 'formik'
import { useParams } from 'react-router-dom'
import { useEffect, useMemo, useRef, useState } from 'react'
import { DatePicker, TimePicker } from '@mui/x-date-pickers'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { DateValidationError } from '@mui/x-date-pickers/models'
import { OrderStage } from '../../types/model/OrderStage'
import IconButton from '@mui/material/IconButton'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import Card from '@mui/material/Card'
import AssignmentIcon from '@mui/icons-material/Assignment'
import Collapse from '@mui/material/Collapse'
import { ExpandMore, TabPanel, validationSchema } from './helper'
import { getRolesFromToken } from '../../utils/token'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getAllToolTypes, getPlannedToolTypesById } from '../../api/toolType.api'
import { getAllElements, getPlannedElementById } from '../../api/element.api'
import { useAddOrderStage, useUpdateOrderStage } from './hooks'
import { CustomTextField } from '../../components/form/FormInput'
import { v4 as uuidv4 } from 'uuid'
import { ToolType } from '../../types/model/ToolType'
import OrderStageToolsTable from './OrderStageToolsTable'
import OrderStageElementsTable from './OrderStageElementsTable'
import { Role } from '../../types/roleEnum'

type OrderStageCardProps = {
    index?: string
    stage?: OrderStage
    isLoading: boolean
    isDisplaying: boolean
}

const OrderStageCard = ({ index, stage, isLoading, isDisplaying }: OrderStageCardProps) => {
    const [tabValue, setTabValue] = useState(0)
    const [expandedInformation, setExpandedInformation] = useState(false)
    const [plannedStartDate, setPlannedStartDate] = useState<Dayjs | null>(dayjs(stage?.plannedStartDate))
    const [plannedStartHour, setPlannedStartHour] = useState<Dayjs | null>(dayjs(stage?.plannedStartDate))
    const [plannedFinishHour, setPlannedFinishHour] = useState<Dayjs | null>(dayjs(stage?.plannedEndDate))
    const [preparedPlannedStartDate, setPreparedPlannedStartDate] = useState('')
    const [preparedPlannedEndDate, setPreparedPlannedEndDate] = useState('')
    const [userRole, setUserRole] = useState('')
    const addOrderStage = useAddOrderStage()
    const updateOrderStage = useUpdateOrderStage()

    const dummyScrollDiv = useRef<any>(null)
    const params = useParams()
    const plannedElementsRef = useRef<{ numberOfElements: number; elementId: string }[]>([])
    const plannedToolsTypesRef = useRef<{ numberOfTools: number; toolTypeId: string }[]>([])
    const [error, setError] = useState<DateValidationError | null>(null)
    const [isEditing, setIsEditing] = useState(false)
    const [isDisplayingMode, setIsDisplayingMode] = useState(isDisplaying)

    const handleSetPlannedElements = (value: { numberOfElements: number; elementId: string }[]) => {
        plannedElementsRef!.current! = value
    }
    const handleSetPlannedToolsTypes = (value: { numberOfTools: number; toolTypeId: string }[]) => {
        plannedToolsTypesRef!.current! = value
    }

    const queryAllToolTypes = useQuery<Array<ToolType>, AxiosError>(
        ['toolType-list'],
        async () => await getAllToolTypes(),
    )

    const queryAllElements = useQuery<Array<OrderStage>, AxiosError>(
        ['elements-list'],
        async () => await getAllElements(),
    )

    useEffect(() => {
        const role = getRolesFromToken()
        if (role.length !== 0) setUserRole(role[0])
    }, [])

    useEffect(() => {
        if (isDisplayingMode === false) {
            setExpandedInformation(true)
        }
    }, [isDisplayingMode])

    useEffect(() => {
        const date = dayjs(plannedStartDate).format('YYYY-MM-DDTHH:mm:ss.SSS')
        const hours = dayjs(plannedStartHour).format('HH:mm:ss.SSS')
        const preparedDate = date.substring(0, date.indexOf('T') + 1).replace('T', 'T' + hours)
        setPreparedPlannedStartDate(preparedDate)
    }, [plannedStartHour, plannedStartDate])

    useEffect(() => {
        const date = dayjs(plannedStartDate).format('YYYY-MM-DDTHH:mm:ss.SSS')
        const hours = dayjs(plannedFinishHour).format('HH:mm:ss.SSS')
        const preparedDate = date.substring(0, date.indexOf('T') + 1).replace('T', 'T' + hours)
        setPreparedPlannedEndDate(preparedDate)
    }, [plannedFinishHour, plannedStartDate])

    const tabProps = (index: number) => {
        return {
            id: `simple-tab-${index}`,
            'aria-controls': `simple-tabpanel-${index}`,
        }
    }

    const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
        setTabValue(newValue)
    }

    const handleExpandInformationClick = async () => {
        setExpandedInformation(!expandedInformation)
    }

    const handleSetToolsTypeOnEdit = async () => {
        const listOfToolsIds: number[] = stage?.listOfToolsPlannedNumber as any
        const toolsTypeData = await Promise.all(
            listOfToolsIds.map(async (tool) => {
                return await getPlannedToolTypesById(tool)
            }),
        )
        const filteredData = toolsTypeData.map((tool) => {
            const data = {
                numberOfTools: tool.numberOfTools,
                toolTypeId: tool.toolType.id.toString(),
            }
            return data
        })
        handleSetPlannedToolsTypes(filteredData)
    }

    const handleSetElementsOnEdit = async () => {
        const listOfElementsIds: number[] = stage?.listOfElementsPlannedNumber as any
        const elementsData = await Promise.all(
            listOfElementsIds.map(async (element) => {
                return await getPlannedElementById(element)
            }),
        )
        const filteredData = elementsData.map((element) => {
            const data = {
                numberOfElements: element.numberOfElements,
                elementId: element.element.id.toString(),
            }
            return data
        })
        handleSetPlannedElements(filteredData)
    }

    const handleEditButtonAction = () => {
        setIsEditing(true)
        setIsDisplayingMode(false)
        handleSetToolsTypeOnEdit()
        handleSetElementsOnEdit()
    }

    const formik = useFormik({
        initialValues: {
            orderId: params.id!,
            orderStageId: !!stage && stage.id ? stage!.id.toString() : '',
            name: isDisplayingMode ? stage!.name : '',
            status: isDisplayingMode ? stage!.status : null,
            price: isDisplayingMode ? stage!.price : '',
            plannedStartDate: isDisplayingMode ? stage!.plannedStartDate : '',
            plannedEndDate: isDisplayingMode ? stage!.plannedEndDate : '',
            plannedFittersNumber: isDisplayingMode ? stage!.plannedFittersNumber : '',
            minimumImagesNumber: isDisplayingMode ? stage!.minimumImagesNumber : '0',
            listOfToolsPlannedNumber: isDisplayingMode ? stage!.listOfToolsPlannedNumber : [],
            listOfElementsPlannedNumber: isDisplayingMode ? stage!.listOfElementsPlannedNumber : [],
            attachments: [],
            test: '',
        },

        validationSchema: validationSchema,
        onSubmit: (values) => {
            values.listOfElementsPlannedNumber = plannedElementsRef.current!
            values.listOfToolsPlannedNumber = plannedToolsTypesRef.current!
            values.plannedStartDate = preparedPlannedStartDate
            values.plannedEndDate = preparedPlannedEndDate

            if (isEditing) {
                return updateOrderStage.mutate(values)
            }
            addOrderStage.mutate(values)
        },
    })

    const errorMessage = useMemo(() => {
        switch (error) {
            case 'maxDate':
            case 'minDate': {
                return 'Please select a date in the first quarter of 2022'
            }

            case 'invalidDate': {
                return 'Wprowadź poprawną datę'
            }

            default: {
                return ''
            }
        }
    }, [error])

    const getDateInformations = (stage?: OrderStage) => {
        return (
            <Grid container spacing={{ xs: 2, md: 2 }} columns={{ xs: 2, sm: 4, md: 12 }}>
                <Grid item xs={6}>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DatePicker
                            sx={{
                                width: '100%',
                                '& .MuiInputBase-input.Mui-disabled': {
                                    WebkitTextFillColor: '#000000',
                                },
                            }}
                            label="Planowana data rozpoczęcia"
                            disabled={isDisplayingMode}
                            format="DD/MM/YYYY"
                            value={plannedStartDate}
                            onChange={(data) => {
                                setError(null)
                                setPlannedStartDate(data)
                            }}
                            onError={(error) => setError(error)}
                            slotProps={{
                                textField: {
                                    helperText: errorMessage || '',
                                },
                            }}
                            minDate={dayjs('2022-01-01T00:00:00.000')}
                            maxDate={dayjs('2023-06-01T00:00:00.000')}
                        />
                    </LocalizationProvider>
                </Grid>

                {isDisplayingMode ? (
                    <Grid item xs={6}>
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DatePicker
                                sx={{
                                    width: '100%',
                                    '& .MuiInputBase-input.Mui-disabled': {
                                        WebkitTextFillColor: '#000000',
                                    },
                                }}
                                format="DD/MM/YYYY"
                                label="Data rozpoczęcia"
                                value={isDisplayingMode ? dayjs(stage!.startDate) : ''}
                                disabled={isDisplayingMode}
                            />
                        </LocalizationProvider>
                    </Grid>
                ) : null}
                <Grid item xs={6}>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <TimePicker
                            sx={{
                                width: '100%',
                                '& .MuiInputBase-input.Mui-disabled': {
                                    WebkitTextFillColor: '#000000',
                                },
                            }}
                            label="Planowana godzina rozpoczęcia"
                            format={'HH:mm:ss'}
                            ampm={false}
                            value={plannedStartHour}
                            disabled={isDisplayingMode}
                            onChange={(data) => {
                                setPlannedStartHour(data)
                            }}
                        />
                    </LocalizationProvider>
                </Grid>

                {isDisplayingMode ? (
                    <Grid item xs={6}>
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DatePicker
                                sx={{
                                    width: '100%',
                                    '& .MuiInputBase-input.Mui-disabled': {
                                        WebkitTextFillColor: '#000000',
                                    },
                                }}
                                label="Data zakończenia"
                                disabled={isDisplayingMode}
                                format="DD/MM/YYYY"
                                value={isDisplayingMode ? dayjs(stage!.endDate) : ''}
                                onChange={(data) => {
                                    // const formattedDate = dayjs(data).format('YYYY-MM-DDTHH:mm:ss.SSS')
                                }}
                            />
                        </LocalizationProvider>
                    </Grid>
                ) : null}
                <Grid item xs={6}>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <TimePicker
                            sx={{
                                width: '100%',
                                '& .MuiInputBase-input.Mui-disabled': {
                                    WebkitTextFillColor: '#000000',
                                },
                            }}
                            disabled={isDisplayingMode}
                            label="Planowana godzina zakończenia"
                            ampm={false}
                            format={'HH:mm:ss'}
                            value={plannedFinishHour}
                            onChange={(data) => {
                                setPlannedFinishHour(data)
                            }}
                        />
                    </LocalizationProvider>
                </Grid>
            </Grid>
        )
    }

    return (
        <Card id={index ? index.toString() : ''} sx={{ margin: 'auto', marginTop: '20px', border: '1px solid' }}>
            <CardActions disableSpacing>
                <IconButton aria-label="share">
                    <AssignmentIcon />
                </IconButton>
                <Typography variant="h5" color="text.secondary">
                    {stage ? <Typography>Etap {stage.name}</Typography> : <Typography>Dodaj etap</Typography>}
                </Typography>
                <ExpandMore
                    expand={expandedInformation}
                    onClick={handleExpandInformationClick}
                    aria-expanded={expandedInformation}
                    aria-label="show more"
                >
                    <ExpandMoreIcon />
                </ExpandMore>
            </CardActions>
            <Collapse in={expandedInformation} timeout="auto" unmountOnExit>
                <Paper sx={{ p: '10px' }}>
                    <Grid
                        component="form"
                        onSubmit={formik.handleSubmit}
                        container
                        spacing={{ xs: 2, md: 2 }}
                        columns={{ xs: 2, sm: 4, md: 12 }}
                    >
                        <Grid item xs={4} md={3}>
                            <CustomTextField
                                readOnly={isDisplayingMode!}
                                disabled={isDisplayingMode}
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                variant="outlined"
                                label="Nazwa etapu"
                                name="name"
                                value={formik.values.name}
                                onChange={formik.handleChange}
                                error={formik.touched.name && Boolean(formik.errors.name)}
                                helperText={formik.touched.name && formik.errors.name}
                            />
                        </Grid>
                        {isDisplayingMode ? (
                            <Grid item xs={4} md={3}>
                                <CustomTextField
                                    readOnly={isDisplayingMode!}
                                    disabled={isDisplayingMode}
                                    sx={{ width: '100%' }}
                                    id="standard-basic"
                                    variant="outlined"
                                    label="Status"
                                    name="status"
                                    defaultValue={isDisplayingMode ? stage!.status : null}
                                />
                            </Grid>
                        ) : null}
                        <Grid item xs={4} md={3}>
                            <CustomTextField
                                readOnly={isDisplayingMode!}
                                disabled={isDisplayingMode}
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                variant="outlined"
                                label="Cena"
                                name="price"
                                value={formik.values.price}
                                onChange={formik.handleChange}
                                error={formik.touched.price && Boolean(formik.errors.price)}
                                helperText={formik.touched.price && formik.errors.price}
                            />
                        </Grid>

                        <Grid item xs={4} md={3}>
                            <CustomTextField
                                readOnly={isDisplayingMode!}
                                disabled={isDisplayingMode}
                                sx={{
                                    width: '100%',
                                }}
                                id="standard-basic"
                                variant="outlined"
                                label="Planowana liczba montażystów"
                                name="plannedFittersNumber"
                                value={formik.values.plannedFittersNumber}
                                onChange={formik.handleChange}
                                error={
                                    formik.touched.plannedFittersNumber && Boolean(formik.errors.plannedFittersNumber)
                                }
                                helperText={formik.touched.plannedFittersNumber && formik.errors.plannedFittersNumber}
                            />
                        </Grid>
                        <Grid item xs={4} md={3}>
                            <CustomTextField
                                readOnly={isDisplayingMode!}
                                disabled={isDisplayingMode}
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                label="Minimalna liczba zdjęć"
                                variant="outlined"
                                name="minimumImagesNumber"
                                value={formik.values.minimumImagesNumber}
                                onChange={formik.handleChange}
                                error={formik.touched.minimumImagesNumber && Boolean(formik.errors.minimumImagesNumber)}
                                helperText={formik.touched.minimumImagesNumber && formik.errors.minimumImagesNumber}
                            />
                        </Grid>
                        <Box sx={{ marginTop: '20px', width: '100%' }}>
                            <Box
                                sx={{
                                    borderBottom: 1,
                                    borderColor: 'divider',
                                    display: 'flex',
                                    justifyContent: 'space-around',
                                }}
                            >
                                <Tabs
                                    component="div"
                                    value={tabValue}
                                    onChange={handleTabChange}
                                    aria-label="basic tabs example"
                                >
                                    <Tab label="Informacje o datach" {...tabProps(0)} />
                                    <Tab label="Narzędzia" {...tabProps(1)} />
                                    <Tab label="Elementy" {...tabProps(2)} />
                                    {isDisplayingMode ? <Tab label="Montażyści..." {...tabProps(2)} /> : null}
                                    <Tab label="Szczegóły etapu/Zalaczniki" {...tabProps(1)} />
                                </Tabs>
                            </Box>
                            <TabPanel key={uuidv4()} value={tabValue} index={0}>
                                {getDateInformations(stage)}
                            </TabPanel>
                            <TabPanel key={uuidv4()} value={tabValue} index={1}>
                                <OrderStageToolsTable
                                    itemsArray={queryAllToolTypes.data!}
                                    isDisplayingMode={isDisplayingMode!}
                                    toolsTypeListIds={stage?.listOfToolsPlannedNumber as any}
                                    handleChange={handleSetPlannedToolsTypes}
                                    toolsRef={plannedToolsTypesRef}
                                />
                            </TabPanel>
                            <TabPanel key={uuidv4()} value={tabValue} index={2}>
                                <OrderStageElementsTable
                                    itemsArray={queryAllElements.data!}
                                    isDisplayingMode={isDisplayingMode!}
                                    elementsListIds={stage?.listOfElementsPlannedNumber as any}
                                    handleChange={handleSetPlannedElements}
                                    elementsRef={plannedElementsRef}
                                />
                            </TabPanel>

                            <TabPanel key={uuidv4()} value={tabValue} index={3}>
                                <Typography>Zalaczniki...</Typography>
                            </TabPanel>
                            <TabPanel key={uuidv4()} value={tabValue} index={4}>
                                <Grid item xs={2}>
                                    <CustomTextField
                                        readOnly={isDisplayingMode!}
                                        sx={{ width: '100%' }}
                                        label="Załączniki"
                                        name="attachments"
                                    />
                                </Grid>
                            </TabPanel>
                        </Box>

                        <Grid spacing={2} container justifyContent="flex-end" sx={{ marginTop: '20px' }}>
                            <Grid item>
                                {isDisplayingMode && userRole === Role.SPECIALIST && (
                                    <Button
                                        color="primary"
                                        variant="contained"
                                        disabled={isLoading}
                                        onClick={handleEditButtonAction}
                                    >
                                        Edytuj etap
                                    </Button>
                                )}
                                {(!isDisplayingMode || isEditing) && (
                                    <Button type="submit" color="primary" variant="contained" disabled={isLoading}>
                                        Zapisz etap
                                    </Button>
                                )}
                                <div ref={dummyScrollDiv} />
                            </Grid>
                        </Grid>
                    </Grid>
                </Paper>
            </Collapse>
        </Card>
    )
}

export default OrderStageCard
