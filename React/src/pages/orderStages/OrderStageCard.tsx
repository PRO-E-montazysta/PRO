import { Grid, Paper, Box, Button, Tabs, Tab, CircularProgress, Typography, CardActions, styled } from '@mui/material'
import TextField from '@mui/material/TextField'
import dayjs, { Dayjs } from 'dayjs'
import * as yup from 'yup'
import { useFormik } from 'formik'
import { useNavigate, useParams } from 'react-router-dom'
import { MouseEvent, useCallback, useEffect, useRef, useState } from 'react'
import { DatePicker, TimePicker } from '@mui/x-date-pickers'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { OrderStage } from '../../types/model/OrderStage'
import IconButton, { IconButtonProps } from '@mui/material/IconButton'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import Card from '@mui/material/Card'
import AssignmentIcon from '@mui/icons-material/Assignment'
import Collapse from '@mui/material/Collapse'
import { ExpandMore, TabPanel } from './helper'
import { getRolesFromToken } from '../../utils/token'
import { Role } from '../../types/roleEnum'
import { useMutation, useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getAllToolTypes } from '../../api/toolType.api'
import { getAllElements } from '../../api/element.api'
import OrderStageToolTypesTable from './OrderStageToolTypesTable'
import OrderStageDetailsElementsTable from './ElementsTable'
import { useAddOrderStage } from './hooks'
import { CustomTextField } from '../../components/form/FormInput'
import TestE from './Test'
import { v4 as uuidv4 } from 'uuid'
import TestTestTest from './TestTestTest'

type OrderStageCardProps = {
    index?: string
    stage?: OrderStage
    isLoading: boolean
    isDisplayingMode?: boolean
}

