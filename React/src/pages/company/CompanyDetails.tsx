import { Button, CircularProgress, Divider, Grid, Paper, TextField, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { AxiosError } from 'axios'
import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
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
import { addNewCompanyForm, emptyForm, useFormStructure, validationSchemaPost, validationSchemaUpdate } from './helper'
import FormInput from '../../components/form/FormInput'
import FormLabel from '../../components/form/FormLabel'
import DialogInfo, { DialogInfoParams } from '../../components/dialogInfo/DialogInfo'

import Card from '@mui/material/Card'
import ExpandMore from '../../components/expandMore/ExpandMore'
import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar'
import FormSelect from '../../components/form/FormSelect'
import { companyStatusOptions } from '../../helpers/enum.helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddCompany, useCompanyData, useDeleteCompany, useEditCompany } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import { PageMode } from '../../types/form'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'

const CompanyDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addCompanyMutation = useAddCompany()
    const editCompanyMutation = useEditCompany((data) => handleOnEditSuccess(data))
    const deleteCompanyMutation = useDeleteCompany(() => companyData.remove())
    const companyData = useCompanyData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [companyData],
        [addCompanyMutation, editCompanyMutation, deleteCompanyMutation],
    )

    const handleSubmit = (values: any) => {
        if (pageMode == 'new') addCompanyMutation.mutate(values)
        else if (pageMode == 'edit') editCompanyMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć firmę?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id && Number.isInteger(params.id)) deleteCompanyMutation.mutate(params.id)
            },
        })
    }

    const formik = useFormik({
        initialValues: initData,
        validationSchema: getValidatinSchema(formStructure, pageMode),
        onSubmit: handleSubmit,
    })

    const handleReset = () => {
        formik.resetForm()
        formik.setValues(initData)
    }

    const handleCancel = () => {
        handleReset()
        setPageMode('read')
    }

    const handleOnEditSuccess = (data: any) => {
        companyData.refetch({
            queryKey: ['company', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (companyData.data) {
            formik.setValues(companyData.data)
            setInitData(companyData.data)
        }
    }, [companyData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            setInitData(getInitValues(formStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    const addAdminCardContent = () => {
        return (
            <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                    <FormInput formik={formik} id="firstName" readonly={pageMode == 'read'} label="Imię" type="text" />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <FormInput
                        formik={formik}
                        id="lastName"
                        readonly={pageMode == 'read'}
                        label="Nazwisko"
                        type="text"
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <FormInput formik={formik} id="email" readonly={pageMode == 'read'} label="Email" type="text" />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <FormInput
                        formik={formik}
                        id="password"
                        readonly={pageMode == 'read'}
                        label="Hasło"
                        type="password"
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <FormInput
                        formik={formik}
                        id="username"
                        readonly={pageMode == 'read'}
                        label="Nazwa użytkownika"
                        type="text"
                    />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <FormInput formik={formik} id="phone" readonly={pageMode == 'read'} label="Telefon" type="text" />
                </Grid>
                <Grid item xs={12} sm={6}>
                    <FormInput formik={formik} id="pesel" readonly={pageMode == 'read'} label="Pesel" type="text" />
                </Grid>
            </Grid>
        )
    }

    return (
        <>
            <FormBox>
                <FormTitle text="Firma" />
                <FormPaper>
                    {queriesStatus.result != 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
                            <FormStructure
                                formStructure={formStructure}
                                formik={formik}
                                readonlyMode={pageMode == 'read'}
                                pageMode={pageMode}
                            />
                            {pageMode == 'new' ? (
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
                            <FormButtons
                                id={params.id}
                                onCancel={handleCancel}
                                onDelete={handleDelete}
                                onEdit={() => setPageMode('edit')}
                                onReset={handleReset}
                                onSubmit={formik.submitForm}
                                readonlyMode={pageMode == 'read'}
                            />
                        </>
                    )}
                </FormPaper>
            </FormBox>
            {/* 
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
                                    {params.id !== 'new' ? (
                                        <FormLabel label="Data utworzenia" formik={formik} id={'createdAt'} />
                                    ) : null}
                                    <FormLabel label="Status" formik={formik} id={'status'} />
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
                                    {params.id !== 'new' ? (
                                        <FormInput
                                            id={'createdAt'}
                                            formik={formik}
                                            readonly
                                            style={{ marginTop: !readonlyMode ? '12px' : '' }}
                                            type="datetime-local"
                                        />
                                    ) : null}
                                    <FormSelect
                                        id={'status'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={companyStatusOptions()}
                                    />
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
                                            onClick={() => setPageMode('edit')}
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
            {dialog.open && <DialogInfo {...dialog} />} */}
        </>
    )
}

export default CompanyDetails
