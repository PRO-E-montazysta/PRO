import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import * as yup from 'yup'
import { AppSize } from '../../hooks/useBreakpoints'
import { UnavailabilityFilter } from '../../types/model/Unavailability'
import { typeOfUnavailabilityName, typeOfUnavailabilityOptions } from '../../helpers/enum.helper'
import { formatArrayToOptions, formatShortDate } from '../../helpers/format.helper'
import { FormInputProps } from '../../types/form'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getAllEmployees } from '../../api/employee.api'
import { Employee } from '../../types/model/Employee'
import { Role } from '../../types/roleEnum'

export const headCells: Array<HeadCell<UnavailabilityFilter>> = [
    {
        type: 'string',
        id: 'assignedTo',
        label: 'Pracownik',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'typeOfUnavailability',
        label: 'Rodzaj nieobecności',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (typeOfUnavailability: string) => typeOfUnavailabilityName(typeOfUnavailability),
    },
    {
        type: 'string',
        id: 'unavailableFrom',
        label: 'Od',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (date: string) => (date ? formatShortDate(date) : ''),
    },
    {
        type: 'string',
        id: 'unavailableTo',
        label: 'Do',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (date: string) => (date ? formatShortDate(date) : ''),
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'assignedTo',
        value: '',
        label: 'Pracownik',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'typeOfUnavailability',
        value: '',
        label: 'Rodzaj nieobecności',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: typeOfUnavailabilityOptions(),
    },
    {
        id: 'unavailableFrom',
        value: '',
        label: 'Od',
        inputType: 'date',
        typeValue: 'date',
    },
    {
        id: 'unavailableTo',
        value: '',
        label: 'Do',
        inputType: 'date',
        typeValue: 'date',
    },
]

export const useFormStructure = (): Array<FormInputProps> => {
    const queryEmployees = useQuery<Array<Employee>, AxiosError>(['employee-list'], getAllEmployees, {
        cacheTime: 15 * 60 * 1000,
        staleTime: 10 * 60 * 1000,
    })

    return [
        {
            label: 'Pracownik',
            id: 'assignedToId',
            initValue: '',
            type: 'select', //TODO: autocomplete
            validation: yup.string().required('Wybierz pracownika!'),
            options: formatArrayToOptions('id', (x: Employee) => x.firstName + ' ' + x.lastName, queryEmployees.data),
        },
        {
            label: 'Typ nieobecności',
            id: 'typeOfUnavailability',
            initValue: '',
            type: 'select',
            validation: yup.string().required('Wybierz rodzaj niedostępności'),
            options: typeOfUnavailabilityOptions(),
        },
        {
            label: 'Opis',
            id: 'description',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(3, 'Opis musi zawierać co najmniej 3 znaki')
                .max(500, 'Opis może zawierać maksymalnie 500 zanków'),
        },
        {
            label: 'Od',
            id: 'unavailableFrom',
            initValue: '',
            type: 'date',
            validation: yup.date().required('Wybierz datę'),
        },
        {
            label: 'Do',
            id: 'unavailableTo',
            initValue: '',
            type: 'date',
            validation: yup
                .date()
                .required('Wybierz datę')
                .min(yup.ref('unavailableFrom'), 'Błędna data zakończenia nieobecności'),
        },
        {
            label: 'Przypisane przez',
            id: 'assignedById',
            initValue: '',
            type: 'select',
            options: formatArrayToOptions('id', (x: Employee) => x.firstName + ' ' + x.lastName, queryEmployees.data),
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
    ]
}
