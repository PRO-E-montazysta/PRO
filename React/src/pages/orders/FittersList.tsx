import { Box, IconButton, Typography } from '@mui/material'
import ForwardIcon from '@mui/icons-material/Forward'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useEffect, useState } from 'react'
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
}
const FittersList = ({ list, title, arrayType, addFitter, removeFitter }: FittersListProps) => {
    const [fittersData, setFittersData] = useState<any[]>([])
    const fittersQuery = useQuery<any[], AxiosError>([], () => getAllFitter())
    useEffect(() => {
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
        return thisFitter.firstName + ' ' + thisFitter.lastName
    }
    return (
        <Box>
            <Typography>{title}</Typography>
            <Box sx={{ border: '1px solid black', overflowY: 'scroll', height: '300px' }}>
                {list.map((f, i) => {
                    return (
                        <Box
                            key={i}
                            sx={{ p: '0 5px', alignItems: 'center', justifyContent: 'space-between', display: 'flex' }}
                        >
                            {arrayType == 'left' ? (
                                <IconButton
                                    onClick={() => handleAddFitter(f)}
                                    size="small"
                                    sx={{ transform: 'rotate(180deg)' }}
                                >
                                    <ForwardIcon />
                                </IconButton>
                            ) : null}
                            {getFitterNameById(f)}
                            {arrayType == 'right' ? (
                                <IconButton size="small" onClick={() => handleRemoveFitter(f)}>
                                    <ForwardIcon />
                                </IconButton>
                            ) : null}
                        </Box>
                    )
                })}
            </Box>
        </Box>
    )
}
export default FittersList
