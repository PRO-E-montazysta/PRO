import { Paper, Typography } from '@mui/material'
import { Box } from '@mui/system'
import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'

import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'

import * as yup from 'yup'
import { useFormStructure } from './helper'
import { useAddOrder, useDeleteOrder, useEditOrder, useOrderData } from './hooks'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { FormButtons } from '../../components/form/FormButtons'
import { FormStructure } from '../../components/form/FormStructure'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import QueryBoxStatus from '../../components/base/QueryStatusBox'

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

    //form with initial values and validation
    const formik = useFormik({
        initialValues: initData,
        validationSchema: yup.object(getValidatinSchema(formStructure)),
        onSubmit: () => handleSubmit(),
    })

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

    const handleOnEditSuccess = (data: any) => {
        orderData.refetch({
            queryKey: ['order', { id: data.id }],
        })
        setReadonlyMode(true)
    }

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
            <Box maxWidth={1200} style={{ margin: 'auto', marginTop: '20px' }}>
                <Typography variant="h4" sx={{ color: 'white', fontWeight: 700 }}>
                    Zamówienia
                </Typography>
                <Paper sx={{ p: '10px', mt: '20px' }}>
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
