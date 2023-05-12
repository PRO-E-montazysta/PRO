import { AxiosError } from 'axios'
import { useMutation, useQuery } from 'react-query'
import useError from '../../hooks/useError'
import { getLocationById, postLocation, updateLocation } from '../../api/location.api'
import * as yup from 'yup'
import { FormInputProps } from '../../types/form'

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
        },
        {
            label: 'Ulica',
            id: 'street',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Numer posiadłości',
            id: 'propertyNumber',
            initValue: '',
            type: 'input',
        },
        {
            label: 'Numer apartamentu',
            id: 'apartmentNumber',
            initValue: '',
            type: 'input',
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
        },
        {
            label: 'x',
            id: 'xCoordinate',
            initValue: '',
            type: 'number',
            dontIncludeInFormStructure: true,
            readonly: true,
        },
        {
            label: 'y',
            id: 'yCoordinate',
            initValue: '',
            type: 'number',
            dontIncludeInFormStructure: true,
            readonly: true,
        },
    ]
}
