import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useState } from 'react'
import { useParams } from 'react-router-dom'
import { styled } from '@mui/material/styles'
import Card from '@mui/material/Card'
import CardHeader from '@mui/material/CardHeader'
import CardContent from '@mui/material/CardContent'
import CardActions from '@mui/material/CardActions'
import Collapse from '@mui/material/Collapse'
import Avatar from '@mui/material/Avatar'
import IconButton, { IconButtonProps } from '@mui/material/IconButton'
import Typography from '@mui/material/Typography'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import WorkHistoryIcon from '@mui/icons-material/WorkHistory'
import { Grid } from '@mui/material'
import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar'

interface CustomExpandMoreProps extends IconButtonProps {
    expand: boolean
}

const CustomExpandMore = styled((props: CustomExpandMoreProps) => {
    const { expand, ...other } = props
    return <IconButton {...other} />
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}))

type ExpandMoreProps = {
    title: string;
    cardContent: JSX.Element;
}

const ExpandMore = ({ title, cardContent}: ExpandMoreProps) => {
    const [expandedInformation, setExpandedInformation] = useState(false)

    const handleExpandInformationClick = () => {
        setExpandedInformation(!expandedInformation)
    }

    return (
        <>
            <CardActions disableSpacing>
                <IconButton aria-label="share">
                    <PermContactCalendarIcon />
                </IconButton>
                <Typography variant="body2" color="text.secondary">
                    {title}
                </Typography>
                <CustomExpandMore
                    expand={expandedInformation}
                    onClick={handleExpandInformationClick}
                    aria-expanded={expandedInformation}
                    aria-label="show more"
                >
                    <ExpandMoreIcon />
                </CustomExpandMore>
            </CardActions>
            <Collapse in={expandedInformation} timeout="auto" unmountOnExit>
                <CardContent>
                    {/* <Typography variant="body2" color="text.secondary">
                        Działa
                    </Typography> */}
                    {cardContent}
                </CardContent>
            </Collapse>
        </>
    )
}

export default ExpandMore
