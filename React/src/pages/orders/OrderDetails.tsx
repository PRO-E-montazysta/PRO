import { CircularProgress, Paper, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { useFormik } from 'formik'
<<<<<<< HEAD
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
=======
import { useEffect, useState } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate, useParams } from 'react-router-dom'
import { deleteOrder, getOrderDetails, postOrder, updateOrder } from '../../api/order.api'
import { formatArrayToOptions, formatLocation } from '../../helpers/format.helper'
import { priorityOptions, statusOptions } from '../../helpers/enum.helper'
import { theme } from '../../themes/baseTheme'
import { Client } from '../../types/model/Client'
import { Order } from '../../types/model/Order'
import { AppUser } from '../../types/model/AppUser'
import { getAllClients } from '../../api/client.api'
import { getAllForemans } from '../../api/foreman.api'
import { getAllLocations } from '../../api/location.api'
import { getAllManagers } from '../../api/manager.api'
import { getAllSalesReprezentatives } from '../../api/salesReprezentatives.api'
import { getAllSpecialists } from '../../api/specialist.api'
>>>>>>> b0c32a396fbae5a67659d71a248f20d6bf2a014a

import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'

import * as yup from 'yup'
import { useFormStructure } from './helper'
import { useAddOrder, useDeleteOrder, useEditOrder, useOrderData } from './hooks'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { FormButtons } from '../../components/form/FormButtons'
import { FormStructure } from '../../components/form/FormStructure'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import QueryBoxStatus from '../../components/base/QueryStatusBox'

const OrderDetails = () => {
    //parameters from url
    const params = useParams()
    
    //mode of this page 
    const [readonlyMode, setReadonlyMode] = useState(true)
    //global dialog box 
    const { showDialog } = useContext(DialogGlobalContext)
    //custom form structure 
    const formStructure = useFormStructure()

    //initial values from custom form structure
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addOrderMutation = useAddOrder()
    const editOrderMutation = useEditOrder((data) => handleOnEditSuccess(data))
    const deleteOrderMutation = useDeleteOrder(() => orderData.remove())
    const orderData = useOrderData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus([addOrderMutation, editOrderMutation, deleteOrderMutation, orderData])

    //form with initial values and validation
    const formik = useFormik({
        initialValues: initData,
        validationSchema: yup.object(getValidatinSchema(formStructure)),
        onSubmit: () => handleSubmit(),
    })

    const handleSubmit = () => {
        if (params.id == 'new') addOrderMutation.mutate(JSON.parse(JSON.stringify(formik.values)))
        else editOrderMutation.mutate(JSON.parse(JSON.stringify(formik.values)))
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć zlecenie?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id && params.id != 'new') deleteOrderMutation.mutate(params.id)
            },
        })
    }

<<<<<<< HEAD
    const handleOnEditSuccess = (data: any) => {
        orderData.refetch({
            queryKey: ['order', { id: data.id }],
        })
        setReadonlyMode(true)
    }
