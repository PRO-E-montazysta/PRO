import { Button, CircularProgress, Divider, Grid, Paper, TextField, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { AxiosError } from 'axios'
import { useFormik } from 'formik'
import { useEffect, useState } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate, useParams } from 'react-router-dom'
import { deleteCompany, getCompanyDetails, postCompany, updateCompany } from '../../api/company.api'
import { theme } from '../../themes/baseTheme'
import { Company } from '../../types/model/Company'

import SaveIcon from '@mui/icons-material/Save'
import ReplayIcon from '@mui/icons-material/Replay'
import CloseIcon from '@mui/icons-material/Close'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import { addNewCompanyForm, emptyForm, validationSchemaPost, validationSchemaUpdate } from './helper'
import FormInput from '../../components/form/FormInput'
import FormLabel from '../../components/form/FormLabel'
import DialogInfo, { DialogInfoParams } from '../../components/dialogInfo/DialogInfo'

import Card from '@mui/material/Card'
import ExpandMore from '../../components/expandMore/ExpandMore'
import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar'

const CompanyDetails = () => {
    const params = useParams()
    const [readonlyMode, setReadonlyMode] = useState(true)
    const [initData, setInitData] = useState(emptyForm)
    const navigation = useNavigate()
    const [validationSchemaPrepared, setValidationSchemaPrepared] = useState<any>(validationSchemaPost)

    const [dialog, setDialog] = useState({
        open: false,
        dialogText: [''],
        confirmAction: () => {},
        confirmLabel: '',
    })

    const queryData = useQuery<Company, AxiosError>(
        ['company', { id: params.id }],
        async () => getCompanyDetails(params.id && params.id != 'new' ? params.id : ''),
        {
            enabled: !!params.id && params.id != 'new',
        },
    )

    useEffect(() => {
        if (queryData.data) {
            formik.setValues(JSON.parse(JSON.stringify(queryData.data)))
            setInitData(JSON.parse(JSON.stringify(queryData.data)))
            setValidationSchemaPrepared(validationSchemaUpdate)
        }
    }, [queryData.data])

    useEffect(() => {
        if (params.id === 'new') {
            setReadonlyMode(false)
            formik.setValues(JSON.parse(JSON.stringify(addNewCompanyForm)))
            setInitData(JSON.parse(JSON.stringify(addNewCompanyForm)))
        } else setReadonlyMode(true)
    }, [params.id])

    const mutationPost = useMutation({
        mutationFn: postCompany,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Nowa firma utworzona pomyślnie'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    if (data.id) navigation(`/companies/${data.id}`)
                    else navigation(`/companies`)
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: any) {
            displayInfo({
                dialogText: ['Błąd poczas tworzenia nowej firmy', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationUpdate = useMutation({
        mutationFn: updateCompany,
        onSuccess(data) {
            queryData.refetch({
                queryKey: ['company', { id: data.id }],
            })
            setReadonlyMode(true)
            displayInfo({
                dialogText: ['Zmiany w firmie zostały zapisane'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas zapisywania firmy', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationDelete = useMutation({
        mutationFn: deleteCompany,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Firma została usunięta'],
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
                dialogText: ['Błąd poczas usuwania firmy', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const handleSubmit = () => {
        if (params.id === 'new') mutationPost.mutate(JSON.parse(JSON.stringify(formik.values)))
        else {
            mutationUpdate.mutate(JSON.parse(JSON.stringify(formik.values)))
        }
    }

    const handleDelete = () => {
        displayInfo({
            dialogText: ['Czy na pewno chcesz usunąć firmę?'],
            confirmAction: () => {
                setDialog({ ...dialog, open: false })
                if (formik.values.id) {
                    mutationDelete.mutate(formik.values.id)
                }
            },
            confirmLabel: 'Usuń',
            cancelAction: () => {
                setDialog({ ...dialog, open: false })
            },
            cancelLabel: 'Anuluj',
        })
    }

    const handleReset = () => {
        formik.resetForm()
        formik.setValues(JSON.parse(JSON.stringify(initData)))
    }

    const handleCancel = () => {
        handleReset()
        setReadonlyMode(true)
    }

    const displayInfo = (params: DialogInfoParams) => {
        setDialog({
            ...params,
            open: true,
        })
    }

    const formik = useFormik({
        initialValues: initData,
        validationSchema: validationSchemaPrepared,
        onSubmit: handleSubmit,
    })

    const addAdminCardContent = () => {
        return (
            <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                    <TextField
                        sx={{ width: '100%' }}
                        required
                        label="Imię"
                        id="firstName"
                        onChange={formik.handleChange}
                        error={formik.touched.firstName && Boolean(formik.errors.firstName)}
                        helperText={formik.touched.firstName && formik.errors.firstName}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        sx={{ width: '100%' }}
                        required
                        label="Nazwisko"
                        id="lastName"
                        onChange={formik.handleChange}
                        error={formik.touched.lastName && Boolean(formik.errors.lastName)}
                        helperText={formik.touched.lastName && formik.errors.lastName}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        sx={{ width: '100%' }}
                        required
                        label="Email"
                        id="email"
                        onChange={formik.handleChange}
                        error={formik.touched.email && Boolean(formik.errors.email)}
                        helperText={formik.touched.email && formik.errors.email}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        sx={{ width: '100%' }}
                        required
                        label="Hasło"
                        type="password"
                        id="password"
                        onChange={formik.handleChange}
                        error={formik.touched.password && Boolean(formik.errors.password)}
                        helperText={formik.touched.password && formik.errors.password}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        sx={{ width: '100%' }}
                        required
                        label="Nazwa użytkownika"
                        id="username"
                        onChange={formik.handleChange}
                        error={formik.touched.username && Boolean(formik.errors.username)}
                        helperText={formik.touched.username && formik.errors.username}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        sx={{ width: '100%' }}
                        required
                        label="Telefon"
                        id="phone"
                        onChange={formik.handleChange}
                        error={formik.touched.phone && Boolean(formik.errors.phone)}
                        helperText={formik.touched.phone && formik.errors.phone}
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField
                        sx={{ width: '100%' }}
                        required
                        label="Pesel"
                        id="pesel"
                        onChange={formik.handleChange}
                        error={formik.touched.pesel && Boolean(formik.errors.pesel)}
                        helperText={formik.touched.pesel && formik.errors.pesel}
                    />
                </Grid>
            </Grid>
        )
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
                            {queryData.isLoading ? <CircularProgress /> : <Typography>Nie znaleziono firmy</Typography>}
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
                                    <FormLabel label="Nazwa firmy" formik={formik} id={'companyName'} />
                                    <FormLabel label="Uzasadnienie statusu" formik={formik} id={'statusReason'} />
                                </Grid>
                                <Divider
                                    orientation="vertical"
                                    variant="middle"
                                    style={{ borderColor: theme.palette.primary.main }}
                                    sx={{ mr: '-1px' }}
                                    flexItem
                                />
                                <Grid item xs={6}>
                                    <FormInput id={'companyName'} formik={formik} readonly={readonlyMode} />
                                    <FormInput id={'statusReason'} formik={formik} readonly={readonlyMode} />
                                </Grid>
                                {params.id === 'new' ? (
                                    <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                                        <Card sx={{ width: '100%', left: '50%' }}>
                                            <ExpandMore
                                                titleIcon={<PermContactCalendarIcon />}
                                                title="Dodaj administratora firmy"
                                                cardContent={addAdminCardContent()}
                                            />
                                        </Card>
                                    </Grid>
                                ) : null}
                            </Grid>
                            <Box sx={{ margin: '20px', gap: '20px', display: 'flex', flexDirection: 'row-reverse' }}>
                                {readonlyMode && params.id !== 'new' ? (
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
                                        {params.id !== 'new' && (
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

export default CompanyDetails
