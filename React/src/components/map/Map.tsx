import { MapContainer, TileLayer, Marker, Popup, useMap } from 'react-leaflet'
import React, { CSSProperties, useEffect } from 'react'
import 'leaflet/dist/leaflet.css'
import icon from '../../assets/img/logo.png'
import L from 'leaflet'
import { Coordinates } from './helper'
import { PageMode } from '../../types/form'

type MapProps = {
    coords: Coordinates
    popupText: string
    pageMode?: PageMode
    handleCoordinatesChange: (coords: Coordinates) => void
    style?: CSSProperties
}

export default function Map(props: MapProps) {
    const { coords, popupText, handleCoordinatesChange, pageMode, style } = props
    const customIcon = new L.Icon({
        //creating a custom icon to use in Marker
        iconUrl: icon,
        iconSize: [30, 30],
        iconAnchor: [5, 30],
    })

    return (
        <MapContainer center={[coords.lat, coords.lon]} zoom={15} scrollWheelZoom={true} style={style}>
            <TileLayer
                attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> 
        contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <Marker icon={customIcon} position={[coords.lat, coords.lon]}>
                <Popup>{popupText}</Popup>
            </Marker>
            <MapView coords={coords} handleCoordinatesChange={handleCoordinatesChange} pageMode={pageMode} />
        </MapContainer>
    )
}
type MapViewProps = {
    coords: Coordinates
    pageMode?: PageMode
    handleCoordinatesChange: (coords: Coordinates, pageMode?: PageMode) => void
}
const MapView = (props: MapViewProps) => {
    const { coords, handleCoordinatesChange, pageMode } = props
    const map = useMap()
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
    }, [pageMode])
    return null
}
