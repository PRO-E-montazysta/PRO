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

const ClientDetails = () => {
    const params = useParams()
    const [readonlyMode, setReadonlyMode] = useState(true)
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

    const appSize = useBreakpoints()
    const handleSubmit = (values: any) => {
        if (params.id == 'new') addClientMutation.mutate(values)
        else editClientMutation.mutate(values)
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć klienta?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id && params.id != 'new') deleteClientMutation.mutate(params.id)
            },
        })
    }

    const formik = useFormik({
        initialValues: initData,
        validationSchema: getValidatinSchema(formStructure),
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

    const handleOnEditSuccess = (data: any) => {
        clientData.refetch({
            queryKey: ['client', { id: data.id }],
        })
        setReadonlyMode(true)
    }

    useEffect(() => {
        if (clientData.data) {
            formik.setValues(JSON.parse(JSON.stringify(clientData.data)))
            setInitData(JSON.parse(JSON.stringify(clientData.data)))
        }
    }, [clientData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setReadonlyMode(false)
            formik.setValues(JSON.parse(JSON.stringify(getInitValues(formStructure))))
            setInitData(JSON.parse(JSON.stringify(getInitValues(formStructure))))
        } else setReadonlyMode(true)
    }, [params.id])

    return (
        <FormBox>
            <FormTitle text="Klienci" />
            <FormPaper>
                {queriesStatus.result != 'isSuccess' ? (
                    <QueryBoxStatus queriesStatus={queriesStatus} />
                ) : (
                    <>
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
                    </>
                )}
            </FormPaper>
        </FormBox>
    )
}

export default ClientDetails
