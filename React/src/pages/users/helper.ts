import { FilterInputType } from '../../components/table/filter/TableFilter'
import { AppUser } from '../../types/model/AppUser'
import { HeadCell } from '../../components/table/sort/SortedTableHeader'

import * as yup from 'yup'

export const headCells: Array<HeadCell<AppUser>> = [
    {
        type: 'string',
        id: 'firstName',
        label: 'Imię użytkownika',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'lastName',
        label: 'Nazwisko użytkownika',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'password',
        label: 'Hasło',
        disablePadding: false,
        numeric: false,
    },
    {
        type: 'string',
        id: 'email',
        label: 'E-mail',
        disablePadding: false,
        numeric: false,
    },
]

export const filterInitStructure: Array<FilterInputType> = [
    {
        id: 'firstName',
        value: '',
        label: 'Imię użytkownika',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'lastName',
        value: '',
        label: 'Nazwisko użytkownika',
        inputType: 'text',
        typeValue: 'string',
    },
    {
        id: 'email',
        value: '',
        label: 'E-mail',
        inputType: 'text',
        typeValue: 'string',
    },
]

export const emptyForm = {
    id: null,
    firstName: '',
    lastName: '',
    password: '',
    email: '',
}

export const validationSchema = yup.object({
    firstName: yup.string().min(2, 'Imię musi zawierać co najmniej 2 znaki').required('Wprowadź imię'),
    lastName: yup.string().min(2, 'Nazwisko musi zawierać co najmniej 2 znaki').required('Wprowadź nazwisko'),
    password: yup.string().min(8, 'Hasło musi zawierać co najmniej 8 znaków').required('Wprowadź hasło'),
    email: yup.string().email('Wymagany jest poprawny email').required('Wprowadź email')
})



