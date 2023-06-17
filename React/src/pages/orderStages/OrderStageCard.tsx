import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos'
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos'
import AssignmentIcon from '@mui/icons-material/Assignment'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import { Box, Button, CardActions, Grid, Paper, Tab, Tabs, Typography } from '@mui/material'
import Card from '@mui/material/Card'
import Collapse from '@mui/material/Collapse'
import IconButton from '@mui/material/IconButton'
import { DatePicker, TimePicker } from '@mui/x-date-pickers'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { DateValidationError, TimeValidationError } from '@mui/x-date-pickers/models'
import { AxiosError } from 'axios'
import dayjs, { Dayjs } from 'dayjs'
import { useFormik } from 'formik'
import { useContext, useEffect, useLayoutEffect, useMemo, useRef, useState } from 'react'
import { useQuery, useQueryClient } from 'react-query'
import { useParams } from 'react-router-dom'
import { getAllElements, getPlannedElementById } from '../../api/element.api'
import { getOrderDetails } from '../../api/order.api'
import { getAllToolTypes, getPlannedToolTypesById } from '../../api/toolType.api'
import useAttachmentData from '../../components/attachments/AttachmentData.hook'
import Attachments from '../../components/attachments/Attachments'
import { deleteFilesFromServer, saveNewFiles } from '../../components/attachments/attachments.helper'
import { CustomTextField } from '../../components/form/FormInput'
import { orderStageStatusName } from '../../helpers/enum.helper'
import useBreakpoints from '../../hooks/useBreakpoints'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { Order } from '../../types/model/Order'
import { OrderStage } from '../../types/model/OrderStage'
import { ToolType } from '../../types/model/ToolType'
import { Role } from '../../types/roleEnum'
import { isAuthorized } from '../../utils/authorize'
import { getRolesFromToken } from '../../utils/token'
import PlannerStageDetails from '../orders/PlannerStageDetails'
import OrderStageElementsTable from './OrderStageElementsTable'
import OrderStageToolsTable from './OrderStageToolsTable'
import { ExpandMore, TabPanel, validationSchema } from './helper'
import { useAddOrderStage, useOrderStageNextStatus, useOrderStagePreviousStatus, useUpdateOrderStage } from './hooks'
import { PageMode } from '../../types/form'
import { useOrderData } from '../orders/hooks'
import { canChangeToNextStatus, canChangeToPreviousStatus, validateNextOrderStageStatus } from './statusValidation'

type OrderStageCardProps = {
    index?: string
    stage?: OrderStage
    addingNewStag: boolean
    setAddingNewStage: (value: boolean) => void
}

