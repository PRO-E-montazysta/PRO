import { Box, Grid } from '@mui/material'
import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
import FormInput from '../../components/form/FormInput'
import Card from '@mui/material/Card'
import ExpandMore from '../../components/expandMore/ExpandMore'
import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar'
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
import { useInputWidth } from '../../hooks/useInputWidth'

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

    const inputWidth = useInputWidth()

    const addAdminCardContent = () => {
        return (
            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: '15px' }}>
                <FormInput
                    style={{ width: inputWidth }}
                    formik={formik}
                    id="firstName"
                    readonly={pageMode == 'read'}
                    label="Imię"
                    type="text"
                />
                <FormInput
                    style={{ width: inputWidth }}
                    formik={formik}
                    id="lastName"
                    readonly={pageMode == 'read'}
                    label="Nazwisko"
                    type="text"
                />
                <FormInput
                    style={{ width: inputWidth }}
                    formik={formik}
                    id="email"
                    readonly={pageMode == 'read'}
                    label="Email"
                    type="text"
                />
                <FormInput
                    style={{ width: inputWidth }}
                    formik={formik}
                    id="password"
                    readonly={pageMode == 'read'}
                    label="Hasło"
                    type="password"
                />
                <FormInput
                    style={{ width: inputWidth }}
                    formik={formik}
                    id="username"
                    readonly={pageMode == 'read'}
                    label="Nazwa użytkownika"
                    type="text"
                />
                <FormInput
                    style={{ width: inputWidth }}
                    formik={formik}
                    id="phone"
                    readonly={pageMode == 'read'}
                    label="Telefon"
                    type="text"
                />
                <FormInput
                    style={{ width: inputWidth }}
                    formik={formik}
                    id="pesel"
                    readonly={pageMode == 'read'}
                    label="Pesel"
                    type="text"
                />
            </Box>
        )
    }

    return (
        <>
            <FormBox>
                <FormTitle
                    mainTitle={pageMode == 'new' ? 'Nowa firma' : 'Firma'}
                    subTitle={pageMode == 'new' ? null : formik.values['companyName']}
                />
                <FormPaper>
                    {queriesStatus.result != 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
                            <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
                            {pageMode == 'new' ? (
                                <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                                    <Card sx={{ width: '100%', left: '50%' }}>
                                        <ExpandMore
                                            titleIcon={<PermContactCalendarIcon />}
                                            title="Dane dministratora firmy"
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
        </>
    )
}

export default CompanyDetails
