import axios from 'axios'
import { Location } from '../../types/model/Location'
import Localization from './Localization'

export type Coordinates = {
    lat: number
    lon: number
}

export const getPositionError = (err: any) => {
    if (
        err.code === 1 || //if user denied accessing the location
        err.code === 2 || //for any internal errors
        err.code === 3 //error due to timeout
    ) {
        alert(err.message)
    } else {
        alert(err)
    }
}

export const getLocationFromCoordinates = async (coords: Coordinates): Promise<Partial<Location>> => {
    const url =
        'https://nominatim.openstreetmap.org/reverse?format=jsonv2' + '&lat=' + coords.lat + '&lon=' + coords.lon
    let localization: Partial<Location> = {
        city: '',
        street: '',
        propertyNumber: '',
        apartmentNumber: '',
        zipCode: '',
        xcoordinate: coords.lat,
        ycoordinate: coords.lon,
    }

    const reposnose = await axios(url, {
        method: 'GET',
        headers: {
            'Access-Control-Allow-Origin': 'https://o2cj2q.csb.app',
        },
    })
    if (reposnose && reposnose.data && reposnose.data.address) {
        const address = reposnose.data.address
        localization.city = address.city
            ? address.city
            : address.town
            ? address.town
            : address.village
            ? address.village
            : ''
        localization.street = address.road ? address.road : ''
        localization.propertyNumber = address.house_number ? address.house_number : ''
        localization.zipCode = address.postcode ? address.postcode : ''
    }
    return localization
}

export const getLocationFromAddress = async (location: Location): Promise<Partial<Location>> => {
    const COUNTRY = 'Polska'
    const url = `https://nominatim.openstreetmap.org/search?
    street=${location.street + ' ' + location.propertyNumber}
    &city=${location.city}
    &country=${COUNTRY}
    &postalcode=${location.zipCode}&format=json`

    let localization: Partial<Location> = {
        city: location.city,
        street: location.street,
        propertyNumber: location.propertyNumber,
        apartmentNumber: location.apartmentNumber,
        zipCode: location.zipCode,
        xcoordinate: location.xcoordinate,
        ycoordinate: location.ycoordinate,
    }

    const response = await axios(url, {
        method: 'POST',
        headers: {
            'Access-Control-Allow-Origin': 'https://o2cj2q.csb.app',
        },
    })
    if (response && response.data && response.data.length > 0) {
        localization.xcoordinate = response.data[0].lat
        localization.ycoordinate = response.data[0].lon
    }

    return localization
}

export const options = {
    enableHighAccuracy: true,
    maximumAge: 30000,
    timeout: 27000,
}
