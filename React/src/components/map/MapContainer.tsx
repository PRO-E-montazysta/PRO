import { useEffect, useState } from 'react'
import Map from './Map'
import { getPositionError } from './helper'

export default function MapContainer() {
    const [coords, setCorrds] = useState({
        latitude: 0,
        longitude: 0,
    })
    const [popupText, setPopupText] = useState('')
    const [address, setAddress] = useState({
        street: '',
        city: '',
        number: '',
        country: 'Polska',
        postalcode: '',
    })

    const options = {
        enableHighAccuracy: true,
        maximumAge: 30000,
        timeout: 27000,
    }

    useEffect(() => {
        navigator.geolocation.getCurrentPosition(getCurrentCityName, getPositionError, options)
    }, [])

    //get current location when the app loads for the first time
    const getCurrentCityName = (position: any) => {
        setCorrds({
            latitude: position.coords.latitude,
            longitude: position.coords.longitude,
        })

        let url =
            'https://nominatim.openstreetmap.org/reverse?format=jsonv2' +
            '&lat=' +
            coords.latitude +
            '&lon=' +
            coords.longitude

        fetch(url, {
            method: 'GET',
            mode: 'cors',
            headers: {
                'Access-Control-Allow-Origin': 'https://o2cj2q.csb.app',
            },
        })
            .then((response) => response.json())
            .then((data) => setPopupText(data.display_name))
    }

    //get input from text fields and append it to address object
    const update = (field: any) => {
        return (e: any) => {
            const value = e.currentTarget.value
            setAddress((address) => ({ ...address, [field]: value }))
        }
    }

    //send the data on the state to the API
    const getData = (url: any) => {
        fetch(url, {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Access-Control-Allow-Origin': 'https://o2cj2q.csb.app',
            },
        })
            .then((response) => {
                if (response.ok) {
                    return response.json()
                }
            })
            .then((data) => {
                setPopupText(data[0].display_name)
                setCorrds({
                    latitude: data[0].lat,
                    longitude: data[0].lon,
                })
            })
            .catch(() => getPositionError('Please Check your input'))
    }

    //set form input( data entered ) to state on form submit
    const submitHandler = (e: any) => {
        e.preventDefault()
        console.log(address)

        let url = `https://nominatim.openstreetmap.org/search?
    street=${address.street}
    &city=${address.city}
    &country=${address.country}
    &postalcode=${address.postalcode}&format=json`

        getData(url)
    }

    return (
        <div>
            <h1>Lokalizacja</h1>
            <section>
                <form>
                    <label>Miasto:</label>
                    <input type="text" value={address.city} onChange={update('city')} id="city" />
                    <br />
                    <label>Ulica:</label>
                    <input value={address.street} onChange={update('street')} id="street" type="text" />
                    <label>Numer:</label>
                    <input type="number" value={address.number} onChange={update('number')} id="number" />
                    <label>Kod pocztowy:</label>
                    <input type="text" value={address.postalcode} onChange={update('postalcode')} id="postalcode" />
                    <br />
                    <button onClick={(e) => submitHandler(e)}>Search</button>
                </form>
            </section>
            <Map coords={coords} popupText={popupText} />
        </div>
    )
}
