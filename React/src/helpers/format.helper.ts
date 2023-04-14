import { SelectMenuItemProps } from '../components/form/types'
import { Location } from '../types/model/Location'

export const formatDate = (date: string) => {
    if (!date) return ''
    return date.slice(0, 16).replace('T', ' ')
}

export const formatShortDate = (date: string) => {
    if (!date) return ''
    return date.slice(0, 10)
}

export const formatLocation = (location: Location) => {
    if (!location) return ''
    else {
        let result = ''
        result += location.name ? location.name + ' - ' : ''
        result += location.street
            ? 'ul. ' +
              location.street +
              ' ' +
              location.propertyNumber +
              (location.apartmentNumber ? '/' + location.apartmentNumber : '')
            : ''
        result += location.city ? ', ' + location.city + ' ' : ''
        result += location.zipCode ? location.zipCode : ''
        return result
    }
}

export const formatArrayToOptions = (
    keyProp: string,
    formatFn: (value: any) => string,
    array?: Array<any>,
): SelectMenuItemProps[] => {
    return array
        ? array.map((x) => {
              const value = formatFn(x)
              return {
                  key: x[keyProp],
                  value: value,
              }
          })
        : []
}
