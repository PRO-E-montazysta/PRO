import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery, useQueryClient } from 'react-query'
import { AxiosError } from 'axios'
import { getAllMyNotifications, updateNotification } from '../../api/notification.api'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { Filter } from '../../components/table/filter/TableFilter'
import { useFormik } from 'formik'
import { Notification } from '../../types/model/Notification'

const Notifications = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()
    const queryClient = useQueryClient()

    const queryNotification = useQuery<Array<Notification>, AxiosError>(['my-notification', filterParams], async () =>
        getAllMyNotifications({ queryParams: filterParams }),
    )

    const filter: Filter = {
        formik: useFormik({
            initialValues: initialValues,
            // validationSchema={{}}
            onSubmit: () => {
                setFilterStructure(setNewFilterValues(filter.formik.values, filterStructure))
                setFilterParams(getFilterParams(filterStructure))
            },
            onReset: () => filter.formik.setValues(initialValues),
        }),
        inputs: inputs,
    }

    const createUrl = (row: Notification) => {
        console.log(row.notificationType)
        let url = '/'

        switch (row.notificationType) {
            case 'ORDER_CREATED':
            case 'ACCEPT_ORDER':
            case 'FOREMAN_ASSIGNMENT':
                return (url += 'orders/' + row.orderId)
            case 'TOOL_EVENT':
                return (url += 'toolevent/' + row.toolEventId)
            case 'ELEMENT_EVENT':
                return (url += 'elementevent/' + row.elementEventId)
        }
    }

    return (
        <FatTable
            idPropName="id"
            query={queryNotification}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'createdAt'}
            initOrderByDesc={true}
            onClickRow={async (e, row) => {
                if (row.readAt == null) {
                    await updateNotification(row.id)
                    queryClient.invalidateQueries('notifications')
                }
                navigation(`${createUrl(row)}`)
            }}
            pageHeader="Lista powiadomień"
        />
    )
}

export default Notifications
