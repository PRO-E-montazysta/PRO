import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useState } from 'react'
import { useParams } from 'react-router-dom'
import { Employee } from '../../../types/model/Employee'
import Card from '@mui/material/Card'
import CardHeader from '@mui/material/CardHeader'
import Avatar from '@mui/material/Avatar'
import IconButton from '@mui/material/IconButton'
import Typography from '@mui/material/Typography'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import WorkHistoryIcon from '@mui/icons-material/WorkHistory'
import { Grid } from '@mui/material'
import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar'
import { getUserById } from '../../../api/user.api'
import ExpandMore from '../../../components/expandMore/ExpandMore'


const EmpDetails = () => {
    const params = useParams()

    const queryData = useQuery<Employee, AxiosError>(
        ['users', { id: params.id }],
        async () => getUserById(params.id!),
        {
            enabled: !!params.id,
        },
    )

    const getUserRoles = () => {
        return queryData.data?.roles.map((role) => role)
    }

    const getStatusDescription = () => {
        if (queryData.data?.status === 'AVAIBLE') {
            return <p>'Pracownik jest dostępny'</p>
        } else {
            return <p>{queryData.data?.unavailbilityDescription}</p>
        }
    }

    return (
        <Grid container alignItems="center" justifyContent="center" marginTop={2}>
            <Card sx={{ minWidth: 500, left: '50%' }}>
                <CardHeader
                    avatar={
                        <Avatar sx={{ bgcolor: 'red' }} aria-label="recipe">
                            R
                        </Avatar>
                    }
                    action={
                        <IconButton aria-label="settings">
                            <MoreVertIcon />
                        </IconButton>
                    }
                    title={`${queryData.data?.firstName} ${queryData.data?.lastName}`}
                    subheader={getUserRoles()}
                />

                <ExpandMore
                    titleIcon={<PermContactCalendarIcon />}
                    title="Informacje kontaktowe"
                    cardContent={
                        <Typography variant="body2" color="text.secondary">
                            Numer telefonu: {queryData.data?.phone}, E-mail: {queryData.data?.email}
                        </Typography>
                    }
                />

                <ExpandMore
                    titleIcon={<WorkHistoryIcon />}
                    title={`Status: ${queryData.data?.status}`}
                    cardContent={getStatusDescription() || <p>historia</p>}
                />

                <ExpandMore
                    titleIcon={<WorkHistoryIcon />}
                    title="Historia pracy w tabeli"
                    cardContent={<p>Możemy tu wyświetlać historię pracy. Zlecenie, etap, data.</p>}
                />
            </Card>
        </Grid>
    )
}

export default EmpDetails
