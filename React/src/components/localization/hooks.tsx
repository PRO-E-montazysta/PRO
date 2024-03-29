import { AxiosError } from 'axios'
import { useMutation, useQuery } from 'react-query'
import useError from '../../hooks/useError'
import { getLocationById, postLocation, updateLocation } from '../../api/location.api'
import * as yup from 'yup'
import { FormInputProps } from '../../types/form'
import { Role } from '../../types/roleEnum'

export const useLocationData = (id: string | undefined) => {
    return useQuery<Location, AxiosError>(
        ['location', { id: id }],
        async () => getLocationById(id && id != 'new' ? id : ''),
        {
            enabled: !!id && id != 'new',
        },
    )
}

export const useAddLocation = (onSuccessCallback: (data: any) => void) => {
    const showError = useError()
    return useMutation({
        mutationFn: postLocation,
        onSuccess: onSuccessCallback,
        onError: showError,
    })
}

export const useEditLocation = (onSuccessCallback: (data: any) => void) => {
    const showError = useError()
    return useMutation({
        mutationFn: updateLocation,
        onSuccess: onSuccessCallback,
        onError: showError,
    })
}

export const useFormStructureLocation = (): Array<FormInputProps> => {
    return [
        {
            label: 'Miasto',
            id: 'city',
            initValue: '',
            type: 'input',
            editPermissionRoles: [Role.SALES_REPRESENTATIVE, Role.WAREHOUSE_MANAGER, Role.ADMIN],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Ulica',
            id: 'street',
            initValue: '',
            type: 'input',
            editPermissionRoles: [Role.SALES_REPRESENTATIVE, Role.WAREHOUSE_MANAGER, Role.ADMIN],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Numer posiadłości',
            id: 'propertyNumber',
            initValue: '',
            type: 'input',
            editPermissionRoles: [Role.SALES_REPRESENTATIVE, Role.WAREHOUSE_MANAGER, Role.ADMIN],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Numer apartamentu',
            id: 'apartmentNumber',
            initValue: '',
            type: 'input',
            editPermissionRoles: [Role.SALES_REPRESENTATIVE, Role.WAREHOUSE_MANAGER, Role.ADMIN],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'Kod pocztowy',
            id: 'zipCode',
            initValue: '',
            type: 'input',
            validation: yup
                .string()
                .trim()
                .matches(/^[0-9]{2}-[0-9]{3}$/, 'Kod pocztowy musi być formatu xx-xxx'),
            editPermissionRoles: [Role.SALES_REPRESENTATIVE, Role.WAREHOUSE_MANAGER, Role.ADMIN],
            viewPermissionRoles: [Role['*']],
        },
        {
            label: 'x',
            id: 'xcoordinate',
            initValue: '',
            type: 'number',
            dontIncludeInFormStructure: true,
            readonly: true,
            validation: yup.number().required(),
        },
        {
            label: 'y',
            id: 'ycoordinate',
            initValue: '',
            type: 'number',
            dontIncludeInFormStructure: true,
            readonly: true,
            validation: yup.number().required(),
        },
    ]
}
