import { useEffect, useState } from 'react'
import Map from './Map'
import { Coordinates, getLocationFromAddress, getLocationFromCoordinates, getPositionError, options } from './helper'
import { Location } from '../../types/model/Location'

export default function MapContainer() {
    const [popupText, setPopupText] = useState('')
    const [currentCoordinates, setCurrentCoordinates] = useState<Coordinates>({
        lat: 0,
        lon: 0,
    })

    const [location, setLocation] = useState<Location>({
        id: 0,
        city: '',
        street: '',
        propertyNumber: '',
        apartmentNumber: '',
        xCoordinate: 0,
        yCoordinate: 0,
        zipCode: '',
    })

    useEffect(() => {
        navigator.geolocation.getCurrentPosition(
            (position: any) => {
                setCurrentCoordinates({
                    lat: position.coords.latitude,
                    lon: position.coords.longitude,
                })
            },
            getPositionError,
            options,
        )
    }, [])
    useEffect(() => {
        setMapOnMe()
    }, [currentCoordinates])

    const setMapOnMe = async (e?: any) => {
        const location = await getLocationFromCoordinates(currentCoordinates)
        setLocation(location)
    }

    //get input from text fields and append it to address object
    const update = (field: any) => {
        return (e: any) => {
            const value = e.currentTarget.value
            setLocation((location) => ({ ...location, [field]: value }))
        }
    }

    const onSubmit = async (e: any) => {
        e.preventDefault()
        const loc = await getLocationFromAddress(location)
        console.log(loc)
        setLocation(loc)
    }

    const onCoordinatesChange = async (coords: Coordinates) => {
        console.log(coords)
        const loc = await getLocationFromCoordinates(coords)
        console.log(loc)
        setLocation(loc)
    }

    return (
        <div>
            <h1>Lokalizacja</h1>
            <section>
                <form>
                    <label>Miasto:</label>
                    <input type="text" value={location.city} onChange={update('city')} id="city" />
                    <br />
                    <label>Ulica:</label>
                    <input value={location.street} onChange={update('street')} id="street" type="text" />
                    <label>Numer:</label>
                    <input
                        type="text"
                        value={location.propertyNumber}
                        onChange={update('propertyNumber')}
                        id="propertyNumber"
                    />
                    <label>Kod pocztowy:</label>
                    <input type="text" value={location.zipCode} onChange={update('zipCode')} id="zipCode" />

                    <label>x:</label>
                    <input readOnly type="text" value={location.xCoordinate} id="xCoordinate" />
                    <label>y:</label>
                    <input readOnly type="text" value={location.yCoordinate} id="yCoordinate" />

                    <br />
                    <button onClick={(e) => onSubmit(e)}>Search</button>
                    <button type="button" onClick={setMapOnMe}>
                        My location
                    </button>
                </form>
            </section>
            <Map
                coords={{ lat: location.xCoordinate, lon: location.yCoordinate }}
                popupText={popupText}
                handleCoordinatesChange={onCoordinatesChange}
            />
        </div>
    )
}
