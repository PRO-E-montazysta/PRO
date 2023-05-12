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
    title: string
}

const Localization = (props: LocalizationProps) => {
    const { title, formStructure, formik, pageMode } = props
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
        }
    }, [formik.values])

    const search = async () => {
        if (pageMode != 'read') {
            const location = await getLocationFromAddress(formik.values)
            formik.setValues(location)
            setCoordinates({
                lat: location.xcoordinate!,
                lon: location.ycoordinate!,
            })
        }
    }

    const setLocationByCoordinates = async (coords: Coordinates) => {
        const location = await getLocationFromCoordinates(coords)
        formik.setValues(location)
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
            <FormPaper sx={{ mt: 0, pt: '10px' }}>
                <Typography variant="h5" sx={{ mb: '20px' }}>
                    {title}
                </Typography>
                <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
                {pageMode != 'read' && (
                    <Button
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
                    coords={coordinates ? coordinates : userCoordintates}
                    popupText={'lel'}
                    handleCoordinatesChange={onCoordinatesChange}
                    pageMode={pageMode}
                    style={{
                        marginTop: '20px',
                        width: '100%',
                        height: '500px',
                    }}
                />
            </FormPaper>
        </FormBox>
    )
}

export default Localization
