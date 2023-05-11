import { MapContainer, TileLayer, Marker, Popup, useMap } from 'react-leaflet'
import React, { useCallback, useEffect } from 'react'
import 'leaflet/dist/leaflet.css'
import icon from '../../assets/img/logo.png'
import L from 'leaflet'
import { Coordinates } from './helper'

type MapProps = {
    coords: Coordinates
    popupText: string
    handleCoordinatesChange: (coords: Coordinates) => void
}

export default function Map(props: MapProps) {
    const { coords, popupText, handleCoordinatesChange } = props
    const customIcon = new L.Icon({
        //creating a custom icon to use in Marker
        iconUrl: icon,
        iconSize: [25, 35],
        iconAnchor: [5, 30],
    })

    return (
        <MapContainer
            center={[coords.lat, coords.lon]}
            zoom={15}
            scrollWheelZoom={true}
            style={{
                width: '500px',
                height: '500px',
            }}
        >
            <TileLayer
                attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> 
        contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <Marker icon={customIcon} position={[coords.lat, coords.lon]}>
                <Popup>{popupText}</Popup>
            </Marker>
            <MapView coords={coords} handleCoordinatesChange={handleCoordinatesChange} />
        </MapContainer>
    )
}
type MapViewProps = {
    coords: Coordinates
    handleCoordinatesChange: (coords: Coordinates) => void
}
const MapView = (props: MapViewProps) => {
    const { coords, handleCoordinatesChange } = props
    const map = useMap()
    console.log('useMap')
    map.setView([coords.lat, coords.lon], map.getZoom())
    //Sets geographical center and zoom for the view of the map
    const mapOnClick = (e: any) => {
        handleCoordinatesChange({
            lat: e.latlng.lat,
            lon: e.latlng.lng,
        })
    }
    useEffect(() => {
        map.on('click', mapOnClick)
        return () => {
            map.off('click', mapOnClick)
        }
    }, [])
    return null
}
