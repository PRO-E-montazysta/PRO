import { Paper, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useMutation, useQuery } from 'react-query'
import { useNavigate, useParams } from 'react-router-dom'
import { deleteOrder, getOrderDetails, postOrder, updateOrder } from '../../api/order.api'
import { formatArrayToOptions, formatLocation } from '../../helpers/format.helper'
import { priorityOptions, statusOptions } from '../../helpers/enum.helper'
import { theme } from '../../themes/baseTheme'
import { Client } from '../../types/model/Client'
import { Order } from '../../types/model/Order'
import { AppUser } from '../../types/model/AppUser'
import { getAllClients } from '../../api/client.api'
import { getAllForemans } from '../../api/foreman.api'
import { getAllLocations } from '../../api/location.api'
import { getAllManagers } from '../../api/manager.api'
import { getAllSalesRepresentatives } from '../../api/salesRepresentatives.api'
import { getAllSpecialists } from '../../api/specialist.api'

import * as yup from 'yup'
import { useFormStructure } from './helper'
import { useAddOrder, useDeleteOrder, useEditOrder, useOrderData } from './hooks'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { FormButtons } from '../../components/form/FormButtons'
import { FormStructure } from '../../components/form/FormStructure'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import useBreakpoints from '../../hooks/useBreakpoints'
import { AxiosError } from 'axios'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { validationSchema } from '../toolTypes/helper'

const OrderDetails = () => {
    //parameters from url
    const params = useParams()

    //mode of this page
    const [readonlyMode, setReadonlyMode] = useState(true)
    //global dialog box
    const { showDialog } = useContext(DialogGlobalContext)
    //custom form structure
    const formStructure = useFormStructure()

    //initial values from custom form structure
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addOrderMutation = useAddOrder()
    const editOrderMutation = useEditOrder((data) => handleOnEditSuccess(data))
    const deleteOrderMutation = useDeleteOrder(() => orderData.remove())
    const orderData = useOrderData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus([orderData], [addOrderMutation, editOrderMutation, deleteOrderMutation])

    const appSize = useBreakpoints()
    const handleSubmit = () => {
        if (params.id == 'new') addOrderMutation.mutate(JSON.parse(JSON.stringify(formik.values)))
        else editOrderMutation.mutate(JSON.parse(JSON.stringify(formik.values)))
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć zlecenie?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id && params.id != 'new') deleteOrderMutation.mutate(params.id)
            },
        })
    }

    const queryData = useQuery<Order, AxiosError>(
        ['order', { id: params.id }],
        async () => getOrderDetails(params.id && params.id != 'new' ? params.id : ''),
        {
            enabled: !!params.id && params.id != 'new',
        },
    )

    const queryClient = useQuery<Array<Client>, AxiosError>(['client-list'], getAllClients)
    const queryForeman = useQuery<Array<AppUser>, AxiosError>(['foreman-list'], getAllForemans)
    const queryLocation = useQuery<Array<Location>, AxiosError>(['location-list'], getAllLocations)
    const queryManager = useQuery<Array<AppUser>, AxiosError>(['manager-list'], getAllManagers)
    const querySalesReprezentative = useQuery<Array<AppUser>, AxiosError>(
        ['sales-reprezentative-list'],
        getAllSalesRepresentatives,
    )
    const querySpecialist = useQuery<Array<AppUser>, AxiosError>(['specialist-list'], getAllSpecialists)

    const formik = useFormik({
        initialValues: initData,
        validationSchema: validationSchema,
        onSubmit: handleSubmit,
    })

    const handleReset = () => {
        formik.resetForm()
        formik.setValues(JSON.parse(JSON.stringify(initData)))
    }

    const handleCancel = () => {
        handleReset()
        setReadonlyMode(true)
    }

    //edit mode
    //populate formik and init values with data from db
    useEffect(() => {
        if (orderData.data) {
            formik.setValues(JSON.parse(JSON.stringify(orderData.data)))
            setInitData(JSON.parse(JSON.stringify(orderData.data)))
        }
    }, [orderData.data])

    //new mode
    //set readonly mode, populate formik and init data with init values from helper
    useEffect(() => {
        if (params.id == 'new') {
            setReadonlyMode(false)
            formik.setValues(JSON.parse(JSON.stringify(getInitValues(formStructure))))
            setInitData(JSON.parse(JSON.stringify(getInitValues(formStructure))))
        } else setReadonlyMode(true)
    }, [params.id])

    return (
        <>
            <Box
                sx={{
                    p: appSize.isMobile || appSize.isTablet ? '10px' : '20px',
                    maxWidth: '1200px',
                    m: 'auto',
                    minWidth: '280px',
                }}
            >
                <Typography
                    variant="h4"
                    fontWeight="bold"
                    padding="5px"
                    color={theme.palette.primary.contrastText}
                    fontSize={appSize.isMobile || appSize.isTablet ? '22px' : '32px'}
                >
                    Zamówienia
                </Typography>
                <Paper
                    sx={{
                        mt: appSize.isMobile || appSize.isTablet ? '10px' : '20px',
                        p: appSize.isMobile ? '10px' : '20px',
                    }}
                >
                    {queriesStatus.result != 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
                            <FormStructure formStructure={formStructure} formik={formik} readonlyMode={readonlyMode} />
                            <FormButtons
                                id={params.id}
                                onCancel={handleCancel}
                                onDelete={handleDelete}
                                onEdit={() => setReadonlyMode(false)}
                                onReset={handleReset}
                                onSubmit={formik.submitForm}
                                readonlyMode={readonlyMode}
                            />
                        </>
                    )}
                </Paper>
            </Box>
        </>
    )
}

export default OrderDetails
function handleOnEditSuccess(data: any): void {
    throw new Error('Function not implemented.')
}

