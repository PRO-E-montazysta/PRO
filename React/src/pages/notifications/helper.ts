import { FilterInputType } from '../../components/table/filter/TableFilter'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'
import { notificationTypeOptions } from '../../helpers/enum.helper'
import { formatDate } from '../../helpers/format.helper'
import { AppSize } from '../../hooks/useBreakpoints'
import { Notification } from '../../types/model/Notification'

export const headCells: Array<HeadCell<Notification>> = [
    {
        type: 'string',
        id: 'content',
        label: 'Treść powiadomienia',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: true,
    },
    {
        type: 'string',
        id: 'subContent',
        label: 'Dotyczy',
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: true,
    },
    {
        type: 'string',
        id: 'createdAt',
        label: 'Data powiadomienia',
        formatFn: (date: string) => formatDate(date),
        visibleInMode: [AppSize.mobile, AppSize.tablet, AppSize.notebook, AppSize.desktop],
        numeric: true,
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'notificationTypes',
        value: '',
        label: 'Typ powiadomienia',
        inputType: 'multiselect',
        typeValue: 'Array',
        options: notificationTypeOptions(),
    },
]
