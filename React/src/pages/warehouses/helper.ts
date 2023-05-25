import { FilterInputType } from '../../components/table/filter/TableFilter'
import { WarehouseFilterDto } from '../../types/model/Warehouse'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'

import * as yup from 'yup'

import { AppSize } from '../../hooks/useBreakpoints'
import { FormInputProps } from '../../types/form'
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
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'openingHours',
        label: 'Godziny otwarcia',
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
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

export const useFormStructure = (): Array<FormInputProps> => {
    return [
        {
            label: 'Nazwa magazynu',
            id: 'name',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(3, 'Nazwa musi zawierać co najmniej 3 znaki')
                .max(255, 'Nazwa może zawierać maksymalnie 255 znaków')
                .required('Wprowadź nazwę'),
        },
        {
            label: 'Opis',
            id: 'description',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(3, 'Opis musi zawierać co najmniej 3 znaki')
                .max(1000, 'Opis może zawierać maksymalnie 1000 znaków'),
        },
        {
            label: 'Godziny otwarcia',
            id: 'openingHours',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .min(3, 'Godziny otwarcia muszą zawierać co najmniej 3 znaki')
                .max(255, 'Godziny otwarcia mogą zawierać maksymalnie 255 znaków')
                .required('Wprowadź godziny otwarcia'),
        },
    ]
}
