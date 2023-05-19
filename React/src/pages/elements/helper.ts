import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Element, TypeOfUnit } from '../../types/model/Element'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import * as yup from 'yup'
import { typeOfUnitName, typeOfUnitOptions } from '../../helpers/enum.helper'
import { AppSize } from '../../hooks/useBreakpoints'
import { FormInputProps } from '../../types/form'
import { Role } from '../../types/roleEnum'
import { ElementInWarehouse, ElementInWarehouseFilterDto } from '../../types/model/ElementInWarehouse'
import { formatArrayToOptions } from '../../helpers/format.helper'

export const headCells: Array<HeadCell<Element>> = [
    {
        type: 'string',
        id: 'name',
        label: 'Nazwa',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'code',
        label: 'Kod',
        visibleInMode: [AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'typeOfUnit',
        label: 'Jednostka',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
        formatFn: (typeOfUnit: string) => typeOfUnitName(typeOfUnit),
    },
    {
        type: 'string',
        id: 'quantityInUnit',
        label: 'Ilość w jednostce',
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

export const useFormStructure = (): Array<FormInputProps> => {
    return [
        {
            label: 'Nazwa elementu',
            id: 'name',
            initValue: '',
            type: 'input',
            validation: yup.string().min(3, 'Nazwa musi zaweirać co najmniej 3 znaki').required('Wprowadź nazwę'),
        },
        {
            label: 'Kod',
            id: 'code',
            initValue: '',
            type: 'input',
            addNewPermissionRoles: [Role.NOBODY],
            editPermissionRoles: [Role.NOBODY],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Typ jednostki',
            id: 'typeOfUnit',
            initValue: '',
            type: 'select',
            options: typeOfUnitOptions(),
            validation: yup.string().required('Wybierz rodzaj jednostki'),
        },
        {
            label: 'Ilość w jednostce',
            id: 'quantityInUnit',
            initValue: '',
            type: 'number',
        },
    ]
}

//ElementInWarehouse card
export const elementInWarehouseHeadCells: Array<HeadCell<ElementInWarehouseFilterDto>> = [
    {
        type: 'string',
        id: 'warehouse',
        label: 'Magazyn',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'inWarehouseCount',
        label: 'Ilość',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'rack',
        label: 'Regał',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
    {
        type: 'string',
        id: 'shelf',
        label: 'Półka',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: false,
    },
]

export const elementInWarehouseFilterInitStructure: Array<FilterInputType> = [
    {
        id: 'minCount',
        value: '',
        label: 'Minimalna ilość',
        inputType: 'number',
        typeValue: 'number',
    },
    {
        id: 'warehouseId',
        value: '',
        label: 'Magazyn',
        inputType: 'multiselect',
        typeValue: 'Array',
    },
]
