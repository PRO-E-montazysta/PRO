import { Button, Paper, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'

import { theme } from '../../themes/baseTheme'

import { useFormStructure } from './helper'
import {
    useAddOrderLocation,
    useAddOrder,
    useDeleteOrder,
    useEditOrderLocation,
    useEditOrder,
    useOrderData,
} from './hooks'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { FormButtons } from '../../components/form/FormButtons'
import { FormStructure } from '../../components/form/FormStructure'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import useBreakpoints from '../../hooks/useBreakpoints'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import FormPaper from '../../components/form/FormPaper'
import FormTitle from '../../components/form/FormTitle'
import FormBox from '../../components/form/FormBox'
import { PageMode } from '../../types/form'
import OrderStagesDetails from '../orderStages/OrderStagesDetails'
import { getRolesFromToken } from '../../utils/token'
import { Role } from '../../types/roleEnum'
import EditIcon from '@mui/icons-material/Edit'
import Localization from '../../components/localization/Localization'
import { Order } from '../../types/model/Order'
import { useAddLocation, useFormStructureLocation, useLocationData } from '../../components/localization/hooks'

const OrderDetails = () => {
    const [userRole, setUserRole] = useState('')

    //parameters from url
    const params = useParams()

    //mode of this page
    const [pageMode, setPageMode] = useState<PageMode>('read')
    //global dialog box
    const { showDialog } = useContext(DialogGlobalContext)
    //custom form structure
    const formStructure = useFormStructure()

    //initial values from custom form structure
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addOrderMutation = useAddOrder((data) => submitLocation(data))
    const editOrderMutation = useEditOrder((data) => submitLocation(data))
    const deleteOrderMutation = useDeleteOrder(() => orderData.remove())
    const orderData = useOrderData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus([orderData], [addOrderMutation, editOrderMutation, deleteOrderMutation])

    const appSize = useBreakpoints()

    useEffect(() => {
        const role = getRolesFromToken()
        console.log(role)
        if (role.length !== 0) setUserRole(role[0])
    }, [])

    const handleSubmit = async (values: any) => {
        //show formik location errors to user
        formikLocation.submitForm()
        //check if there are any error
        if (Object.keys(formikLocation.errors).length == 0) {
            if (pageMode == 'new') addOrderMutation.mutate(values)
            else if (pageMode == 'edit') editOrderMutation.mutate(values)
            else console.warn('Try to submit while read mode')
        }
    }

    const submitLocation = (data: Order) => {
        if (data && data.id) {
            const body = {
                ...formikLocation.values,
                orderId: data.id,
            }
            console.log(body)
            if (pageMode == 'new') addLocationMutation.mutate(body)
            else if (pageMode == 'edit') editLocationMutation.mutate(body)
        }
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć zlecenie?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id && Number.isInteger(params.id)) deleteOrderMutation.mutate(params.id)
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
        orderData.refetch({
            queryKey: ['order', { id: data.orderId }],
        })
        queryLocationData.refetch({
            queryKey: ['location', { id: data.id }],
        })
        setPageMode('read')
    }

    //edit mode
    //populate formik and init values with data from db
    useEffect(() => {
        if (orderData.data) {
            formik.setValues(orderData.data)
            setInitData(orderData.data)
        }
    }, [orderData.data])

    //new mode
    //set readonly mode, populate formik and init data with init values from helper
    useEffect(() => {
        if (params.id === 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            formik.resetForm()
            setInitData(getInitValues(formStructure))
            formikLocation.setValues(getInitValues(formStructureLocation))
            formikLocation.resetForm()
            setInitDataLocation(getInitValues(formStructureLocation))
        } else setPageMode('read')
    }, [params.id])

    const getAddOrderStageButton = () => {
        return userRole === Role.SPECIALIST ? true : false
    }

    const [isAddOrderStageVisible, setIsAddOrderStageVisible] = useState(false)

    const handleAddOrderStage = () => {
        setIsAddOrderStageVisible(!isAddOrderStageVisible)
    }
    //--------------- Location functionality --------------------
    const formStructureLocation = useFormStructureLocation()
    const [initDataLocation, setInitDataLocation] = useState(getInitValues(formStructureLocation))
    const formikLocation = useFormik({
        initialValues: initDataLocation,
        validationSchema: getValidatinSchema(formStructureLocation, pageMode),
        onSubmit: () => {},
    })
    const queryLocationData = useLocationData(
        orderData.data && orderData.data.locationId ? orderData.data.locationId.toString() : '',
    )
    const addLocationMutation = useAddOrderLocation()
    const editLocationMutation = useEditOrderLocation((data) => handleOnEditSuccess(data))

    useEffect(() => {
        if (queryLocationData.data) {
            formikLocation.setValues(queryLocationData.data)
            setInitDataLocation(queryLocationData.data)
        }
    }, [queryLocationData.data])

    return (
        <>
            <FormBox>
                <FormTitle
                    mainTitle={pageMode == 'new' ? 'Nowe zlecenie' : 'Zlecenie'}
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
                                orderStageButton={getAddOrderStageButton()}
                                handleAddOrderStage={handleAddOrderStage}
                                isAddOrderStageVisible={isAddOrderStageVisible}
                            />
                        </>
                    )}
                </FormPaper>
                <OrderStagesDetails isAddOrderStageVisible={isAddOrderStageVisible} />
            </FormBox>
        </>
    )
}

export default OrderDetails
