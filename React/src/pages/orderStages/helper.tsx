import { Box, Typography } from '@mui/material'
import IconButton, { IconButtonProps } from '@mui/material/IconButton'
import { styled } from '@mui/material/styles'

type TabPanelProps = {
    children?: React.ReactNode
    index: number
    value: number
}

export const TabPanel = (props: TabPanelProps) => {
    const { children, value, index, ...other } = props

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    )
}

interface ExpandMoreProps extends IconButtonProps {
    expand: boolean
}

export const ExpandMore = styled((props: ExpandMoreProps) => {
    const { expand, ...other } = props
    return <IconButton {...other} />
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}))
