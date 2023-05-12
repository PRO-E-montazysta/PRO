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
import { FormInputProps } from '../../types/form'
import { Role } from '../../types/roleEnum'

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

export const useFormStructure = (): Array<FormInputProps> => {
    const queryClient = useQuery<Array<Client>, AxiosError>(['client-list'], getAllClients, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    const queryForeman = useQuery<Array<AppUser>, AxiosError>(['foreman-list'], getAllForemans, {
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
            validation: yup.string().min(3, 'Nazwa musi zawierać co najmniej 3 znaki').required('Wprowadź nazwę'),
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
            id: 'status',
            initValue: '',
            type: 'select',
            validation: yup.string().required('Wybierz status'),
            options: statusOptions(),
        },
        {
            label: 'Planowany czas rozpoczęcia',
            id: 'plannedStart',
            initValue: '',
            type: 'date-time',
            validation: yup.date().required('Wybierz datę'),
        },
        {
            label: 'Planowany czas zakończenia',
            id: 'plannedEnd',
            initValue: '',
            type: 'date-time',
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
            type: 'date-time',
            readonly: true,
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Czas ostatniej edycji',
            id: 'editedAt',
            initValue: '',
            type: 'date-time',
            readonly: true,
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
    ]
}

export const useFormStructureLocation = (): Array<FormInputProps> => {
    return [
        {
            label: 'Miasto',
            id: 'city',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Ulica',
            id: 'street',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Numer posiadłości',
            id: 'propertyNumber',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Numer apartamentu',
            id: 'apartmentNumber',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Kod pocztowy',
            id: 'zipCode',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .trim()
                .matches(/^[0-9]{2}-[0-9]{3}$/, 'Kod pocztowy musi być formatu xx-xxx'),
        },
        {
            label: 'x',
            id: 'xCoordinate',
            initValue: '',
            type: 'number',
            dontIncludeInFormStructure: true,
            readonly: true,
        },
        {
            label: 'y',
            id: 'yCoordinate',
            initValue: '',
            type: 'number',
            dontIncludeInFormStructure: true,
            readonly: true,
        },
    ]
}
