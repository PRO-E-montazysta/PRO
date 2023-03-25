import { FilterInputType } from '../../components/table/filter/TableFilter'
import { WarehouseFilterDto } from '../../types/model/Warehouse'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'

import * as yup from 'yup'

import { AppSize } from '../../hooks/useBreakpoints'
export const headCells: Array<HeadCell<WarehouseFilterDto>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'description',
        label: 'Opis',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'openingHours',
        label: 'Godziny otwarcia',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'fullAddress',
        label: 'Adres',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'name',
        value: '',
        label: 'Nazwa',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'description',
        value: '',
        label: 'Opis',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'zipCode',
        value: '',
        label: 'Kod pocztowy',
        inputType: 'text',
        typeValue: 'string',
    },
]

export const emptyForm = {
    id: null,
    name: '',
    description: '',
    openingHours: '',
    companyId: null,
    locationId: null,
    elementInWarehouses: [],
    tools: [],
}

export const validationSchema = yup.object({
    name: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
    openingHours: yup.string().required('Wprowadź godziny otwarcia'),
    companyId: yup.number().typeError('Wybierz firmę'),
    locationId: yup.number().typeError('Wybierz lokalizację'),
})
