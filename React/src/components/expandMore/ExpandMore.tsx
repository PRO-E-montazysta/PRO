import { useEffect, useState } from 'react'
import { styled } from '@mui/material/styles'
import CardContent from '@mui/material/CardContent'
import CardActions from '@mui/material/CardActions'
import Collapse from '@mui/material/Collapse'
import IconButton, { IconButtonProps } from '@mui/material/IconButton'
import Typography from '@mui/material/Typography'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'

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
    titleIcon: JSX.Element
    title: string
    cardContent: JSX.Element
    isOpen?: boolean
}

const ExpandMore = ({ titleIcon, title, cardContent, isOpen }: ExpandMoreProps) => {
    const [expandedInformation, setExpandedInformation] = useState(false)
    useEffect(() => {
        if (!!isOpen) setExpandedInformation(isOpen)
    }, [isOpen])

    const handleExpandInformationClick = () => {
        setExpandedInformation(!expandedInformation)
    }

    return (
        <>
            <CardActions disableSpacing>
                <IconButton id={`expandMore-${title}`} aria-label="share">
                    {titleIcon}
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
                <CardContent>{cardContent}</CardContent>
            </Collapse>
        </>
    )
}

export default ExpandMore
