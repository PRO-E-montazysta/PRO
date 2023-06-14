import { Box, IconButton, Typography } from '@mui/material'
import ForwardIcon from '@mui/icons-material/Forward'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useLayoutEffect, useState } from 'react'
import { getAllFitter } from '../../api/fitter.api'

export type PopupPlannerInfo = {
    isOpen: boolean
    stageId?: string
}

export const cleanPlannerInfo: PopupPlannerInfo = {
    isOpen: false,
    stageId: '',
}

type FittersListProps = {
    title: string
    list: number[]
    arrayType: 'right' | 'left'
    addFitter?: (id: number) => void
    removeFitter?: (id: number) => void
    readonly: boolean
}
const FittersList = ({ list, title, arrayType, addFitter, removeFitter, readonly }: FittersListProps) => {
    const [fittersData, setFittersData] = useState<any[]>([])
    const fittersQuery = useQuery<any[], AxiosError>([], () => getAllFitter())

    useLayoutEffect(() => {
        if (fittersQuery.data) setFittersData(fittersQuery.data)
    }, [fittersQuery.data])

    const handleAddFitter = (id: number) => {
        if (addFitter) addFitter(id)
    }
    const handleRemoveFitter = (id: number) => {
        if (removeFitter) removeFitter(id)
    }

    const getFitterNameById = (id: number): string => {
        const thisFitter = fittersData.find((f) => f.id == id)
        if (thisFitter) return thisFitter.firstName + ' ' + thisFitter.lastName
        else return ''
    }
    return (
        <Box>
            <Typography variant="h6" sx={{ fontWeight: '700', mb: '5px' }}>
                {title}
            </Typography>
            <Box
                sx={{
                    border: '2px solid gray',
                    overflowY: 'auto',
                    height: '300px',
                    borderRadius: '10px',
                }}
            >
                {list &&
                    list.map((f, i) => {
                        return (
                            <Box
                                key={i}
                                sx={{
                                    m: '5px 10px',
                                    alignItems: 'center',
                                    justifyContent: 'space-between',
                                    display: 'flex',
                                    height: '30px',
                                }}
                            >
                                {arrayType == 'left' && (
                                    <Box>
                                        {!readonly ? (
                                            <IconButton
                                                onClick={() => handleAddFitter(f)}
                                                size="small"
                                                sx={{ transform: 'rotate(180deg)' }}
                                            >
                                                <ForwardIcon />
                                            </IconButton>
                                        ) : (
                                            <Box></Box>
                                        )}
                                    </Box>
                                )}
                                {getFitterNameById(f)}
                                {arrayType == 'right' && (
                                    <Box>
                                        {!readonly ? (
                                            <IconButton size="small" onClick={() => handleRemoveFitter(f)}>
                                                <ForwardIcon />
                                            </IconButton>
                                        ) : (
                                            <Box></Box>
                                        )}
                                    </Box>
                                )}
                            </Box>
                        )
                    })}
            </Box>
        </Box>
    )
}
export default FittersList
