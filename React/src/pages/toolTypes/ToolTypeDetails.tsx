import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddToolType, useToolTypeData, useDeleteToolType, useEditToolType } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import Error from '../../components/error/Error'
import { Role } from '../../types/roleEnum'

const ToolTypeDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addToolTypeMutation = useAddToolType()
    const editToolTypeMutation = useEditToolType((data) => handleOnEditSuccess(data))
    const deleteToolTypeMutation = useDeleteToolType(() => toolTypeData.remove())
    const toolTypeData = useToolTypeData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [toolTypeData],
        [addToolTypeMutation, editToolTypeMutation, deleteToolTypeMutation],
    )

    const handleSubmit = (values: any) => {
        if (pageMode == 'new') addToolTypeMutation.mutate(values)
        else if (pageMode == 'edit') editToolTypeMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć typ narzędzia?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id) deleteToolTypeMutation.mutate(params.id)
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
        toolTypeData.refetch({
            queryKey: ['tooltype', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (toolTypeData.data) {
            formik.setValues(toolTypeData.data)
            setInitData(toolTypeData.data)
        }
    }, [toolTypeData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            setInitData(getInitValues(formStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    return toolTypeData.data?.deleted ? (
        <>
            <Error code={404} message={'Ten obiekt został usunięty'} />
        </>
    ) : (
        <FormBox>
            <FormTitle
                mainTitle={pageMode == 'new' ? 'Nowy typ narzędzia' : 'Typ narzędzia'}
                subTitle={pageMode == 'new' ? null : formik.values['name']}
            />
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
                            editPermissionRoles={[Role.WAREHOUSE_MANAGER]}
                            deletePermissionRoles={[Role.WAREHOUSE_MANAGER]}
                        />
                    </>
                )}
            </FormPaper>
        </FormBox>
    )
}

export default ToolTypeDetails
