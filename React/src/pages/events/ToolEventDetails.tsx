import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useToolEventFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddToolEvent, useDeleteToolEvent, useEditToolEvent, useToolEventData } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormTitle from '../../components/form/FormTitle'
import FormBox from '../../components/form/FormBox'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import { useQuery } from 'react-query'
import { Tool } from '../../types/model/Tool'
import { AxiosError } from 'axios'
import { getAllTools } from '../../api/tool.api'
import DisplayToolHistory from '../../components/toolHistory/DisplayToolHistory'
import { Role } from '../../types/roleEnum'
import { getAboutMeInfo } from '../../api/employee.api'

const ToolEventDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const toolEventFormStructure = useToolEventFormStructure()
    const [initData, setInitData] = useState(getInitValues(toolEventFormStructure))

    //mutations and queries
    const addToolEventMutation = useAddToolEvent()
    const editToolEventMutation = useEditToolEvent((data) => handleOnEditSuccess(data))
    const deleteToolEventMutation = useDeleteToolEvent(() => toolEventData.remove())
    const toolEventData = useToolEventData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [toolEventData],
        [addToolEventMutation, editToolEventMutation, deleteToolEventMutation],
    )

    const handleSubmit = (values: any) => {
        if (pageMode === 'new') addToolEventMutation.mutate(values)
        else if (pageMode === 'edit') editToolEventMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć zgłoszenie?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result === 1 && params.id && Number.isInteger(params.id)) deleteToolEventMutation.mutate(params.id)
            },
        })
    }

    const formik = useFormik({
        initialValues: initData,
        validationSchema: getValidatinSchema(toolEventFormStructure, pageMode),
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
        toolEventData.refetch({
            queryKey: ['ToolEvent', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (toolEventData.data) {
            formik.setValues(toolEventData.data)
            setInitData(toolEventData.data)
        }
    }, [toolEventData.data])

    useEffect(() => {
        if (params.id === 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(toolEventFormStructure))
            setInitData(getInitValues(toolEventFormStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    const queryTools = useQuery<Array<Tool>, AxiosError>(['tool-list'], getAllTools, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    const aboutMeQuery = useQuery<any, AxiosError>(['about-me'], async () => getAboutMeInfo())

    return (
        <>
            <FormBox>
                <FormTitle
                    mainTitle={pageMode == 'new' ? 'Nowa usterka narzędzia' : 'Usterka narzędzia'}
                    subTitle={
                        pageMode == 'new'
                            ? ''
                            : String(
                                  queryTools.data
                                      ?.filter((f) => f.id == formik.values['toolId'])
                                      .map((x) => x.name + ' - ' + x.code),
                              )
                    }
                />
                <FormPaper>
                    {queriesStatus.result !== 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
                            <FormStructure formStructure={toolEventFormStructure} formik={formik} pageMode={pageMode} />
                            {pageMode !== 'new' ? (
                                <DisplayToolHistory toolId={formik.values['toolId']}></DisplayToolHistory>
                            ) : (
                                ''
                            )}
                            <FormButtons
                                id={params.id}
                                onCancel={handleCancel}
                                onDelete={handleDelete}
                                onEdit={() => setPageMode('edit')}
                                onReset={handleReset}
                                onSubmit={formik.submitForm}
                                readonlyMode={pageMode === 'read'}
                                editPermissionRoles={
                                    aboutMeQuery.data.userId == formik.values['createdById'] &&
                                    formik.values['status'] == 'CREATED'
                                        ? [Role.MANAGER, Role.WAREHOUSE_MANAGER, Role.FOREMAN]
                                        : [Role.MANAGER, Role.WAREHOUSE_MANAGER]
                                }
                                deletePermissionRoles={
                                    aboutMeQuery.data.userId == formik.values['createdById'] &&
                                    formik.values['status'] == 'CREATED'
                                        ? [Role.MANAGER, Role.WAREHOUSE_MANAGER, Role.FOREMAN]
                                        : [Role.MANAGER, Role.WAREHOUSE_MANAGER]
                                }
                            />
                        </>
                    )}
                </FormPaper>
            </FormBox>
        </>
    )
}

export default ToolEventDetails
