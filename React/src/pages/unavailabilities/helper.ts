import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import * as yup from 'yup'
import { AppSize } from '../../hooks/useBreakpoints'
import { UnavailabilityFilter } from '../../types/model/Unavailability'
import { typeOfUnavailabilityName, typeOfUnavailabilityOptions } from '../../helpers/enum.helper'
import { formatShortDate } from '../../helpers/format.helper'

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

export const emptyForm = {
    id: null,
    typeOfUnavailability: '',
    description: '',
    unavailableFrom: '',
    unavailableTo: '',
    assignedToId: '',
}

export const validationSchema = yup.object({
    assignedToId: yup.string().required('Wybierz pracownika!'),
    typeOfUnavailability: yup.string().required('Wybierz rodzaj niedostępności'),
    unavailableFrom: yup.date().required('Wybierz datę'),
    unavailableTo: yup
        .date()
        .required('Wybierz datę')
        .min(yup.ref('unavailableFrom'), 'Błędna data zakończenia nieobecności'),
})
