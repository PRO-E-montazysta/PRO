import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import {
    useAddWarehouse,
    useWarehouseData,
    useDeleteWarehouse,
    useEditWarehouse,
    useAddWarehouseLocation,
    useEditWarehouseLocation,
} from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import { useFormStructureLocation, useLocationData } from '../../components/localization/hooks'
import { Warehouse } from '../../types/model/Warehouse'
import Localization from '../../components/localization/Localization'
import Error from '../../components/error/Error'

const WarehouseDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addWarehouseMutation = useAddWarehouse((data) => submitLocation(data))
    const editWarehouseMutation = useEditWarehouse((data) => submitLocation(data))
    const deleteWarehouseMutation = useDeleteWarehouse(() => warehouseData.remove())
    const warehouseData = useWarehouseData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [warehouseData],
        [addWarehouseMutation, editWarehouseMutation, deleteWarehouseMutation],
    )

    const handleSubmit = (values: any) => {
        //show formik location errors to user
        formikLocation.submitForm()
        //check if there are any error
        if (Object.keys(formikLocation.errors).length == 0) {
            if (pageMode == 'new') addWarehouseMutation.mutate(values)
            else if (pageMode == 'edit') editWarehouseMutation.mutate(values)
            else console.warn('Try to submit while read mode')
        }
    }

    const submitLocation = (data: Warehouse) => {
        if (data && data.id) {
            const body = {
                ...formikLocation.values,
                warehouseId: data.id,
            }
            if (pageMode == 'new') addLocationMutation.mutate(body)
            else if (pageMode == 'edit') editLocationMutation.mutate(body)
        }
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć magazyn?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id) deleteWarehouseMutation.mutate(params.id)
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
        formikLocation.resetForm()
        formikLocation.setValues(initDataLocation)
    }

    const handleCancel = () => {
        handleReset()
        setPageMode('read')
    }

    const handleOnEditSuccess = (data: any) => {
        warehouseData.refetch({
            queryKey: ['warehouse', { id: data.warehouseId }],
        })
        queryLocationData.refetch({
            queryKey: ['location', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (warehouseData.data) {
            formik.setValues(warehouseData.data)
            setInitData(warehouseData.data)
        }
    }, [warehouseData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            formik.resetForm()
            setInitData(getInitValues(formStructure))
            formikLocation.setValues(getInitValues(formStructureLocation))
            formikLocation.resetForm()
            setInitDataLocation(getInitValues(formStructureLocation))
        } else setPageMode('read')
    }, [params.id])

    //--------------- Location functionality --------------------
    const formStructureLocation = useFormStructureLocation()
    const [initDataLocation, setInitDataLocation] = useState(getInitValues(formStructureLocation))
    const formikLocation = useFormik({
        initialValues: initDataLocation,
        validationSchema: getValidatinSchema(formStructureLocation, pageMode),
        onSubmit: () => {},
    })
    const queryLocationData = useLocationData(
        warehouseData.data && warehouseData.data.locationId ? warehouseData.data.locationId.toString() : '',
    )
    const addLocationMutation = useAddWarehouseLocation()
    const editLocationMutation = useEditWarehouseLocation((data) => handleOnEditSuccess(data))

    useEffect(() => {
        if (queryLocationData.data) {
            formikLocation.setValues(queryLocationData.data)
            setInitDataLocation(queryLocationData.data)
        }
    }, [queryLocationData.data])

    return warehouseData.data?.deleted ? (
        <>
            <Error code={404} message={'Ten obiekt został usunięty'} />
        </>
    ) : (
        <FormBox>
            <FormTitle
                mainTitle={pageMode == 'new' ? 'Nowy magazyn' : 'Magazyn'}
                subTitle={pageMode == 'new' ? null : formik.values['name']}
            />
            <FormPaper>
                {queriesStatus.result != 'isSuccess' ? (
                    <QueryBoxStatus queriesStatus={queriesStatus} />
                ) : (
                    <>
                        <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
                        <Localization
                            title="Lokalizacja"
                            formik={formikLocation}
                            formStructure={formStructureLocation}
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
    )
}

export default WarehouseDetails
