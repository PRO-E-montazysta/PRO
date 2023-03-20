import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Element, TypeOfUnit } from '../../types/model/Element'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'

import * as yup from 'yup'
import { typeOfUnitName } from '../../helpers/enum.helper'

export const headCells: Array<HeadCell<Element>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'code',
        label: 'Kod',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'typeOfUnit',
        label: 'Jednostka',
        disablePadding: false,
        numeric: false,
        formatFn: (typeOfUnit: string) => typeOfUnitName(typeOfUnit),
    },
    {
        type: 'string',
        id: 'quantityInUnit',
        label: 'Ilość w jednostce',
        disablePadding: false,
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
        id: 'code',
        value: '',
        label: 'Kod',
        inputType: 'text',
        typeValue: 'string',
    },
]

export const emptyForm = {
    id: null,
    name: '',
    code: '',
    typeOfUnit: '',
    quantityInUnit: '',
    elementReturnReleases: [],
    elementInWarehouses: [],
    elementEvents: [],
    attachmentId: '',
    ordersStages: [],
}

export const validationSchema = yup.object({
    name: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
    typeOfUnit: yup.string().required('Wybierz rodzaj jednostki'),
})
