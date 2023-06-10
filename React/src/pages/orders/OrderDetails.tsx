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
    useOrderNextStatus,
    useOrderPreviousStatus,
    useOrderStages,
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
import { isAuthorized } from '../../utils/authorize'

import { OrderStage } from '../../types/model/OrderStage'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getAllOrderStagesForOrder } from '../../api/orderStage.api'
import { EventInput } from '@fullcalendar/core'
import moment from 'moment'
import Planner from './Planner'

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
    const orderNextStatusMutation = useOrderNextStatus(() => {
        orderData.refetch({
            queryKey: ['order', { id: params.id }],
        })
    })
    const orderPreviousStatusMutation = useOrderPreviousStatus(() => {
        orderData.refetch({
            queryKey: ['order', { id: params.id }],
        })
    })

    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [orderData],
        [addOrderMutation, editOrderMutation, deleteOrderMutation, orderNextStatusMutation],
    )

    const appSize = useBreakpoints()

    useEffect(() => {
        const role = getRolesFromToken()
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

    const handleNextStatus = () => {
        showDialog({
            title: 'Czy na pewno chcesz zmienić status zlecenia na następny?',
            btnOptions: [
                { text: 'Tak', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id) orderNextStatusMutation.mutate(params.id)
            },
        })
    }

    const handlePreviousStatus = () => {
        showDialog({
            title: 'Czy na pewno chcesz cofnąć status zlecenia?',
            btnOptions: [
                { text: 'Tak', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id) orderPreviousStatusMutation.mutate(params.id)
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
        return userRole === Role.SPECIALIST ? orderData.data?.status == 'PLANNING' : false
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

    const canChangeToNextStatus = () => {
        return (
            (orderData.data?.status == 'CREATED' && isAuthorized([Role.SALES_REPRESENTATIVE])) ||
            (orderData.data?.status == 'PLANNING' && isAuthorized([Role.SPECIALIST])) ||
            (orderData.data?.status == 'TO_ACCEPT' && isAuthorized([Role.MANAGER])) ||
            (orderData.data?.status == 'ACCEPTED' && isAuthorized([Role.FOREMAN])) ||
            (orderData.data?.status == 'IN_PROGRESS' && isAuthorized([Role.FOREMAN]))
        )
    }

    const canChangeToPreviousStatus = () => {
        return (
            (orderData.data?.status == 'PLANNING' && isAuthorized([Role.SPECIALIST])) ||
            (orderData.data?.status == 'TO_ACCEPT' && isAuthorized([Role.MANAGER])) ||
            (orderData.data?.status == 'ACCEPTED' && isAuthorized([Role.FOREMAN])) ||
            (orderData.data?.status == 'IN_PROGRESS' && isAuthorized([Role.FOREMAN])) ||
            (orderData.data?.status == 'FINISHED' && isAuthorized([Role.FOREMAN]))
        )
    }
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
                            <Box sx={{ mt: '100px' }}>
                                <Paper sx={{ p: '20px' }}>
                                    <Typography variant="h5">Harmonogram pracy montażystów</Typography>
                                    <Planner
                                        orderId={params.id}
                                        initialDate={moment(orderData.data?.plannedStart)}
                                        readonly={!isAuthorized([Role.FOREMAN])}
                                    />
                                </Paper>
                            </Box>
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
                                nextStatus={canChangeToNextStatus() ? handleNextStatus : undefined}
                                previousStatus={canChangeToPreviousStatus() ? handlePreviousStatus : undefined}
                                editPermissionRoles={[Role.MANAGER, Role.SALES_REPRESENTATIVE]}
                                deletePermissionRoles={
                                    formik.values['status'] == 'CREATED'
                                        ? [Role.MANAGER, Role.SALES_REPRESENTATIVE]
                                        : undefined
                                }
                            />
                        </>
                    )}
                </FormPaper>
                {params.id !== 'new' && <OrderStagesDetails isAddOrderStageVisible={isAddOrderStageVisible} />}
            </FormBox>
        </>
    )
}

export default OrderDetails
