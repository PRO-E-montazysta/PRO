import { Button, Paper, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { useFormik } from 'formik'
import { useContext, useEffect, useState, useLayoutEffect } from 'react'
import { useParams } from 'react-router-dom'
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
    useOrderStagesData,
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
import Localization from '../../components/localization/Localization'
import { Order } from '../../types/model/Order'
import { useFormStructureLocation, useLocationData } from '../../components/localization/hooks'
import Error from '../../components/error/Error'
import { isAuthorized } from '../../utils/authorize'

import moment from 'moment'
import Planner from './Planner'
import ExpandMore from '../../components/expandMore/ExpandMore'
import MapIcon from '@mui/icons-material/Map'
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth'
import { canChangeToNextStatus, canChangeToPreviousStatus, validateNextOrderStatus } from './statusValidation'

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
    const orderStagesData = useOrderStagesData(params.id)
    const orderNextStatusMutation = useOrderNextStatus(() => {
        orderData.refetch()
    })
    const orderPreviousStatusMutation = useOrderPreviousStatus(() => {
        orderData.refetch()
    })

    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [orderData, orderStagesData],
        [addOrderMutation, editOrderMutation, deleteOrderMutation, orderNextStatusMutation],
    )

    useEffect(() => {
        const role = getRolesFromToken()
        if (role.length !== 0) setUserRole(role[0])
    }, [])

    const handleSubmit = async (values: any) => {
        //show formik location errors to user
        formikLocation.submitForm().then(() => {
            //check if there are any error
            if (
                formikLocation.values.xcoordinate.toString() != '' &&
                formikLocation.values.ycoordinate.toString() != ''
            ) {
                if (pageMode == 'new') addOrderMutation.mutate(values)
                else if (pageMode == 'edit') editOrderMutation.mutate(values)
                else console.warn('Try to submit while read mode')
            } else {
                showDialog({
                    title: 'Lokalizacja wymagana',
                    content:
                        formikLocation.values.city == ''
                            ? 'Do zapisania zlecenia niezbędna jest lokalizacja'
                            : 'Potwierdź wprowadzony adres',
                    btnOptions: [
                        {
                            text: 'Ok',
                            value: 0,
                        },
                    ],
                })
            }
        })
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
                if (result == 1 && params.id) deleteOrderMutation.mutate(params.id)
            },
        })
    }

    const handleNextStatus = () => {
        const validationResult = validateNextOrderStatus(orderData.data, orderStagesData.data)

        if (validationResult.isValid)
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
        else
            showDialog({
                title: 'Nie można zmienić statusu',
                content: validationResult.message,
                btnOptions: [{ text: 'Ok', value: 1, variant: 'contained' }],
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

    const formik = useFormik<Order>({
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
    useLayoutEffect(() => {
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

    const [addingNewStage, setAddingNewStage] = useState(false)

    //--------------- Localization functionality --------------------
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

    return orderData.data?.deleted ? (
        <>
            <Error code={404} message={'Ten obiekt został usunięty'} />
        </>
    ) : (
        <>
            <FormBox>
                <FormTitle
                    mainTitle={pageMode == 'new' ? 'Nowe zlecenie' : 'Zlecenie'}
                    subTitle={pageMode == 'new' ? undefined : formik.values.name}
                />
                <FormPaper>
                    {queriesStatus.result != 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
                            <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />

                            <Paper sx={{ m: '20px 0' }}>
                                <ExpandMore
                                    isOpen={pageMode == 'new' || Object.keys(formikLocation.errors).length > 0}
                                    titleIcon={<MapIcon />}
                                    title="Lokalizacja"
                                    titleVariant="h5"
                                    cardContent={
                                        <Localization
                                            formik={formikLocation}
                                            formStructure={formStructureLocation}
                                            pageMode={
                                                isAuthorized([
                                                    Role.SALES_REPRESENTATIVE,
                                                    Role.ADMIN,
                                                    Role.WAREHOUSE_MANAGER,
                                                ])
                                                    ? pageMode
                                                    : 'read'
                                            }
                                        />
                                    }
                                />
                            </Paper>

                            {orderData.data && orderData.data.orderStages.length > 0 && (
                                <Paper>
                                    <ExpandMore
                                        titleIcon={<CalendarMonthIcon />}
                                        title="Harmonogram pracy montażystów"
                                        titleVariant="h5"
                                        cardContent={
                                            <Planner
                                                orderId={params.id}
                                                initialDate={moment(orderData.data?.plannedStart)}
                                                readonly={!isAuthorized([Role.FOREMAN])}
                                            />
                                        }
                                    />
                                </Paper>
                            )}

                            <FormButtons
                                id={params.id}
                                onCancel={handleCancel}
                                onDelete={handleDelete}
                                onEdit={() => setPageMode('edit')}
                                onReset={handleReset}
                                onSubmit={formik.submitForm}
                                readonlyMode={pageMode == 'read'}
                                orderStageButton={getAddOrderStageButton()}
                                setAddingNewStage={setAddingNewStage}
                                addingNewStage={addingNewStage}
                                nextStatus={canChangeToNextStatus(orderData.data) ? handleNextStatus : undefined}
                                previousStatus={
                                    canChangeToPreviousStatus(orderData.data) ? handlePreviousStatus : undefined
                                }
                                editPermissionRoles={
                                    formik.values.status == 'CREATED'
                                        ? [Role.MANAGER, Role.SALES_REPRESENTATIVE]
                                        : [Role.MANAGER]
                                }
                                deletePermissionRoles={
                                    formik.values.status == 'CREATED'
                                        ? [Role.MANAGER, Role.SALES_REPRESENTATIVE]
                                        : undefined
                                }
                            />
                        </>
                    )}
                </FormPaper>
                {params.id !== 'new' && (
                    <OrderStagesDetails addingNewStage={addingNewStage} setAddingNewStage={setAddingNewStage} />
                )}
            </FormBox>
        </>
    )
}

export default OrderDetails
