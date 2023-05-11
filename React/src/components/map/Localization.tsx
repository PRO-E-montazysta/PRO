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
    const [initCoordinates, setInitCoordinates] = useState<Coordinates>({
        lat: formik.values.xCoordinate,
        lon: formik.values.yCoordinate,
    })

    useEffect(() => {
        if (!formik.values.xCoordinate && !formik.values.yCoordinate)
            navigator.geolocation.getCurrentPosition(
                (position: any) => {
                    setLocationByCoordinates({
                        lat: position.coords.latitude,
                        lon: position.coords.longitude,
                    })
                },
                getPositionError,
                options,
            )
    }, [formik.values])


    const search = async () => {
        if (pageMode != 'read') {
            const location = await getLocationFromAddress(formik.values)
            formik.setValues(location)
            setInitCoordinates({
                lat: location.xCoordinate,
                lon: location.yCoordinate,
            })
        }
    }

    const setLocationByCoordinates = async (coords: Coordinates) => {
        const location = await getLocationFromCoordinates(coords)
        formik.setValues(location)
        setInitCoordinates({
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
                        Szukaj
                    </Button>
                )}

                <Map
                    coords={initCoordinates}
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
