import { FilterInputType } from '../../components/table/filter/TableFilter'
import { DemandAdHocFilterDto } from '../../types/model/DemandAdHoc'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import * as yup from 'yup'
import { AppSize } from '../../hooks/useBreakpoints'
import { FormInputProps } from '../../types/form'
import { Role } from '../../types/roleEnum'
import { formatArrayToOptions, formatDate } from '../../helpers/format.helper'
import { Employee } from '../../types/model/Employee'
import { getAllEmployees } from '../../api/employee.api'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getAllOrderSages } from '../../api/orderStage.api'
import { OrderStage } from '../../types/model/OrderStage'

export const headCells: Array<HeadCell<DemandAdHocFilterDto>> = [
    {
        type: 'string',
        id: 'orderStageName',
        label: 'Etap zlecenia',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'createdByName',
        label: 'Zgłoszone przez',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'createdAt',
        label: 'Data utworzenia',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (date: string) => formatDate(date),
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'createdByName',
        value: '',
        label: 'Brygadzista',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'orderStageName',
        value: '',
        label: 'Etap zlecenia',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'dateFrom',
        value: '',
        label: 'Utworzone po',
        inputType: 'date',
        typeValue: 'date',
    },
    {
        id: 'dateTo',
        value: '',
        label: 'Utworzone przed',
        inputType: 'date',
        typeValue: 'date',
    },
]

export const useFormStructure = (): Array<FormInputProps> => {
    const queryEmployees = useQuery<Array<Employee>, AxiosError>(['employee-list'], getAllEmployees, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })
    const queryOrderStages = useQuery<Array<OrderStage>, AxiosError>(['orderStages-list'], getAllOrderSages, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })

    return [
        {
            label: 'Data utworzenia',
            id: 'createdAt',
            initValue: '',
            type: 'date-time',
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Utowrzone przez',
            id: 'createdById',
            initValue: '',
            type: 'select',
            options: formatArrayToOptions('id', (x: Employee) => x.firstName + ' ' + x.lastName, queryEmployees.data),
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Etap zlecenia',
            id: 'orderStageId',
            initValue: '',
            type: 'select',
            options: formatArrayToOptions('id', (x: OrderStage) => x.name, queryOrderStages.data),
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
            //id przekazane z params
        },
        {
            label: 'Opis zapotrzebowania',
            id: 'description',
            initValue: '',
            type: 'input', //TODO: Jako textfield
            validation: yup
                .string()
                .min(3, 'Opis musi zawierać co najmniej 3 znaki')
                .max(1000, 'Opis może zawierać maksymalnie 1000 zanków')
                .required('Wprowadź opis zapotrzebowania'),
        },
    ]
}
