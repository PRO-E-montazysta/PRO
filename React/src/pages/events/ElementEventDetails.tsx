import { Button, CircularProgress, Divider, Grid, Paper, TextField, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { AxiosError } from 'axios'
import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate, useParams } from 'react-router-dom'
import { deleteElementEvent, getElementEventDetails, postElementEvent, updateElementEvent } from '../../api/event.api'
import { theme } from '../../themes/baseTheme'
import SaveIcon from '@mui/icons-material/Save'
import ReplayIcon from '@mui/icons-material/Replay'
import CloseIcon from '@mui/icons-material/Close'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import { elementEventEmptyForm, elementEventValidationSchema, useFormStructure } from './helper'
import FormInput from '../../components/form/FormInput'
import FormLabel from '../../components/form/FormLabel'
import FormSelect from '../../components/form/FormSelect'
import DialogInfo, { DialogInfoParams } from '../../components/dialogInfo/DialogInfo'
import { eventStatusOptions } from '../../helpers/enum.helper'
import { ElementEvent } from '../../types/model/ElementEvent'
import { Element } from '../../types/model/Element'
import { getAllElements } from '../../api/element.api'
import { formatArrayToOptions } from '../../helpers/format.helper'
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

const ElementEventDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addElementEventMutation = useAddElementEvent()
    const editElementEventMutation = useEditElementEvent((data) => handleOnEditSuccess(data))
    const deleteElementEventMutation = useDeleteElementEvent(() => ElementEventData.remove())
    const ElementEventData = useElementEventData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [ElementEventData],
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
                if (result == 1 && params.id && Number.isInteger(params.id))
                    deleteElementEventMutation.mutate(params.id)
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
        ElementEventData.refetch({
            queryKey: ['ElementEvent', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (ElementEventData.data) {
            formik.setValues(ElementEventData.data)
            setInitData(ElementEventData.data)
        }
    }, [ElementEventData.data])

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
        <>
            <FormBox>
                <FormTitle text="Usterka elementu" />
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
        </>
    )
}

export default ElementEventDetails