=======
    const mutationPost = useMutation({
        mutationFn: postOrder,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Nowe zlecenie utworzono pomyślnie'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    if (data.id) navigation(`/orders/${data.id}`)
                    else navigation(`/orders`)
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            console.error(error)
            displayInfo({
                dialogText: ['Błąd poczas tworzenia nowego zlecenia', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationUpdate = useMutation({
        mutationFn: updateOrder,
        onSuccess(data) {
            queryData.refetch({
                queryKey: ['order', { id: data.id }],
            })
            setReadonlyMode(true)
            displayInfo({
                dialogText: ['Zmiany w zleceniu zostały zapisane'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            console.error(error)
            displayInfo({
                dialogText: ['Błąd poczas zapisywania zlecenia', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationDelete = useMutation({
        mutationFn: deleteOrder,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Zlecenie zostało usunięte'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    queryData.remove()
                    navigation('/orders')
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            console.error(error)
            displayInfo({
                dialogText: ['Błąd poczas usuwania zlecenia', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const queryData = useQuery<Order, AxiosError>(
        ['order', { id: params.id }],
        async () => getOrderDetails(params.id && params.id != 'new' ? params.id : ''),
        {
            enabled: !!params.id && params.id != 'new',
        },
    )

    const queryClient = useQuery<Array<Client>, AxiosError>(['client-list'], getAllClients)
    const queryForeman = useQuery<Array<AppUser>, AxiosError>(['foreman-list'], getAllForemans)
    const queryLocation = useQuery<Array<Location>, AxiosError>(['location-list'], getAllLocations)
    const queryManager = useQuery<Array<AppUser>, AxiosError>(['manager-list'], getAllManagers)
    const querySalesReprezentative = useQuery<Array<AppUser>, AxiosError>(
        ['sales-reprezentative-list'],
        getAllSalesReprezentatives,
    )
    const querySpecialist = useQuery<Array<AppUser>, AxiosError>(['specialist-list'], getAllSpecialists)

    const formik = useFormik({
        initialValues: initData,
        validationSchema: validationSchema,
        onSubmit: handleSubmit,
    })
>>>>>>> b0c32a396fbae5a67659d71a248f20d6bf2a014a

    const handleReset = () => {
        formik.resetForm()
        formik.setValues(JSON.parse(JSON.stringify(initData)))
    }

    const handleCancel = () => {
        handleReset()
        setReadonlyMode(true)
    }

    //edit mode
    //populate formik and init values with data from db
    useEffect(() => {
        if (orderData.data) {
            formik.setValues(JSON.parse(JSON.stringify(orderData.data)))
            setInitData(JSON.parse(JSON.stringify(orderData.data)))
        }
    }, [orderData.data])

    //new mode
    //set readonly mode, populate formik and init data with init values from helper
    useEffect(() => {
        if (params.id == 'new') {
            setReadonlyMode(false)
            formik.setValues(JSON.parse(JSON.stringify(getInitValues(formStructure))))
            setInitData(JSON.parse(JSON.stringify(getInitValues(formStructure))))
        } else setReadonlyMode(true)
    }, [params.id])

    return (
        <>
            <Box maxWidth={1200} style={{ margin: 'auto', marginTop: '20px' }}>
                <Typography variant="h4" sx={{ color: 'white', fontWeight: 700 }}>
                    Zamówienia
                </Typography>
                <Paper sx={{ p: '10px', mt: '20px' }}>
                    {queriesStatus.result != 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
<<<<<<< HEAD
                            <FormStructure formStructure={formStructure} formik={formik} readonlyMode={readonlyMode} />
                            <FormButtons
                                id={params.id}
                                onCancel={handleCancel}
                                onDelete={handleDelete}
                                onEdit={() => setReadonlyMode(false)}
                                onReset={handleReset}
                                onSubmit={formik.submitForm}
                                readonlyMode={readonlyMode}
                            />
=======
                            <Grid container>
                                <Grid
                                    item
                                    xs={6}
                                    style={{
                                        textAlign: 'end',
                                    }}
                                >
                                    <FormLabel label="Nazwa zlecenia" formik={formik} id={'name'} />
                                    <FormLabel label="Priorytet" formik={formik} id={'typeOfPriority'} />
                                    <FormLabel label="Status" formik={formik} id={'typeOfStatus'} />
                                    <FormLabel label="Planowany czas rozpoczęcia" formik={formik} id={'plannedStart'} />
                                    <FormLabel label="Planowany czas zakończenia" formik={formik} id={'plannedEnd'} />
                                    <FormLabel label="Klient" formik={formik} id={'clientId'} />
                                    <FormLabel label="Brygadzista" formik={formik} id={'foremanId'} />
                                    <FormLabel label="Lokalizacja" formik={formik} id={'locationId'} />
                                    <FormLabel label="Manager" formik={formik} id={'managerId'} />
                                    <FormLabel label="Specjalista" formik={formik} id={'specialistId'} />
                                    <FormLabel label="Handlowiec" formik={formik} id={'salesRepresentativeId'} />
                                    <FormLabel label="Czas utworzenia" formik={formik} id={'createdAt'} />
                                    <FormLabel label="Czas ostatniej edycji" formik={formik} id={'editedAt'} />
                                </Grid>
                                <Divider
                                    orientation="vertical"
                                    variant="middle"
                                    style={{ borderColor: theme.palette.primary.main }}
                                    sx={{ mr: '-1px' }}
                                    flexItem
                                />
                                <Grid item xs={6}>
                                    <FormInput id={'name'} formik={formik} readonly={readonlyMode} firstChild />
                                    <FormSelect
                                        id={'typeOfPriority'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={priorityOptions()}
                                    />
                                    <FormSelect
                                        id={'typeOfStatus'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={statusOptions()}
                                    />
                                    <FormInput
                                        id={'plannedStart'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        type="datetime-local"
                                    />
                                    <FormInput
                                        id={'plannedEnd'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        type="datetime-local"
                                    />
                                    <FormSelect
                                        id={'clientId'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={formatArrayToOptions('id', (x: Client) => x.name, queryClient.data)}
                                    />
                                    <FormSelect
                                        id={'foremanId'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={formatArrayToOptions(
                                            'id',
                                            (x: AppUser) => x.firstName + ' ' + x.lastName,
                                            queryForeman.data,
                                        )}
                                    />
                                    <FormSelect
                                        id={'locationId'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={formatArrayToOptions('id', formatLocation, queryLocation.data)}
                                    />
                                    <FormSelect
                                        id={'managerId'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={formatArrayToOptions(
                                            'id',
                                            (x: AppUser) => x.firstName + ' ' + x.lastName,
                                            queryManager.data,
                                        )}
                                    />
                                    <FormSelect
                                        id={'specialistId'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={formatArrayToOptions(
                                            'id',
                                            (x: AppUser) => x.firstName + ' ' + x.lastName,
                                            querySpecialist.data,
                                        )}
                                    />
                                    <FormSelect
                                        id={'salesRepresentativeId'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={formatArrayToOptions(
                                            'id',
                                            (x: AppUser) => x.firstName + ' ' + x.lastName,
                                            querySalesReprezentative.data,
                                        )}
                                    />
                                    <FormInput
                                        id={'createdAt'}
                                        formik={formik}
                                        readonly
                                        style={{ marginTop: !readonlyMode ? '12px' : '' }}
                                        type="datetime-local"
                                    />
                                    <FormInput id={'editedAt'} formik={formik} readonly type="datetime-local" />
                                </Grid>
                            </Grid>
                            <Box sx={{ margin: '20px', gap: '20px', display: 'flex', flexDirection: 'row-reverse' }}>
                                {readonlyMode && params.id != 'new' ? (
                                    <>
                                        <Button
                                            color="primary"
                                            startIcon={<EditIcon />}
                                            variant="contained"
                                            type="submit"
                                            style={{ width: 120 }}
                                            onClick={() => setReadonlyMode(false)}
                                        >
                                            Edytuj
                                        </Button>
                                        <Button
                                            color="error"
                                            startIcon={<DeleteIcon />}
                                            variant="contained"
                                            type="submit"
                                            style={{ width: 120 }}
                                            onClick={handleDelete}
                                        >
                                            Usuń
                                        </Button>
                                    </>
                                ) : (
                                    <>
                                        <Button
                                            color="primary"
                                            startIcon={<SaveIcon />}
                                            variant="contained"
                                            onClick={formik.submitForm}
                                            style={{ width: 120 }}
                                        >
                                            Zapisz
                                        </Button>
                                        <Button
                                            color="primary"
                                            startIcon={<ReplayIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                                            style={{ color: theme.palette.primary.main, width: 120 }}
                                            variant="outlined"
                                            onClick={handleReset}
                                        >
                                            Reset
                                        </Button>
                                        {params.id != 'new' && (
                                            <Button
                                                color="primary"
                                                startIcon={<CloseIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                                                style={{ color: theme.palette.primary.main, width: 120 }}
                                                variant="outlined"
                                                onClick={handleCancel}
                                            >
                                                Anuluj
                                            </Button>
                                        )}
                                    </>
                                )}
                            </Box>
>>>>>>> b0c32a396fbae5a67659d71a248f20d6bf2a014a
                        </>
                    )}
                </Paper>
            </Box>
        </>
    )
}

export default OrderDetails
