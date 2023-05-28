import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useElementInWarehouseData, useEditElementInWarehouse } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import { useElementData } from '../elements/hooks'

const ElementInWarehouseDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const editElementInWarehouseMutation = useEditElementInWarehouse((data) => handleOnEditSuccess(data))
    const elementInWarehouseData = useElementInWarehouseData(params.id)
    const elementData = useElementData(String(elementInWarehouseData.data?.elementId))
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus([elementInWarehouseData], [editElementInWarehouseMutation])

    const handleSubmit = (values: any) => {
        if (pageMode == 'edit') editElementInWarehouseMutation.mutate(values)
        else console.warn('Try to submit while read mode')
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
        elementInWarehouseData.refetch({
            queryKey: ['element-in-warehouse', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (elementInWarehouseData.data) {
            formik.setValues(elementInWarehouseData.data)
            setInitData(elementInWarehouseData.data)
        }
    }, [elementInWarehouseData.data])

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
            <FormTitle mainTitle="Stan magazynowy" subTitle={elementData.data?.name + ' - ' + elementData.data?.code} />
            <FormPaper>
                {queriesStatus.result != 'isSuccess' ? (
                    <QueryBoxStatus queriesStatus={queriesStatus} />
                ) : (
                    <>
                        <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
                        <FormButtons
                            id={params.id}
                            onCancel={handleCancel}
                            onEdit={() => setPageMode('edit')}
                            onReset={handleReset}
                            onSubmit={formik.submitForm}
                            readonlyMode={pageMode == 'read'}
                            //TODO: Edycja tylko dla [Role.WAREHOUSE_MANAGER, Role.WAREHOUSE_MAN]
                        />
                    </>
                )}
            </FormPaper>
        </FormBox>
    )
}

export default ElementInWarehouseDetails
