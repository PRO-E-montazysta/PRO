import { Box, Button, Typography } from '@mui/material'
import { Location } from '../../types/model/Location'
import FormBox from '../form/FormBox'
import { useFormik } from 'formik'
import { FormStructure, FormStructureParams } from '../form/FormStructure'
import FormPaper from '../form/FormPaper'
import useBreakpoints from '../../hooks/useBreakpoints'
import { useEffect, useState } from 'react'
import { Coordinates, getLocationFromAddress, getLocationFromCoordinates, getPositionError, options } from './helper'
import Map from './Map'
import { FormInputProps, PageMode } from '../../types/form'

type LocalizationProps = {
    formStructure: Array<FormInputProps>
    formik: any
    pageMode?: PageMode
    invokeSearch?: boolean
}

const Localization = (props: LocalizationProps) => {
    const { formStructure, formik, pageMode, invokeSearch } = props
    const appSize = useBreakpoints()
    const [coordinates, setCoordinates] = useState<Coordinates | null>(null)

    const [userCoordintates, setUserCoordinates] = useState<Coordinates>({
        lat: 0,
        lon: 0,
    })

    useEffect(() => {
        navigator.geolocation.getCurrentPosition(
            (position: any) => {
                setUserCoordinates({
                    lat: position.coords.latitude,
                    lon: position.coords.longitude,
                })
            },
            getPositionError,
            options,
        )
    }, [])

    useEffect(() => {
        if (formik.values.xcoordinate && formik.values.ycoordinate) {
            setCoordinates({
                lat: formik.values.xcoordinate,
                lon: formik.values.ycoordinate,
            })
        } else setCoordinates(null)
    }, [formik.values])

    const search = async () => {
        if (pageMode != 'read') {
            const location = await getLocationFromAddress(formik.values)
            for (const [key, value] of Object.entries(location)) {
                formik.setFieldValue(key, value)
            }

            setCoordinates({
                lat: location.xcoordinate!,
                lon: location.ycoordinate!,
            })
        }
    }

    useEffect(() => {
        if (invokeSearch) search()
    }, [invokeSearch])
    const setLocationByCoordinates = async (coords: Coordinates) => {
        const location = await getLocationFromCoordinates(coords)
        for (const [key, value] of Object.entries(location)) {
            formik.setFieldValue(key, value)
        }
        setCoordinates({
            lat: coords.lat,
            lon: coords.lon,
        })
    }

    const onCoordinatesChange = async (coords: Coordinates) => {
        if (pageMode != 'read') {
            await setLocationByCoordinates(coords)
        }
    }

    return (
        <FormBox>
            <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
            {pageMode != 'read' && (
                <Button
                    id="confirmLocation"
                    onClick={search}
                    variant="contained"
                    sx={{
                        mt: appSize.isMobile ? '20px' : '',
                        width: appSize.isMobile ? '100%' : '100px',
                        float: !appSize.isMobile ? 'right' : '',
                        transform: !appSize.isMobile ? 'translate(0, -100%)' : '',
                    }}
                >
                    Potwierdź
                </Button>
            )}
            <Map
                coords={coordinates?.lat && coordinates?.lon ? coordinates : userCoordintates}
                popupText={'Wskazana lokalizacja'}
                handleCoordinatesChange={onCoordinatesChange}
                pageMode={pageMode}
                style={{
                    marginTop: '20px',
                    width: '100%',
                    height: '500px',
                }}
            />
        </FormBox>
    )
}

export default Localization
