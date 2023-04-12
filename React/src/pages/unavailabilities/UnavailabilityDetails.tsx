import { Button, CircularProgress, Divider, Grid, Paper, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { AxiosError } from 'axios'
import { useFormik } from 'formik'
import { useEffect, useState } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate, useParams } from 'react-router-dom'
import {
    deleteUnavailability,
    getUnavailabilityDetails,
    postUnavailability,
    updateUnavailability,
} from '../../api/unavailability.api'
import { formatArrayToOptions } from '../../helpers/format.helper'
import { theme } from '../../themes/baseTheme'

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
import { Unavailability } from '../../types/model/Unavailability'
import { typeOfUnavailabilityOptions } from '../../helpers/enum.helper'
import { Employee } from '../../types/model/Employee'
import { getAllUsers } from '../../api/user.api'

const UnavailabilityDetails = () => {
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
            dialogText: ['Czy na pewno chcesz usunąć nieobecność?'],
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
        mutationFn: postUnavailability,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Nowa nieobecność utworzona pomyślnie'],
                confirmAction: () => {
                    console.log(data)
                    setDialog({ ...dialog, open: false })
                    if (data.id) navigation(`/unavailabilities/${data.id}`)
                    else navigation(`/unavailabilities`)
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            console.error(error)
            displayInfo({
                dialogText: ['Błąd poczas tworzenia nowej nieobecności', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationUpdate = useMutation({
        mutationFn: updateUnavailability,
        onSuccess(data) {
            queryData.refetch({
                queryKey: ['unavailability', { id: data.id }],
            })
            setReadonlyMode(true)
            displayInfo({
                dialogText: ['Zmiany w nieobecności zostały zapisane'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas zapisywania nieobecności', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationDelete = useMutation({
        mutationFn: deleteUnavailability,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Nieobecność została usunięta'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    queryData.remove()
                    navigation('/unavailabilities')
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas usuwania nieobecności', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const queryData = useQuery<Unavailability, AxiosError>(
        ['unavailability', { id: params.id }],
        async () => getUnavailabilityDetails(params.id && params.id != 'new' ? params.id : ''),
        {
            enabled: !!params.id && params.id != 'new',
        },
    )

    const queryEmployees = useQuery<Array<Employee>, AxiosError>(['employee-list'], getAllUsers)

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
                                <Typography>Nie znaleziono nieobecności</Typography>
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
                                    <FormLabel label="Pracownik" formik={formik} id={'assignedToId'} />
                                    <FormLabel label="Typ nieobecności" formik={formik} id={'typeOfUnavailability'} />
                                    <FormLabel label="Opis" formik={formik} id={'description'} />
                                    <FormLabel label="Od" formik={formik} id={'unavailableFrom'} />
                                    <FormLabel label="Do" formik={formik} id={'unavailableTo'} />
                                    <FormLabel label="Przypisane przez" formik={formik} id={'assignedById'} />
                                </Grid>
                                <Divider
                                    orientation="vertical"
                                    variant="middle"
                                    style={{ borderColor: theme.palette.primary.main }}
                                    sx={{ mr: '-1px' }}
                                    flexItem
                                />
                                <Grid item xs={6}>
                                    <FormSelect //TODO: Autocomplete
                                        id={'assignedToId'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={formatArrayToOptions(
                                            'id',
                                            (x: Employee) => x.firstName + ' ' + x.lastName,
                                            queryEmployees.data,
                                        )}
                                        firstChild
                                    />
                                    <FormSelect
                                        id={'typeOfUnavailability'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={typeOfUnavailabilityOptions()}
                                    />
                                    <FormInput id={'description'} formik={formik} readonly={readonlyMode} />
                                    <FormInput
                                        id={'unavailableFrom'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        type="date"
                                    />
                                    <FormInput
                                        id={'unavailableTo'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        type="date"
                                    />
                                    <FormSelect
                                        id={'assignedById'}
                                        formik={formik}
                                        readonly
                                        options={formatArrayToOptions(
                                            'id',
                                            (x: Employee) => x.firstName + ' ' + x.lastName,
                                            queryEmployees.data,
                                        )}
                                    />
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

export default UnavailabilityDetails
