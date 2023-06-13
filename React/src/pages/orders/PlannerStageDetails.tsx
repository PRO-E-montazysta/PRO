import { Box, Typography } from '@mui/material'
import { Moment } from 'moment'
import moment from 'moment'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useState, useLayoutEffect } from 'react'
import { getFittersAvailable } from '../../api/fitter.api'
import FittersList from './FittersList'

type PlannerStageDetailsProps = {
    dateFrom: string | undefined
    dateTo: string | undefined
    fitters: number[]
    orderStageId: string | undefined
    setFitters: (fitters: number[]) => void
    readonly: boolean
}

const PlannerStageDetails = ({
    dateFrom,
    dateTo,
    fitters,
    setFitters,
    readonly,
    orderStageId,
}: PlannerStageDetailsProps) => {
    const [avaliableFitters, setAvaliableFitters] = useState<number[]>([])

    const avaliableFittersQuery = useQuery<any[], AxiosError>(
        ['avaliable-fitters', { id: orderStageId }],
        () => getFittersAvailable(dateFrom!, dateTo!),
        {
            enabled: dateFrom != undefined && dateTo != undefined,
        },
    )

    useLayoutEffect(() => {
        if (avaliableFittersQuery.data && fitters) {
            console.log(fitters)
            setAvaliableFitters(avaliableFittersQuery.data.map((d) => d.id).filter((d) => fitters.indexOf(d) == -1))
        }
    }, [avaliableFittersQuery.data, fitters])

    const addFitter = (id: number) => {
        setFitters([...fitters, id])
        setAvaliableFitters(avaliableFitters.filter((f) => f != id))
    }
    const removeFitter = (id: number) => {
        setAvaliableFitters([...avaliableFitters, id])
        setFitters(fitters.filter((f) => f != id))
    }

    return (
        <Box>
            <Box
                sx={{
                    display: 'grid',
                    gridTemplateColumns: '1fr 1fr',
                    gap: '10px 20px',
                }}
            >
                <Box>
                    <Typography variant="body2">Rozpoczęcie</Typography>
                    <Typography variant="body1" sx={{ fontWeight: '700' }}>
                        {moment(dateFrom).format('HH:mm DD-MM-YYYY')}
                    </Typography>
                </Box>
                <Box>
                    <Typography variant="body2">Zakończenie</Typography>
                    <Typography variant="body1" sx={{ fontWeight: '700' }}>
                        {moment(dateTo).format('HH:mm DD-MM-YYYY')}
                    </Typography>
                </Box>
                <FittersList
                    key={'signed'}
                    title="Przypisani"
                    arrayType="right"
                    list={fitters}
                    removeFitter={removeFitter}
                    readonly={readonly}
                />
                <FittersList
                    key={'avaliable'}
                    title="Dostępni"
                    arrayType="left"
                    list={avaliableFitters}
                    addFitter={addFitter}
                    readonly={readonly}
                />
            </Box>
        </Box>
    )
}

export default PlannerStageDetails
