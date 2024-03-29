import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddTool, useToolData, useDeleteTool, useEditTool } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import Error from '../../components/error/Error'
import DisplayToolHistory from '../../components/history/DisplayToolHistory'
import { Role } from '../../types/roleEnum'
import { isAuthorized } from '../../utils/authorize'

const ToolDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addToolMutation = useAddTool()
    const editToolMutation = useEditTool((data) => handleOnEditSuccess(data))
    const deleteToolMutation = useDeleteTool(() => toolData.remove())
    const toolData = useToolData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus([toolData], [addToolMutation, editToolMutation, deleteToolMutation])

    const handleSubmit = (values: any) => {
        if (pageMode === 'new') addToolMutation.mutate(values)
        else if (pageMode === 'edit') editToolMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć narzędzie?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result === 1 && params.id) deleteToolMutation.mutate(params.id)
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
        toolData.refetch({
            queryKey: ['tool', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (toolData.data) {
            formik.setValues(toolData.data)
            setInitData(toolData.data)
        }
    }, [toolData.data])

    useEffect(() => {
        if (params.id === 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            setInitData(getInitValues(formStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    const canPrintLabel = () => {
        return isAuthorized([Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER])
    }

    return toolData.data?.deleted ? (
        <>
            <Error code={404} message={'Ten obiekt został usunięty'} />
        </>
    ) : (
        <FormBox>
            <FormTitle
                mainTitle={pageMode == 'new' ? 'Nowe narzędzie' : 'Narzędzie'}
                subTitle={pageMode == 'new' ? '' : String(formik.values['name'] + ' - ' + formik.values['code'])}
            />
            <FormPaper>
                {queriesStatus.result !== 'isSuccess' ? (
                    <QueryBoxStatus queriesStatus={queriesStatus} />
                ) : (
                    <>
                        <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
                        {pageMode !== 'new' ? <DisplayToolHistory toolId={params.id}></DisplayToolHistory> : ''}
                        <FormButtons
                            id={params.id}
                            onCancel={handleCancel}
                            onDelete={handleDelete}
                            onEdit={() => setPageMode('edit')}
                            onReset={handleReset}
                            onSubmit={formik.submitForm}
                            readonlyMode={pageMode === 'read'}
                            printLabel={
                                canPrintLabel() && toolData.data ? [toolData.data.name, toolData.data.code] : undefined
                            }
                            editPermissionRoles={[Role.WAREHOUSE_MANAGER]}
                            deletePermissionRoles={[Role.WAREHOUSE_MANAGER]}
                        />
                    </>
                )}
            </FormPaper>
        </FormBox>
    )
}

export default ToolDetails
