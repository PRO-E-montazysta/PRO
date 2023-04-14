import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Order } from '../../types/model/Order'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { formatArrayToOptions, formatDate, formatLocation } from '../../helpers/format.helper'
import { priorityName, priorityOptions, statusName, statusOptions } from '../../helpers/enum.helper'

import * as yup from 'yup'
import { useQuery } from 'react-query'
import { Client } from '../../types/model/Client'
import { Company } from '../../types/model/Company'
import { AppUser } from '../../types/model/AppUser'
import { Location } from '../../types/model/Location'
import { AxiosError } from 'axios'
import { getAllClients } from '../../api/client.api'
import { getAllCompanies } from '../../api/company.api'
import { getAllForemans } from '../../api/foreman.api'
import { getAllLocations } from '../../api/location.api'
import { getAllManagers } from '../../api/manager.api'
import { getAllSalesRepresentatives } from '../../api/salesRepresentatives.api'
import { getAllSpecialists } from '../../api/specialist.api'
import { AppSize } from '../../hooks/useBreakpoints'
import { SelectMenuItemProps } from '../../components/form/types'


export const headCells: Array<HeadCell<Order>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        numeric: false,
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'createdAt',
        label: 'Czas utworzenia',
        numeric: false,
        formatFn: (date: string) => (date ? formatDate(date) : ''),
        visibleInMode: [AppSize.desktop],
    },
    {
        type: 'string',
        id: 'typeOfPriority',
        label: 'Priorytet',
        numeric: false,
        formatFn: (status: string) => priorityName(status),

        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'plannedStart',
        label: 'Planowany czas rozpoczęcia',
        numeric: false,
        formatFn: (date: string) => (date ? formatDate(date) : ''),
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'plannedEnd',
        label: 'Planowany czas zakończenia',
        numeric: false,
        formatFn: (date: string) => formatDate(date),
        visibleInMode: [AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'typeOfStatus',
        label: 'Status',
        numeric: false,
        formatFn: (status: string) => statusName(status),
        visibleInMode: [AppSize.notebook, AppSize.desktop],
    },
    {
        type: 'string',
        id: 'orderStages',
        label: 'Liczba etapów',
        numeric: false,
        formatFn: (orderStages) => orderStages.length,
        visibleInMode: [AppSize.desktop],
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'typeOfStatus',
        value: ['PLANNED', 'IN_PROGRESS'],
        label: 'Status',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: statusOptions(),
    },
    {
        id: 'name',
        value: '',
        label: 'Nazwa',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'createdAtMin',
        value: '',
        label: 'Czas utworzenia od',
        inputType: 'datetime-local',
        typeValue: 'date',
    },
    {
        id: 'createdAtMax',
        value: '',
        label: 'Czas utworzenia do',
        inputType: 'datetime-local',
        typeValue: 'date',
    },
]

export type FormInputProps = {
    label: string
    id: string
    initValue: any
    type: 'input' | 'select' | 'date'
    options?: Array<SelectMenuItemProps>
    validation?: any
    readonly?: boolean
}

export const useFormStructure = (): Array<FormInputProps> => {
    const queryClient = useQuery<Array<Client>, AxiosError>(['client-list'], getAllClients, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    const queryCompany = useQuery<Array<Company>, AxiosError>(['company-list'], getAllCompanies, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    const queryForeman = useQuery<Array<AppUser>, AxiosError>(['foreman-list'], getAllForemans, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    const queryLocation = useQuery<Array<Location>, AxiosError>(['location-list'], getAllLocations, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    const queryManager = useQuery<Array<AppUser>, AxiosError>(['manager-list'], getAllManagers, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    const querySalesRepresentative = useQuery<Array<AppUser>, AxiosError>(
        ['sales-reprezentative-list'],
        getAllSalesRepresentatives,
        {
            cacheTime: 15 * 60 * 1000,
            staleTime: 10 * 60 * 1000,
        },
    )
    const querySpecialist = useQuery<Array<AppUser>, AxiosError>(['specialist-list'], getAllSpecialists, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })

    return [
        {
            label: 'Nazwa zlecenia',
            id: 'name',
            initValue: '',
            type: 'input',
            validation: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
        },
        {
            label: 'Firma',
            id: 'companyId',
            initValue: null,
            type: 'select',
            validation: yup.number().typeError('Wybierz firmę'),
            options: formatArrayToOptions('id', (x: Company) => x.companyName, queryCompany.data),
        },
        {
            label: 'Priorytet',
            id: 'typeOfPriority',
            initValue: '',
            type: 'select',
            validation: yup.string().required('Wybierz priorytet'),
            options: priorityOptions(),
        },
        {
            label: 'Status',
            id: 'typeOfStatus',
            initValue: '',
            type: 'select',
            validation: yup.string().required('Wybierz status'),
            options: statusOptions(),
        },
        {
            label: 'Planowany czas rozpoczęcia',
            id: 'plannedStart',
            initValue: '',
            type: 'date',
            validation: yup.date().required('Wybierz datę'),
        },
        {
            label: 'Planowany czas zakończenia',
            id: 'plannedEnd',
            initValue: '',
            type: 'date',
            validation: yup.date().required('Wybierz datę'),
        },
        {
            label: 'Klient',
            id: 'clientId',
            initValue: null,
            type: 'select',
            options: formatArrayToOptions('id', (x: Client) => x.name, queryClient.data),
        },
        {
            label: 'Brygadzista',
            id: 'foremanId',
            initValue: null,
            type: 'select',
            options: formatArrayToOptions('id', (x: AppUser) => x.firstName + ' ' + x.lastName, queryForeman.data),
        },
        {
            label: 'Lokalizacja',
            id: 'locationId',
            initValue: null,
            type: 'select',
            options: formatArrayToOptions('id', formatLocation, queryLocation.data),
        },
        {
            label: 'Manager',
            id: 'managerId',
            initValue: null,
            type: 'select',
            options: formatArrayToOptions('id', (x: AppUser) => x.firstName + ' ' + x.lastName, queryManager.data),
        },
        {
            label: 'Specjalista',
            id: 'specialistId',
            initValue: null,
            type: 'select',
            options: formatArrayToOptions('id', (x: AppUser) => x.firstName + ' ' + x.lastName, querySpecialist.data),
        },
        {
            label: 'Handlowiec',
            id: 'salesRepresentativeId',
            initValue: null,
            type: 'select',
            options: formatArrayToOptions(
                'id',
                (x: AppUser) => x.firstName + ' ' + x.lastName,
                querySalesRepresentative.data,
            ),
        },
        {
            label: 'Czas utworzenia',
            id: 'createdAt',
            initValue: '',
            type: 'date',
            readonly: true,
        },
        {
            label: 'Czas ostatniej edycji',
            id: 'editedAt',
            initValue: '',
            type: 'date',
            readonly: true,
        },
    ]
}
