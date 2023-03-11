import { FilterInputType } from '../../components/table/filter/TableFilter'
import { Company } from '../../types/model/Company'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { formatDate } from '../../helpers/format.helper'
import { statusOptions } from '../../helpers/enum.helper'

import * as yup from 'yup'

export const headCells: Array<HeadCell<Company>> = [
    {
        type: 'string',
        id: 'id',
        label: 'Id',
        disablePadding: false,
        numeric: true,
    },
    {
        type: 'string',
        id: 'companyName',
        label: 'Nazwa firmy',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'createdAt',
        label: 'Data utworzenia',
        disablePadding: false,
        numeric: false,
        formatFn: (date: string) => (date ? formatDate(date) : ''),
    },
    {
        type: 'string',
        id: 'status',
        label: 'Status',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'statusReason',
        label: 'Uzasadnienie statusu',
        disablePadding: false,
        numeric: false,
    },
];

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'id',
        value: '',
        label: 'Id',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'companyName',
        value: '',
        label: 'Nazwa firmy',
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
    {
        id: 'typeOfStatus',
        value: ['ACTIVE', 'BLOCKED'],
        label: 'Status',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: statusOptions(),
    },
];

export const emptyForm = {
    id: null,
    companyName: '',
    createdAt: '',
    status: '',
    statusReason: ''
};

export const validationSchema = yup.object({
    companyName: yup.string().min(2, 'Nazwa musi zawierać co najmniej 2 znaki').required('Wprowadź nazwę'),
});