const OrderStageCard = ({ index, stage, addingNewStag, setAddingNewStage }: OrderStageCardProps) => {
    const [stageMode, setStageMode] = useState<PageMode>(addingNewStag ? 'new' : 'read')
    const [tabValue, setTabValue] = useState(0)
    const [userRole, setUserRole] = useState('')
    const [expandedInformation, setExpandedInformation] = useState(false)
    const [plannedStartDate, setPlannedStartDate] = useState<Dayjs | null>(
        dayjs(stage?.plannedStartDate ? stage?.plannedStartDate : null),
    )
    const [plannedStartHour, setPlannedStartHour] = useState<Dayjs | null>(
        dayjs(stage?.plannedStartDate ? stage?.plannedStartDate : null),
    )
    const [plannedFinishHour, setPlannedFinishHour] = useState<Dayjs | null>(
        dayjs(stage?.plannedEndDate ? stage?.plannedEndDate : null),
    )
    const [preparedPlannedStartDate, setPreparedPlannedStartDate] = useState('')
    const [preparedPlannedEndDate, setPreparedPlannedEndDate] = useState('')
    const addOrderStage = useAddOrderStage(() => onSaveOrderStageSucceess())
    const updateOrderStage = useUpdateOrderStage(() => onSaveOrderStageSucceess())
    const appSize = useBreakpoints()
    const { showDialog } = useContext(DialogGlobalContext)

    const dummyScrollDiv = useRef<any>(null)
    const params = useParams()
    const plannedElementsRef = useRef<{ numberOfElements: number; elementId: string }[]>([])
    const plannedToolsTypesRef = useRef<{ numberOfTools: number; toolTypeId: string }[]>([])
    const [error, setError] = useState<DateValidationError | null>(null)
    const [startHourError, setStartHourError] = useState<TimeValidationError | null>(null)
    const [endHourError, setEndHourError] = useState<TimeValidationError | null>(null)

    useEffect(() => {
        const role = getRolesFromToken()
        if (role.length !== 0) setUserRole(role[0])
    }, [])

    const canBeEdittedBySpecialist = () => {
        if (userRole === Role.SPECIALIST) {
            return stageMode == 'read'
        }

        return true
    }

    const queryClient = useQueryClient()
    //attachment functionality
    const attachmentData = useAttachmentData({
        idsOfFilesFromServer: stage?.attachments || [],
    })
    const onSaveOrderStageSucceess = async () => {
        const saveResult = await saveNewFiles(attachmentData.fileListLocal, stage?.id)
        const deleteResult = await deleteFilesFromServer(attachmentData.fileIdsFromServerToDelete)
        const attachmentResult = saveResult && deleteResult
        let message = stageMode == 'edit' ? 'Zedytowano etap pomyślnie' : 'Nowy etap utworzono pomyślnie'
        if (!attachmentResult) message += ', jednak wystąpił błąd zapisu załączników'
        showDialog({
            btnOptions: [
                {
                    text: 'OK',
                    value: 0,
                },
            ],
            title: attachmentResult ? 'Sukces' : 'Błąd zapisu załączników',
            content: message,
            callback: () => {
                queryClient.refetchQueries({
                    queryKey: ['orderStageForOrder', { id: params.id }],
                })
                setAddingNewStage(false)
                setStageMode('read')
            },
        })
    }

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

    useLayoutEffect(() => {
        if (addingNewStag === true) {
            setExpandedInformation(true)
        }
    }, [addingNewStag])

    useEffect(() => {
        const date = dayjs(plannedStartDate).format('YYYY-MM-DDTHH:mm:ss.SSS')
        const hours = dayjs(plannedStartHour).format('HH:mm:ss.SSS')
        const preparedDate = date.substring(0, date.indexOf('T') + 1).replace('T', 'T' + hours)
        setPreparedPlannedStartDate(preparedDate)
        if (plannedStartHour?.toString() != 'Invalid Date' && plannedStartDate) {
            formik.setFieldValue('plannedStartDate', preparedDate)
        }
    }, [plannedStartHour, plannedStartDate])

    useEffect(() => {
        const date = dayjs(plannedStartDate).format('YYYY-MM-DDTHH:mm:ss.SSS')
        const hours = dayjs(plannedFinishHour).format('HH:mm:ss.SSS')
        const preparedDate = date.substring(0, date.indexOf('T') + 1).replace('T', 'T' + hours)
        setPreparedPlannedEndDate(preparedDate)
        if (plannedFinishHour?.toString() != 'Invalid Date' && plannedStartDate) {
            const isEndDateBeforeStartDay = dayjs(preparedPlannedStartDate).isAfter(dayjs(preparedDate))
            if (!isEndDateBeforeStartDay) {
                formik.setFieldValue('plannedEndDate', preparedDate)
                setEndHourError('disablePast')
            }
        }
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
        setStageMode('edit')
        handleSetToolsTypeOnEdit()
        handleSetElementsOnEdit()
    }

    const handleResetFormik = () => {
        plannedElementsRef.current! = []
        plannedToolsTypesRef.current! = []
        setPreparedPlannedStartDate('')
        setPreparedPlannedEndDate('')
        if (stage) {
            setPlannedStartDate(dayjs(stage.plannedStartDate))
            setPlannedStartHour(dayjs(stage.plannedStartDate))
            setPlannedFinishHour(dayjs(stage.plannedEndDate))
        } else {
            setPlannedStartDate(null)
            setPlannedStartHour(null)
            setPlannedFinishHour(null)
        }
        formik.resetForm()
    }
    const handleCancelButtonAction = () => {
        setStageMode('read')
        setAddingNewStage(false)
        handleResetFormik()
        attachmentData.handleReset()
    }

    const mapStageToFormik = (stage?: OrderStage) => {
        return {
            orderId: params.id!,
            orderStageId: !!stage && stage.id ? stage.id.toString() : '',
            name: stage ? stage.name : '',
            status: stage ? stage.status : null,
            price: stage ? stage.price : '',
            plannedStartDate: stage ? stage.plannedStartDate : '',
            plannedEndDate: stage ? stage.plannedEndDate : '',
            plannedFittersNumber: stage ? stage.plannedFittersNumber : '',
            minimumImagesNumber: stage ? stage.minimumImagesNumber : '0',
            listOfToolsPlannedNumber: stage ? stage.listOfToolsPlannedNumber : [],
            listOfElementsPlannedNumber: stage ? stage.listOfElementsPlannedNumber : [],
            attachments: stage && stage.attachments ? stage.attachments : [],
            fitters: stage && stage.fitters ? stage.fitters : [],
        }
    }
    useLayoutEffect(() => {
        formik.setValues(mapStageToFormik(stage))
    }, [stage])

    const formik = useFormik({
        initialValues: mapStageToFormik(stage),
        validationSchema: validationSchema,
        onSubmit: (values) => {
            values.listOfElementsPlannedNumber = plannedElementsRef.current!
            values.listOfToolsPlannedNumber = plannedToolsTypesRef.current!
            values.plannedStartDate = preparedPlannedStartDate
            values.plannedEndDate = preparedPlannedEndDate

            if (stageMode == 'edit') {
                return updateOrderStage.mutate(values)
            }
            addOrderStage.mutate(values)
        },
    })

    const errorMessage = useMemo(() => {
        switch (error) {
            case 'invalidDate': {
                return 'Wprowadź poprawną datę'
            }

            default: {
                return ''
            }
        }
    }, [error])

    const errorStartHourMessage = useMemo(() => {
        switch (error) {
            case 'invalidDate': {
                return 'Wprowadź poprawną godzinę'
            }
            default: {
                return ''
            }
        }
    }, [startHourError])

    const errorEndHourMessage = useMemo(() => {
        switch (error) {
            case 'invalidDate': {
                return 'Wprowadź poprawną godzinę'
            }
            case 'disablePast': {
                return 'Godzina zakończenia nie może być mniejsza niż godzina rozpoczęcia'
            }
            default: {
                return ''
            }
        }
    }, [endHourError])

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
                            disabled={canBeEdittedBySpecialist()}
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
                        />
                    </LocalizationProvider>
                </Grid>

                {stageMode != 'new' ? (
                    <Grid item xs={6}>
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DatePicker
                                sx={{
                                    width: '100%',
                                    '& .MuiInputBase-input.Mui-disabled': {
                                        WebkitTextFillColor: '#000000',
                                    },
                                }}
                                format="DD/MM/YYYY HH:mm"
                                label="Data rozpoczęcia"
                                value={stage ? dayjs(stage.startDate) : ''}
                                disabled={true}
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
                            format={'HH:mm'}
                            ampm={false}
                            value={plannedStartHour}
                            disabled={canBeEdittedBySpecialist()}
                            onChange={(data) => {
                                setStartHourError(null)
                                setPlannedStartHour(data)
                            }}
                            onError={(error) => setStartHourError(error)}
                            slotProps={{
                                textField: {
                                    helperText: errorStartHourMessage || '',
                                },
                            }}
                        />
                    </LocalizationProvider>
                </Grid>

                {stageMode != 'new' ? (
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
                                disabled={true}
                                format="DD/MM/YYYY HH:mm"
                                value={stage ? dayjs(stage.endDate) : ''}
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
                            disabled={canBeEdittedBySpecialist()}
                            label="Planowana godzina zakończenia"
                            ampm={false}
                            minTime={plannedStartHour}
                            format={'HH:mm'}
                            value={plannedFinishHour}
                            onChange={(data) => {
                                const isDateBeforeStartDate = dayjs(plannedStartHour).isAfter(data)
                                if (isDateBeforeStartDate) {
                                    return setError('disablePast')
                                }
                                setEndHourError(null)
                                setPlannedFinishHour(data)
                            }}
                            onError={(error) => setEndHourError(error)}
                            slotProps={{
                                textField: {
                                    helperText: errorEndHourMessage || '',
                                },
                            }}
                        />
                    </LocalizationProvider>
                </Grid>
            </Grid>
        )
    }

    const queryOrder = useOrderData(stage ? stage.orderId.toString() : '')

    const canEdit = () => {
        const orderStageStatus = stage?.status
        return (
            (orderStageStatus == 'PLANNING' &&
                isAuthorized([Role.SPECIALIST]) &&
                queryOrder.data?.status != 'TO_ACCEPT') ||
            (orderStageStatus == 'ADDING_FITTERS' && isAuthorized([Role.FOREMAN])) ||
            (orderStageStatus == 'PICK_UP' && isAuthorized([Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER])) ||
            (orderStageStatus == 'REALESED' && isAuthorized([Role.FOREMAN])) ||
            (orderStageStatus == 'ON_WORK' && isAuthorized([Role.FOREMAN])) ||
            (orderStageStatus == 'RETURN' && isAuthorized([Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER])) ||
            (orderStageStatus == 'RETURNED' && isAuthorized([Role.FOREMAN]))
        )
    }

    const orderNextStatusMutation = useOrderStageNextStatus(() => {
        queryClient.refetchQueries({
            queryKey: ['order', { id: stage?.orderId.toString() }],
        })
        queryClient.refetchQueries({
            queryKey: ['orderStageForOrder', { id: stage?.orderId.toString() }],
        })
    })
    const orderPreviousStatusMutation = useOrderStagePreviousStatus(() => {
        queryClient.refetchQueries({
            queryKey: ['order', { id: stage?.orderId.toString() }],
        })
        queryClient.refetchQueries({
            queryKey: ['orderStageForOrder', { id: stage?.orderId.toString() }],
        })
    })

    const handleNextStatus = () => {
        const validationResult = validateNextOrderStageStatus(stage)

        if (validationResult.isValid)
            showDialog({
                title: 'Czy na pewno chcesz zmienić status etapu na następny?',
                btnOptions: [
                    { text: 'Tak', value: 1, variant: 'contained' },
                    { text: 'Anuluj', value: 0, variant: 'outlined' },
                ],
                callback: (result: number) => {
                    if (result == 1 && stage && stage.id) orderNextStatusMutation.mutate(stage.id)
                },
            })
        else
            showDialog({
                title: 'Nie można zmienić statusu',
                content: validationResult.message,
                btnOptions: [{ text: 'Ok', value: 1, variant: 'contained' }],
            })
    }

    const handlePreviousStatus = () => {
        showDialog({
            title: 'Czy na pewno chcesz cofnąć status etapu?',
            btnOptions: [
                { text: 'Tak', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && stage && stage.id) orderPreviousStatusMutation.mutate(stage.id)
            },
        })
    }

    return (
        <Card id={index ? index.toString() : ''} sx={{ margin: 'auto', marginTop: '20px', border: '1px solid' }}>
            <CardActions disableSpacing>
                <IconButton aria-label="share">
                    <AssignmentIcon />
                </IconButton>
                <Typography variant="h5" color="text.secondary">
                    {stage ? `Etap - ${stage.name}` : `Nowy etap`}
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
                                readOnly={canBeEdittedBySpecialist()}
                                disabled={canBeEdittedBySpecialist()}
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

                        <Grid item xs={4} md={3}>
                            <CustomTextField
                                readOnly={true}
                                disabled={true}
                                sx={{ width: '100%' }}
                                id="standard-basic"
                                variant="outlined"
                                label="Status"
                                name="status"
                                value={stage ? orderStageStatusName(stage.status) : orderStageStatusName('PLANNING')}
                            />
                        </Grid>

                        <Grid item xs={4} md={3}>
                            <CustomTextField
                                readOnly={canBeEdittedBySpecialist()}
                                disabled={canBeEdittedBySpecialist()}
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
                                readOnly={canBeEdittedBySpecialist()}
                                disabled={canBeEdittedBySpecialist()}
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
                        <Box sx={{ marginTop: '20px', width: '100%' }}>
                            <Box
                                sx={{
                                    borderBottom: 1,
                                    borderColor: 'divider',
                                    display: 'flex',
                                    justifyContent: 'space-around',
                                }}
                            >
                                <Tabs component="div" value={tabValue} onChange={handleTabChange}>
                                    <Tab label="Informacje o datach" {...tabProps(0)} />
                                    <Tab label="Narzędzia" {...tabProps(1)} />
                                    <Tab label="Elementy" {...tabProps(2)} />
                                    <Tab label="Załączniki" {...tabProps(3)} />
                                    {!isAuthorized([Role.SPECIALIST]) && <Tab label="Montażyści" {...tabProps(4)} />}
                                </Tabs>
                            </Box>
                            <TabPanel value={tabValue} index={0}>
                                {getDateInformations(stage)}
                            </TabPanel>
                            <TabPanel value={tabValue} index={1}>
                                <OrderStageToolsTable
                                    itemsArray={queryAllToolTypes.data || []}
                                    isDisplayingMode={canBeEdittedBySpecialist()}
                                    toolsTypeListIds={stage?.listOfToolsPlannedNumber as any}
                                    handleChange={handleSetPlannedToolsTypes}
                                    toolsRef={plannedToolsTypesRef}
                                    releasedToolsInfo={stage?.simpleToolReleases}
                                />
                            </TabPanel>
                            <TabPanel value={tabValue} index={2}>
                                <OrderStageElementsTable
                                    itemsArray={queryAllElements.data || []}
                                    isDisplayingMode={canBeEdittedBySpecialist()}
                                    elementsListIds={stage?.listOfElementsPlannedNumber as any}
                                    handleChange={handleSetPlannedElements}
                                    elementsRef={plannedElementsRef}
                                    releasedReturnedElementsInfo={stage?.simpleElementReturnReleases}
                                />
                            </TabPanel>
                            <TabPanel value={tabValue} index={3}>
                                <Attachments {...attachmentData} readonly={stageMode == 'read'} />
                            </TabPanel>
                            {!isAuthorized([Role.SPECIALIST]) && (
                                <TabPanel value={tabValue} index={4}>
                                    <PlannerStageDetails
                                        dateFrom={preparedPlannedStartDate}
                                        dateTo={preparedPlannedEndDate}
                                        fitters={formik.values.fitters}
                                        setFitters={(fitters) => formik.setFieldValue('fitters', fitters)}
                                        readonly={!isAuthorized([Role.FOREMAN]) || stageMode == 'read'}
                                        orderStageId={formik.values.orderStageId}
                                    />
                                </TabPanel>
                            )}
                        </Box>

                        <Grid spacing={2} container justifyContent="flex-end" sx={{ marginTop: '20px' }}>
                            <Grid
                                item
                                sx={{
                                    mt: '15px',
                                    gap: '15px',
                                    display: appSize.isMobile ? 'grid' : 'flex',
                                    flexDirection: 'row-reverse',
                                }}
                            >
                                {stageMode == 'read' && canEdit() && (
                                    <Button
                                        color="primary"
                                        variant="contained"
                                        // disabled={isLoading}
                                        onClick={handleEditButtonAction}
                                    >
                                        Edytuj etap
                                    </Button>
                                )}
                                {stageMode != 'read' && (
                                    <>
                                        <Button
                                            type="submit"
                                            color="primary"
                                            variant="contained"
                                            // disabled={isLoading}
                                            id={`formButton-save`}
                                        >
                                            Zapisz
                                        </Button>
                                        <Button
                                            color="primary"
                                            variant="contained"
                                            // disabled={isLoading}
                                            onClick={handleCancelButtonAction}
                                            id={`formButton-cancel`}
                                        >
                                            Anuluj
                                        </Button>
                                    </>
                                )}
                                {canChangeToNextStatus(stage, queryOrder.data) && stageMode == 'read' && (
                                    <Button
                                        id={`formButton-nextStatus`}
                                        color="primary"
                                        startIcon={<ArrowForwardIosIcon />}
                                        variant="contained"
                                        style={{ width: appSize.isMobile ? 'auto' : 190 }}
                                        onClick={handleNextStatus}
                                    >
                                        Następny status
                                    </Button>
                                )}
                                {canChangeToPreviousStatus(stage) && stageMode == 'read' && (
                                    <Button
                                        id={`formButton-nextStatus`}
                                        color="primary"
                                        startIcon={<ArrowBackIosIcon />}
                                        variant="contained"
                                        style={{ width: appSize.isMobile ? 'auto' : 170 }}
                                        onClick={handlePreviousStatus}
                                    >
                                        Cofnij status
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
