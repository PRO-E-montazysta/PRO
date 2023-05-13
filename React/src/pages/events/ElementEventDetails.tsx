import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useElementEventFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddElementEvent, useDeleteElementEvent, useEditElementEvent, useElementEventData } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormTitle from '../../components/form/FormTitle'
import FormBox from '../../components/form/FormBox'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { getFilteredEvents } from '../../api/event.api'
import { Tool } from '../../types/model/Tool'
import { getAllTools } from '../../api/tool.api'
import { getAllElements } from '../../api/element.api'
import { Element } from '../../types/model/Element'

const ElementEventDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const elementEventFormStructure = useElementEventFormStructure()
    const [initData, setInitData] = useState(getInitValues(elementEventFormStructure))

    //mutations and queries
    const addElementEventMutation = useAddElementEvent()
    const editElementEventMutation = useEditElementEvent((data) => handleOnEditSuccess(data))
    const deleteElementEventMutation = useDeleteElementEvent(() => elementEventData.remove())
    const elementEventData = useElementEventData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [elementEventData],
        [addElementEventMutation, editElementEventMutation, deleteElementEventMutation],
    )

    const handleSubmit = (values: any) => {
        if (pageMode == 'new') addElementEventMutation.mutate(values)
        else if (pageMode == 'edit') editElementEventMutation.mutate(values)
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
                if (result == 1 && params.id) deleteElementEventMutation.mutate(params.id)
            },
        })
    }

    const formik = useFormik({
        initialValues: initData,
        validationSchema: getValidatinSchema(elementEventFormStructure, pageMode),
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
        elementEventData.refetch({
            queryKey: ['ElementEvent', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (elementEventData.data) {
            formik.setValues(elementEventData.data)
            setInitData(elementEventData.data)
        }
    }, [elementEventData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(elementEventFormStructure))
            setInitData(getInitValues(elementEventFormStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    const queryElements = useQuery<Array<Element>, AxiosError>(['element-list'], getAllElements, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    return (
        <>
            <FormBox>
                <FormTitle
                    mainTitle={pageMode == 'new' ? 'Nowa usterka elementu' : 'Usterka elementu'}
                    subTitle={
                        pageMode == 'new'
                            ? ''
                            : String(
                                  queryElements.data
                                      ?.filter((f) => f.id == formik.values['elementId'])
                                      .map((x) => x.name + ' - ' + x.code),
                              )
                    }
                />
                <FormPaper>
                    {queriesStatus.result != 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
                            <FormStructure
                                formStructure={elementEventFormStructure}
                                formik={formik}
                                pageMode={pageMode}
                            />
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

export default ElementEventDetails
