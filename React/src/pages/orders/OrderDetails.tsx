import { Button, CircularProgress, Divider, Grid, Paper, TextField, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { AxiosError } from 'axios'
import { useFormik } from 'formik'
import { useEffect, useState } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate, useParams } from 'react-router-dom'
import { getAllCompanies } from '../../api/company.api'
import { deleteOrder, getOrderDetails, postOrder, updateOrder } from '../../api/order.api'
import { formatArrayToOptions, formatLocation } from '../../helpers/format.helper'
import { priorityOptions, statusOptions } from '../../helpers/enum.helper'
import { theme } from '../../themes/baseTheme'
import { Client } from '../../types/model/Client'
import { Company } from '../../types/model/Company'
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
// import { formStructure } from './helper'
import FormInput from '../../components/form/FormInput'
import FormLabel from '../../components/form/FormLabel'
import FormSelect from '../../components/form/FormSelect'
import DialogInfo, { DialogInfoParams } from '../../components/dialogInfo/DialogInfo'
import { getEmptyForm, getValidatinSchema } from '../../helpers/form.helper'

import * as yup from 'yup'
import { useFormStructure } from './helper'

const OrderDetails = () => {
    const formStructure = useFormStructure()
    console.log(formStructure)
    const params = useParams()
    const [readonlyMode, setReadonlyMode] = useState(true)
    const [initData, setInitData] = useState(getEmptyForm(formStructure))
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
                if (params.id && params.id != 'new') mutationDelete.mutate(params.id)
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


    const formik = useFormik({
        initialValues: initData,
        validationSchema: yup.object(getValidatinSchema(formStructure)),
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
            formik.setValues(JSON.parse(JSON.stringify(getEmptyForm(formStructure))))
            setInitData(JSON.parse(JSON.stringify(getEmptyForm(formStructure))))
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
                            <Box>
                                {formStructure.map((e) => {
                                    switch (e.type) {
                                        case 'input':
                                            return (
                                                <FormInput
                                                    formik={formik}
                                                    id={e.id}
                                                    readonly={readonlyMode || !!e.readonly}
                                                    key={e.id}
                                                />
                                            )
                                        case 'date':
                                            return (
                                                <FormInput
                                                    formik={formik}
                                                    id={e.id}
                                                    readonly={readonlyMode || !!e.readonly}
                                                    key={e.id}
                                                    type={'datetime-local'}
                                                />
                                            )
                                        case 'select':
                                            return (
                                                <FormSelect
                                                    formik={formik}
                                                    id={e.id}
                                                    readonly={readonlyMode || !!e.readonly}
                                                    key={e.id}
                                                    options={e.options}
                                                />
                                            )
                                    }
                                })}
                            </Box>
                            

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
