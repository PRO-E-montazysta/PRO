import { Grid, Paper, Box, Button, Tabs, Tab, CircularProgress, Typography, CardActions } from '@mui/material'
import TextField from '@mui/material/TextField'
import dayjs, { Dayjs } from 'dayjs'
import * as yup from 'yup'
import { useFormik } from 'formik'
import { useNavigate, useParams } from 'react-router-dom'
import { MouseEvent, useEffect, useRef, useState } from 'react'
import { DatePicker, TimePicker } from '@mui/x-date-pickers'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { OrderStage } from '../../types/model/OrderStage'
import IconButton, { IconButtonProps } from '@mui/material/IconButton'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import Card from '@mui/material/Card'
import AssignmentIcon from '@mui/icons-material/Assignment'
import Collapse from '@mui/material/Collapse'
import OrderStageDetailsTable, { ExpandMore, TabPanel } from './helper'
import { getRolesFromToken } from '../../utils/token'
import { Role } from '../../types/roleEnum'
import { useMutation, useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getAllToolTypes } from '../../api/toolType.api'

type OrderStageCardProps = {
    index?: string
    stage?: OrderStage
    isLoading: boolean
    isDisplayingMode?: boolean
}

const OrderStageCard = ({ index, stage, isLoading, isDisplayingMode }: OrderStageCardProps) => {
    const [tabValue, setTabValue] = useState(0)
    const [expandedInformation, setExpandedInformation] = useState(false)
    const [plannedStartDate, setPlannedStartDate] = useState<Dayjs | null>(null)
    const [plannedStartHour, setPlannedStartHour] = useState<Dayjs | null>(dayjs('14:30'))
    const [plannedFinishHour, setPlannedFinishHour] = useState<Dayjs | null>(dayjs('15:30'))
    const [preparedPlannedStartDate, setPreparedPlannedStartDate] = useState('')
    const [preparedPlannedEndDate, setPreparedPlannedEndDate] = useState('')
    const [userRole, setUserRole] = useState('')

    const [plannedToolType, setPlannedToolType] = useState<{ numberOfTools: number; toolTypeId: string }[]>()
    const [plannedElement, setPlannedElement] = useState<[{ numberOfElements: number; elementId: string }]>()

    const dummyScrollDiv = useRef<any>(null)

    const params = useParams()

    const queryToolTypes = useQuery<Array<OrderStage>, AxiosError>(
        ['toolType-list'],
        async () => await getAllToolTypes(),
    )

    useEffect(() => {
        console.log('dzialaj', plannedToolType)
    }, [plannedToolType])

    useEffect(() => {
        console.log('lalala', queryToolTypes)
    }, [queryToolTypes])

    useEffect(() => {
        console.log(queryToolTypes)
        const role = getRolesFromToken()
        if (role.length !== 0) setUserRole(role[0])
    }, [])

    useEffect(() => {
        if (isDisplayingMode === false) {
            setExpandedInformation(true)
        }
    }, [isDisplayingMode])

    useEffect(() => {
        console.log(isDisplayingMode)
        const date = dayjs(plannedStartDate).format('YYYY-MM-DDTHH:mm:ss.SSS')
        const hours = dayjs(plannedStartHour).format('HH:mm:ss.SSS')
        const preparedDate = date.substring(0, date.indexOf('T') + 1).replace('T', 'T' + hours)
        console.log('xd', preparedDate)
        setPreparedPlannedStartDate(preparedDate)
    }, [plannedStartHour])

    useEffect(() => {
        const date = dayjs(plannedStartDate).format('YYYY-MM-DDTHH:mm:ss.SSS')
        const hours = dayjs(plannedFinishHour).format('HH:mm:ss.SSS')
        const preparedDate = date.substring(0, date.indexOf('T') + 1).replace('T', 'T' + hours)
        console.log('xd', preparedDate)
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
            id: params.id,
            name: '',
            status: 'TODO',
            price: '',
            plannedStartDate: preparedPlannedStartDate,
            plannedEndDate: preparedPlannedEndDate,
            // startDate: '',
            // endDate: '',
            // plannedDurationTime: '', //jak chcemy to podawać?
            plannedFittersNumber: '',
            minimumImagesNumber: '',
            // fitters: '',
            foremanId: '',
            comments: '',
            // tools: '',
            listOfToolsPlannedNumber: plannedToolType,
            listOfElementsPlannedNumber: plannedElement,
            elements: '',
            attachments: '',
            test: '',
        },
        // validationSchema: validationSchema,
        onSubmit: (values) => {
            console.log(values)
            //   return mutate({ username: values.email, password: values.password });
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
                                console.log('kurwa', data)
                                // const formattedDate = dayjs(data).format('YYYY-MM-DDTHH:mm:ss.SSS')
                                // setPlannedStartDate(data)
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
                                    // setPlannedStartDate(data)
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
                                // const formattedDate = dayjs(data).format('YYYY-MM-DDTHH:mm:ss.SSS')
                                // setPlannedStartDate(data)
                                setPlannedFinishHour(data)
                            }}
                        />
                    </LocalizationProvider>
                </Grid>
            </Grid>
        )
    }

    // const getStageDetails = () => {
    //     return (
    //         <>

    //         </>
    //     )
    // }

    return (
        <Card id={index ? index.toString() : ''} sx={{ margin: 'auto', marginTop: '20px', border: '1px solid' }}>
            <CardActions disableSpacing>
                <IconButton aria-label="share">
                    <AssignmentIcon />
                </IconButton>
                <Typography variant="h5" color="text.secondary">
                    {stage ? `Etap ${stage.order} zlecenia` : `Dodaj etap`}
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
                        // onSubmit={formik.handleSubmit}
                        container
                        spacing={{ xs: 2, md: 2 }}
                        columns={{ xs: 2, sm: 4, md: 12 }}
                    >
                        <Grid item xs={3}>
                            <TextField
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                variant="standard"
                                label="Nazwa"
                                name="name"
                                defaultValue={isDisplayingMode ? stage!.name : null}
                            />
                        </Grid>
                        {isDisplayingMode ? (
                            <Grid item xs={3}>
                                <TextField
                                    sx={{ width: '100%' }}
                                    id="standard-basic"
                                    variant="standard"
                                    label="Status"
                                    name="status"
                                    defaultValue={isDisplayingMode ? stage!.status : null}
                                />
                            </Grid>
                        ) : null}
                        <Grid item xs={3}>
                            <TextField
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                variant="standard"
                                label="Cena"
                                name="price"
                                defaultValue={isDisplayingMode ? stage!.price : null}
                            />
                        </Grid>
                        <Grid item xs={3}>
                            <TextField
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                variant="standard"
                                label="Planowany czas trwania"
                                name="plannedDurationTime"
                                defaultValue={isDisplayingMode ? stage!.plannedDurationTime : null}
                            />
                        </Grid>

                        <Grid item xs={4}>
                            <TextField
                                defaultValue={isDisplayingMode ? stage!.plannedFittersNumber : null}
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                variant="standard"
                                label="Planowana liczba montażystów"
                                name="plannedFittersNumber"
                            />
                        </Grid>
                        <Grid item xs={4}>
                            <TextField
                                id="standard-basic"
                                label="Minimalna liczba zdjęć"
                                variant="standard"
                                defaultValue={isDisplayingMode ? stage!.minimumImagesNumber : null}
                            />
                        </Grid>
                        {isDisplayingMode || (!isDisplayingMode && (userRole === Role.MANAGER)) ? (
                            <Grid item xs={4}>
                                <TextField
                                    id="standard-basic"
                                    label="Brygadzista"
                                    variant="standard"
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
                                <Tabs value={tabValue} onChange={handleTabChange} aria-label="basic tabs example">
                                    <Tab label="Informacje o datach" {...tabProps(0)} />
                                    <Tab label="Narzędzia" {...tabProps(1)} />
                                    <Tab label="Elementy" {...tabProps(2)} />
                                    {isDisplayingMode ? <Tab label="Montażyści..." {...tabProps(2)} /> : null}
                                    <Tab label="Szczegóły etapu/Zalaczniki" {...tabProps(1)} />
                                </Tabs>
                            </Box>
                            <TabPanel value={tabValue} index={0}>
                                {getDateInformations(stage)}
                            </TabPanel>
                            <TabPanel value={tabValue} index={1}>
                                <OrderStageDetailsTable
                                    itemsArray={queryToolTypes.data}
                                    plannedData={plannedToolType!}
                                    setPlannedData={setPlannedToolType}
                                    isDisplayingMode={isDisplayingMode!}
                                />
                            </TabPanel>
                            <TabPanel value={tabValue} index={2}>
                                <Grid item xs={2}>
                                    <TextField
                                        sx={{ width: '100%' }}
                                        required
                                        id="outlined-required"
                                        label="Elementy"
                                        name="elements"
                                        value={formik.values.elements}
                                        onChange={formik.handleChange}
                                    />
                                </Grid>
                            </TabPanel>

                            <TabPanel value={tabValue} index={3}>
                                Montażyści...
                            </TabPanel>
                            <TabPanel value={tabValue} index={4}>
                                <Grid item xs={2}>
                                    <TextField sx={{ width: '100%' }} label="Załączniki" name="attachments" />
                                </Grid>{' '}
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
