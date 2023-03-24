import { Button, CircularProgress, Divider, Grid, Paper, TextField, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { AxiosError } from 'axios'
import { useFormik } from 'formik'
import { useEffect, useState } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate, useParams } from 'react-router-dom'
import { deleteUser, getUserDetails, postUser, updateUser } from '../../api/user.api'
import { theme } from '../../themes/baseTheme'
import { AppUser } from '../../types/model/AppUser'

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

const UserDetails = () => {
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
            dialogText: ['Czy na pewno chcesz usunąć użytkownika?'],
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
        mutationFn: postUser,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Nowy użytkownik utworzony pomyślnie'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    if (data.id) navigation(`/users/${data.id}`)
                    else navigation(`/users`)
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas tworzenia nowego użytkownika', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationUpdate = useMutation({
        mutationFn: updateUser,
        onSuccess(data) {
            queryData.refetch({
                queryKey: ['User', { id: data.id }],
            })
            setReadonlyMode(true)
            displayInfo({
                dialogText: ['Zmiany w użytkowniku zostały zapisane'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas zapisywania użytkownika', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationDelete = useMutation({
        mutationFn: deleteUser,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Użytkownik został usunięty'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    queryData.remove()
                    navigation('/companies')
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas usuwania użytkownika', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const queryData = useQuery<AppUser, AxiosError>(
        ['User', { id: params.id }],
        async () => getUserDetails(params.id && params.id != 'new' ? params.id : ''),
        {
            enabled: !!params.id && params.id != 'new',
        },
    )

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
                            {queryData.isLoading ? <CircularProgress /> : <Typography>Nie znaleziono użytkownika</Typography>}
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
                                    <FormLabel label="Imię użytkownika" formik={formik} id={'FirstName'} />
                                    <FormLabel label="Nazwisko użytkownika" formik={formik} id={'LastName'} />
                                    <FormLabel label="Hasło" formik={formik} id={'password'} />
                                    <FormLabel label="Email" formik={formik} id={'email'} />
                                </Grid>
                                <Divider
                                    orientation="vertical"
                                    variant="middle"
                                    style={{ borderColor: theme.palette.primary.main }}
                                    sx={{ mr: '-1px' }}
                                    flexItem
                                />
                                <Grid item xs={6}>
                                    <FormInput id={'FirstName'} formik={formik} readonly={readonlyMode} />
                                    <FormInput id={'LastName'} formik={formik} readonly={readonlyMode} />
                                    <FormInput id={'password'} formik={formik} readonly={readonlyMode} />
                                    <FormInput id={'email'} formik={formik} readonly={readonlyMode} />
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

export default UserDetails
