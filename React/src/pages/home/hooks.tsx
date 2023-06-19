import { useQuery } from 'react-query'
import { Unavailability } from '../../types/model/Unavailability'
import { getUnavailabilityByMonth } from '../../api/unavailability.api'
import { AxiosError } from 'axios'
import { useEffect, useState } from 'react'
import { Order } from '../../types/model/Order'
import { SelectMenuItemProps } from '../../components/form/types'
import { getFilteredOrders } from '../../api/order.api'
import { getAllEmployees } from '../../api/employee.api'
import { Employee } from '../../types/model/Employee'
import useError from '../../hooks/useError'

export const useUnavailabilityListByMonth = (year: number, month: number) => {
    const onError = useError()
    return useQuery<Unavailability[], AxiosError>(
        ['unavailability', { month: month, year: year }],
        async () =>
            getUnavailabilityByMonth({
                queryParams: {
                    month: month,
                    year: year,
                },
            }),
        {
            onError: onError,
        },
    )
}

export const useOrdersAsOptions = () => {
    const [orderOptions, setOrderOptions] = useState<SelectMenuItemProps[]>([])
    const orders = useQuery<Array<Order>, AxiosError>(['orders'], async () => getFilteredOrders({ queryParams: {} }))

    useEffect(() => {
        if (orders.data)
            setOrderOptions(
                orders.data.map((o) => {
                    return {
                        key: o.id,
                        value: o.name,
                    }
                }),
            )
    }, [orders])

    return orderOptions
}

export const useEmployeesAsOptions = () => {
    const [employeeOptions, setEmployeeOptions] = useState<SelectMenuItemProps[]>([])
    const employees = useQuery<Array<Employee>, AxiosError>(['employees'], async () => getAllEmployees())

    useEffect(() => {
        if (employees.data)
            setEmployeeOptions(
                employees.data.map((o) => {
                    return {
                        key: o.id,
                        value: o.firstName + ' ' + o.lastName,
                    }
                }),
            )
    }, [employees])

    return employeeOptions
}
