import { Button, CircularProgress, Divider, Grid, Paper, TextField, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { AxiosError } from 'axios'
import { useFormik } from 'formik'
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

import SaveIcon from '@mui/icons-material/Save'
import ReplayIcon from '@mui/icons-material/Replay'
import CloseIcon from '@mui/icons-material/Close'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import { emptyForm, validationSchema } from './helper'
import FormInput from '../../components/form/FormInput'
import FormLabel from '../../components/form/FormLabel'
import FormSelect from '../../components/form/FormSelect'
import DialogInfo, { DialogInfoParams } from '../../components/dialogInfo/DialogInfo'

const OrderDetails = () => {
    const params = useParams()
    const [readonlyMode, setReadonlyMode] = useState(true)
    const [initData, setInitData] = useState(emptyForm)
    const navigation = useNavigate()

    const [dialog, setDialog] = useState({
        open: false,
        dialogText: [''],
        confirmAction: () => {},
        confirmLabel: '',
    })

    const handleSubmit = () => {
        if (params.id == 'new') mutationPost.mutate(JSON.parse(JSON.stringify(formik.values)))
        else mutationUpdate.mutate(JSON.parse(JSON.stringify(formik.values)))
    }

    const handleDelete = () => {
        displayInfo({
            dialogText: ['Czy na pewno chcesz usunąć zlecenie?'],
            confirmAction: () => {
                setDialog({ ...dialog, open: false })
                if (formik.values.id) mutationDelete.mutate(formik.values.id)
            },
            confirmLabel: 'Usuń',
            cancelAction: () => {
                setDialog({ ...dialog, open: false })
            },
            cancelLabel: 'Anuluj',
        })
    }

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

    const handleReset = () => {
        formik.resetForm()
        formik.setValues(JSON.parse(JSON.stringify(initData)))
    }

    const handleCancel = () => {
        handleReset()
        setReadonlyMode(true)
    }

    useEffect(() => {
        if (queryData.data) {
            formik.setValues(JSON.parse(JSON.stringify(queryData.data)))
            setInitData(JSON.parse(JSON.stringify(queryData.data)))
        }
    }, [queryData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setReadonlyMode(false)
            formik.setValues(JSON.parse(JSON.stringify(emptyForm)))
            setInitData(JSON.parse(JSON.stringify(emptyForm)))
        } else setReadonlyMode(true)
    }, [params.id])

    const displayInfo = (params: DialogInfoParams) => {
        setDialog({
            ...params,
            open: true,
        })
    }

    return (
        <>
            <Box maxWidth={800} style={{ margin: 'auto', marginTop: '20px' }}>
                <Paper sx={{ p: '10px' }}>
                    {queryData.isLoading || queryData.isError ? (
                        <Box
                            sx={{
                                display: 'flex',
                                minHeight: '200px',
                                justifyContent: 'center',
                                alignItems: 'center',
                            }}
                        >
                            {queryData.isLoading ? (
                                <CircularProgress />
                            ) : (
                                <Typography>Nie znaleziono zlecenia</Typography>
                            )}
                        </Box>
                    ) : (
                        <>
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
                        </>
                    )}
                </Paper>
            </Box>
            {dialog.open && <DialogInfo {...dialog} />}
        </>
    )
}

export default OrderDetails
