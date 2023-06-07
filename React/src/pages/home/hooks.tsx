import { useQuery } from 'react-query'
import { Unavailability } from '../../types/model/Unavailability'
import { getUnavailabilityByMonth } from '../../api/unavailability.api'
import { AxiosError } from 'axios'

export const useUnavailabilityByMonth = (year: number, month: number) => {
    return useQuery<Unavailability, AxiosError>(['unavailability', { month: month, year: year }], async () =>
        getUnavailabilityByMonth({
            queryParams: {
                month: month,
                year: year,
            },
        }),
    )
}
