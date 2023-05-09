import { MapContainer, TileLayer, Marker, Popup, useMap } from 'react-leaflet'
import React from 'react'
import 'leaflet/dist/leaflet.css'
import icon from '../../assets/img/logo.png'
import L from 'leaflet'

type MapProps = {
    coords: {
        latitude: number
        longitude: number
    }
    popupText: string
}

export default function Map(props: MapProps) {
    const { coords, popupText } = props
    const { latitude, longitude } = coords

    const customIcon = new L.Icon({
        //creating a custom icon to use in Marker
        iconUrl: icon,
        iconSize: [25, 35],
        iconAnchor: [5, 30],
    })

    const MapView = () => {
        let map = useMap()
        map.setView([latitude, longitude], map.getZoom())
        //Sets geographical center and zoom for the view of the map
        return null
    }

    return (
        <MapContainer
            center={[latitude, longitude]}
            zoom={10}
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
            <Marker icon={customIcon} position={[latitude, longitude]}>
                <Popup>{popupText}</Popup>
            </Marker>
            <MapView />
        </MapContainer>
    )
}
