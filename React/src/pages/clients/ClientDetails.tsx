import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'

import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddClient, useClientData, useDeleteClient, useEditClient } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import useBreakpoints from '../../hooks/useBreakpoints'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'

const ClientDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addClientMutation = useAddClient()
    const editClientMutation = useEditClient((data) => handleOnEditSuccess(data))
    const deleteClientMutation = useDeleteClient(() => clientData.remove())
    const clientData = useClientData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus([clientData], [addClientMutation, editClientMutation, deleteClientMutation])

    const handleSubmit = (values: any) => {
        if (pageMode == 'new') addClientMutation.mutate(values)
        else if (pageMode == 'edit') editClientMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć klienta?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id && Number.isInteger(params.id)) deleteClientMutation.mutate(params.id)
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
        clientData.refetch({
            queryKey: ['client', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (clientData.data) {
            formik.setValues(clientData.data)
            setInitData(clientData.data)
        }
    }, [clientData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            setInitData(getInitValues(formStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    return (
        <FormBox>
            <FormTitle text={pageMode == 'new' ? 'Nowy klient' : formik.values['name']} />
            <FormPaper>
                {queriesStatus.result != 'isSuccess' ? (
                    <QueryBoxStatus queriesStatus={queriesStatus} />
                ) : (
                    <>
                        <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
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
    )
}

export default ClientDetails
