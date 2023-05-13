import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddUnavailability, useUnavailabilityData, useDeleteUnavailability, useEditUnavailability } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import { typeOfUnavailabilityName } from '../../helpers/enum.helper'
import Error from '../../components/error/Error'

const UnavailabilityDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addUnavailabilityMutation = useAddUnavailability()
    const editUnavailabilityMutation = useEditUnavailability((data) => handleOnEditSuccess(data))
    const deleteUnavailabilityMutation = useDeleteUnavailability(() => unavailabilityData.remove())
    const unavailabilityData = useUnavailabilityData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [unavailabilityData],
        [addUnavailabilityMutation, editUnavailabilityMutation, deleteUnavailabilityMutation],
    )

    const handleSubmit = (values: any) => {
        if (pageMode == 'new') addUnavailabilityMutation.mutate(values)
        else if (pageMode == 'edit') editUnavailabilityMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć nieobecność?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id) deleteUnavailabilityMutation.mutate(params.id)
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
        unavailabilityData.refetch({
            queryKey: ['unavailability', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (unavailabilityData.data) {
            formik.setValues(unavailabilityData.data)
            setInitData(unavailabilityData.data)
        }
    }, [unavailabilityData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            setInitData(getInitValues(formStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    return unavailabilityData.data?.deleted ? (
        <>
            <Error code={404} message={'Ten obiekt został usunięty'} />
        </>
    ) : (
        <FormBox>
            <FormTitle
                mainTitle={pageMode == 'new' ? 'Nowa nieobecność' : 'Nieobecność'}
                subTitle={pageMode == 'new' ? '' : typeOfUnavailabilityName(formik.values['typeOfUnavailability'])}
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
                        />
                    </>
                )}
            </FormPaper>
        </FormBox>
    )
}

export default UnavailabilityDetails