const OrderStageCard = ({ index, stage, isLoading, isDisplayingMode }: OrderStageCardProps) => {
    const [tabValue, setTabValue] = useState(0)
    const [expandedInformation, setExpandedInformation] = useState(false)
    const [plannedStartDate, setPlannedStartDate] = useState<Dayjs | null>(dayjs(stage?.plannedStartDate))
    const [plannedStartHour, setPlannedStartHour] = useState<Dayjs | null>(dayjs(stage?.plannedStartDate))
    const [plannedFinishHour, setPlannedFinishHour] = useState<Dayjs | null>(dayjs(stage?.plannedEndDate))
    const [preparedPlannedStartDate, setPreparedPlannedStartDate] = useState('')
    const [preparedPlannedEndDate, setPreparedPlannedEndDate] = useState('')
    const [userRole, setUserRole] = useState('')
    const addOrderStage = useAddOrderStage()
    let preparedPlannedData: { numberOfElements: number; elementId: string }[] = []

    const [plannedToolTypes, setPlannedToolTypes] = useState<{ numberOfTools: number; toolTypeId: string }[]>()
    const [plannedElements, setPlannedElements] = useState<{ numberOfElements: number; elementId: string }[]>()

    const dummyScrollDiv = useRef<any>(null)

    const params = useParams()

    //new
    const ref = useRef<{ numberOfElements: number; elementId: string }[]>([])
    const handleChange = (value: { numberOfElements: number; elementId: string }[]) => {
        // console.log('here', ref.current)
        ref!.current! = value
        console.log('here2', ref.current)
    }

    const queryToolTypes = useQuery<Array<OrderStage>, AxiosError>(
        ['toolType-list'],
        async () => await getAllToolTypes(),
    )

    const queryElements = useQuery<Array<OrderStage>, AxiosError>(['elements-list'], async () => await getAllElements())

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
    }, [plannedStartHour])

    useEffect(() => {
        const date = dayjs(plannedStartDate).format('YYYY-MM-DDTHH:mm:ss.SSS')
        const hours = dayjs(plannedFinishHour).format('HH:mm:ss.SSS')
        const preparedDate = date.substring(0, date.indexOf('T') + 1).replace('T', 'T' + hours)
        setPreparedPlannedEndDate(preparedDate)
    }, [plannedFinishHour])

    const tabProps = (index: number) => {
        return {
            id: `simple-tab-${index}`,
            'aria-controls': `simple-tabpanel-${index}`,
        }
    }

    const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
        setTabValue(newValue)
    }

    const handleExpandInformationClick = () => {
        setExpandedInformation(!expandedInformation)
    }

    const formik = useFormik({
        initialValues: {
            orderId: params.id!,
            name: isDisplayingMode ? stage!.name : '',
            status: isDisplayingMode ? stage!.status : null,
            price: isDisplayingMode ? stage!.price : '',
            plannedStartDate: isDisplayingMode ? '2023-04-08' : '',
            plannedEndDate: isDisplayingMode ? stage!.plannedEndDate : '',
            plannedFittersNumber: isDisplayingMode ? stage!.plannedFittersNumber : '',
            minimumImagesNumber: isDisplayingMode ? stage!.minimumImagesNumber : '0',
            listOfToolsPlannedNumber: isDisplayingMode ? stage!.listOfToolsPlannedNumber : [],
            listOfElementsPlannedNumber: isDisplayingMode ? stage!.listOfElementsPlannedNumber : [],
            attachments: [],
            test: '',
        },

        // validationSchema: validationSchema,
        onSubmit: async (values) => {
            console.log('co jest', ref.current)
            values.listOfElementsPlannedNumber = ref.current!
            values.listOfToolsPlannedNumber = plannedToolTypes!
            values.plannedStartDate = preparedPlannedStartDate
            values.plannedEndDate = preparedPlannedEndDate

            await addOrderStage.mutate(values)
        },
    })
    const getDateInformations = (stage?: OrderStage) => {
        return (
            <Grid container spacing={{ xs: 2, md: 2 }} columns={{ xs: 2, sm: 4, md: 12 }}>
                <Grid item xs={6}>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DatePicker
                            sx={{ width: '100%' }}
                            label="Planowana data rozpoczęcia"
                            value={plannedStartDate}
                            onChange={(data) => {
                                // const formattedDate = dayjs(data!).format('YYYY-MM-DDTHH:mm:ss.SSS')
                                setPlannedStartDate(data)
                            }}
                        />
                    </LocalizationProvider>
                </Grid>

                {isDisplayingMode ? (
                    <Grid item xs={6}>
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DatePicker
                                sx={{ width: '100%' }}
                                label="Data rozpoczęcia"
                                value={isDisplayingMode ? stage!.startDate : ''}
                            />
                        </LocalizationProvider>
                    </Grid>
                ) : null}
                <Grid item xs={6}>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <TimePicker
                            sx={{ width: '100%' }}
                            label="Planowana godzina rozpoczęcia"
                            format={'HH:mm:ss'}
                            value={plannedStartHour}
                            onChange={(data) => {
                                // const formattedDate = dayjs(data).format('YYYY-MM-DDTHH:mm:ss.SSS')
                                setPlannedStartHour(data)
                            }}
                        />
                    </LocalizationProvider>
                </Grid>

                {isDisplayingMode ? (
                    <Grid item xs={6}>
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DatePicker
                                sx={{ width: '100%' }}
                                label="Data zakończenia"
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
                            sx={{ width: '100%' }}
                            label="Planowana godzina zakończenia"
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
                    {stage ? <Typography>Etap stage.order zlecenia</Typography> : <Typography>Dodaj etap</Typography>}
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
                        <Grid item xs={3}>
                            <CustomTextField
                                readOnly={isDisplayingMode!}
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                variant="outlined"
                                label="Nazwaaaaaa"
                                name="name"
                                value={formik.values.name}
                                onChange={formik.handleChange}
                                error={formik.touched.name && Boolean(formik.errors.name)}
                                helperText={formik.touched.name && formik.errors.name}
                            />
                        </Grid>
                        {isDisplayingMode ? (
                            <Grid item xs={3}>
                                <CustomTextField
                                    readOnly={isDisplayingMode!}
                                    sx={{ width: '100%' }}
                                    id="standard-basic"
                                    variant="outlined"
                                    label="Status"
                                    name="status"
                                    defaultValue={isDisplayingMode ? stage!.status : null}
                                />
                            </Grid>
                        ) : null}
                        <Grid item xs={3}>
                            <CustomTextField
                                readOnly={isDisplayingMode!}
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                variant="outlined"
                                label="Cena"
                                name="price"
                                // defaultValue={isDisplayingMode ? stage!.price : null}
                                value={formik.values.price}
                                onChange={formik.handleChange}
                                error={formik.touched.price && Boolean(formik.errors.price)}
                                helperText={formik.touched.price && formik.errors.price}
                            />
                        </Grid>

                        <Grid item xs={4}>
                            <CustomTextField
                                readOnly={isDisplayingMode!}
                                // defaultValue={isDisplayingMode ? stage!.plannedFittersNumber : null}
                                sx={{ width: '100%' }}
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
                        <Grid item xs={4}>
                            <CustomTextField
                                readOnly={isDisplayingMode!}
                                id="standard-basic"
                                label="Minimalna liczba zdjęć"
                                variant="outlined"
                                // defaultValue={isDisplayingMode ? stage!.minimumImagesNumber : null}
                                name="minimumImagesNumber"
                                value={formik.values.minimumImagesNumber}
                                onChange={formik.handleChange}
                                error={formik.touched.minimumImagesNumber && Boolean(formik.errors.minimumImagesNumber)}
                                helperText={formik.touched.minimumImagesNumber && formik.errors.minimumImagesNumber}
                            />
                        </Grid>
                        {isDisplayingMode || (!isDisplayingMode && userRole === Role.MANAGER) ? (
                            <Grid item xs={4}>
                                <CustomTextField
                                    readOnly={isDisplayingMode!}
                                    id="standard-basic"
                                    label="Brygadzista"
                                    variant="outlined"
                                    defaultValue={isDisplayingMode ? stage!.foremanId : null}
                                />
                            </Grid>
                        ) : null}
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
                                    <Tab label="Planowane narzędzia" {...tabProps(1)} />
                                    <Tab label="Elementy" {...tabProps(2)} />
                                    {isDisplayingMode ? <Tab label="Montażyści..." {...tabProps(2)} /> : null}
                                    <Tab label="Szczegóły etapu/Zalaczniki" {...tabProps(1)} />
                                </Tabs>
                            </Box>
                            <TabPanel key={uuidv4()} value={tabValue} index={0}>
                                {getDateInformations(stage)}
                            </TabPanel>
                            <TabPanel key={uuidv4()} value={tabValue} index={1}>
                                <OrderStageToolTypesTable
                                    itemsArray={queryToolTypes.data}
                                    plannedData={plannedToolTypes!}
                                    setPlannedData={setPlannedToolTypes}
                                    isDisplayingMode={isDisplayingMode!}
                                    toolTypesListIds={stage?.listOfToolsPlannedNumber as any}
                                />
                            </TabPanel>
                            <TabPanel key={uuidv4()} value={tabValue} index={2}>
                                {/* <Paper component="div">
                                    <Typography component="span">Zalaczniki.2..</Typography>
                                </Paper> */}
                                {/* <OrderStageDetailsElementsTable
                                       itemsArray={queryToolTypes.data}
                                       plannedData={plannedElements!}
                                       setPlannedData={setPlannedElements}
                                       isDisplayingMode={isDisplayingMode!}
                                       elementsListIds={stage?.listOfElementsPlannedNumber as any}
                                    /> */}
                                <TestTestTest
                                    itemsArray={queryElements.data!}
                                    // plannedData={plannedElements!}
                                    // setPlannedData={setPlannedElements}
                                    isDisplayingMode={isDisplayingMode!}
                                    elementsListIds={stage?.listOfElementsPlannedNumber as any}
                                    handleChange={handleChange}
                                    refValue={ref}
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
                                <Button type="submit" color="primary" variant="contained" disabled={isLoading}>
                                    {isDisplayingMode ? 'Edytuj etap' : 'Zapisz etap'}
                                </Button>
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
