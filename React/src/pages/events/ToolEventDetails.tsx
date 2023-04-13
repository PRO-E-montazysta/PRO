import { Button, CircularProgress, Divider, Grid, Paper, TextField, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { AxiosError } from 'axios'
import { useFormik } from 'formik'
import { useEffect, useState } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate, useParams } from 'react-router-dom'
import { deleteToolEvent, getToolEventDetails, postToolEvent, updateToolEvent } from '../../api/event.api'
import { theme } from '../../themes/baseTheme'
import SaveIcon from '@mui/icons-material/Save'
import ReplayIcon from '@mui/icons-material/Replay'
import CloseIcon from '@mui/icons-material/Close'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import { toolEventEmptyForm, toolEventValidationSchema } from './helper'
import FormInput from '../../components/form/FormInput'
import FormLabel from '../../components/form/FormLabel'
import FormSelect from '../../components/form/FormSelect'
import DialogInfo, { DialogInfoParams } from '../../components/dialogInfo/DialogInfo'
import { eventStatusOptions } from '../../helpers/enum.helper'
import { ToolEvent } from '../../types/model/ToolEvent'
import { Tool } from '../../types/model/Tool'
import { getAllTools } from '../../api/tool.api'
import { formatArrayToOptions } from '../../helpers/format.helper'

const ToolEventDetails = () => {
    const params = useParams()
    const [readonlyMode, setReadonlyMode] = useState(true)
    const [initData, setInitData] = useState(toolEventEmptyForm)
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
            dialogText: ['Czy na pewno chcesz usunąć zgłoszenie?'],
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
        mutationFn: postToolEvent,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Nowe zgłoszenie utworzone pomyślnie'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    if (data.id) navigation(`/toolevent/${data.id}`)
                    else navigation(`/events`)
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
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
        mutationFn: updateToolEvent,
        onSuccess(data) {
            queryData.refetch({
                queryKey: ['toolEvent', { id: data.id }],
            })
            setReadonlyMode(true)
            displayInfo({
                dialogText: ['Zmiany w zgłoszeniu zostały zapisane'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas zapisywania zgłoszenia', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationDelete = useMutation({
        mutationFn: deleteToolEvent,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Zgłoszenie zostało usunięte'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    queryData.remove()
                    navigation('/events')
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas usuwania zgłoszenia', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const queryData = useQuery<ToolEvent, AxiosError>(
        ['toolEvent', { id: params.id }],
        async () => getToolEventDetails(params.id && params.id != 'new' ? params.id : ''),
        {
            enabled: !!params.id && params.id != 'new',
        },
    )

    const queryTools = useQuery<Array<Tool>, AxiosError>(['tool-list'], getAllTools)

    const formik = useFormik({
        initialValues: initData,
        validationSchema: toolEventValidationSchema,
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
            formik.setValues(JSON.parse(JSON.stringify(toolEventEmptyForm)))
            setInitData(JSON.parse(JSON.stringify(toolEventEmptyForm)))
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
                                <Typography>Nie znaleziono zgłoszenia</Typography>
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
                                    <FormLabel label="Zgłaszane narzędzie" formik={formik} id={'toolId'} />
                                    {params.id !== 'new' ? (
                                        <>
                                            <FormLabel label="Data zgłoszenia" formik={formik} id={'eventDate'} />
                                            <FormLabel label="Data przyjęcia" formik={formik} id={'movingDate'} />
                                            <FormLabel label="Data zakończenia" formik={formik} id={'movingDate'} />
                                            <FormLabel label="Status" formik={formik} id={'status'} />
                                        </>
                                    ) : null}
                                    <FormLabel label="Opis" formik={formik} id={'description'} />
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
                                        id={'toolId'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={formatArrayToOptions(
                                            'id',
                                            (x: Tool) => x.name + ' - ' + x.code,
                                            queryTools.data,
                                        )}
                                    />
                                    {params.id !== 'new' ? (
                                        /*TODO: Ukrywanie jeśli wartość jest null dla movingDate i completionDate*/
                                        <>
                                            <FormInput
                                                id={'eventDate'}
                                                formik={formik}
                                                readonly
                                                style={{ marginTop: !readonlyMode ? '12px' : '' }}
                                                type="datetime-local"
                                            />
                                            <FormInput
                                                id={'movingDate'}
                                                formik={formik}
                                                readonly
                                                style={{ marginTop: !readonlyMode ? '12px' : '' }}
                                                type="datetime-local"
                                            />
                                            <FormInput
                                                id={'completionDate'}
                                                formik={formik}
                                                readonly
                                                style={{ marginTop: !readonlyMode ? '12px' : '' }}
                                                type="datetime-local"
                                            />
                                            <FormSelect /*TODO: Edycja dostępna dla roli: MANAGER i WAREHOUSE_MANAGER*/
                                                id={'status'}
                                                formik={formik}
                                                readonly={readonlyMode}
                                                options={eventStatusOptions()}
                                            />
                                        </>
                                    ) : null}
                                    <FormInput id={'description'} formik={formik} readonly={readonlyMode} />
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
                                        <Button /*TODO: Dostępne dla roli: MANAGER i WAREHOUSE_MANAGER*/
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

export default ToolEventDetails