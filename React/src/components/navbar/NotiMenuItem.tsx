import { Box, MenuItem, Typography } from '@mui/material'

import { theme } from '../../themes/baseTheme'

type NotiMenuItemProps = {
    onItemClick: () => void
    text: string
    subtext?: string
    link?: string
    id: string
}

const NotiMenuItem = (props: NotiMenuItemProps) => {
    const { onItemClick, text, subtext, link, id } = props
    return (
        <MenuItem
            id={id}
            sx={{
                p: '10px 20px',
                position: 'relative',
                height: '60px',
                fontSize: '14px',
                display: 'block',
                justifyContent: 'space-evenly',
            }}
            onClick={onItemClick}
        >
            {text}
            {subtext ? (
                <Typography sx={{ color: theme.palette.primary.light, fontSize: '13px' }}>{subtext}</Typography>
            ) : (
                <Typography sx={{ color: theme.palette.primary.light, textDecoration: 'underline', fontSize: '12px' }}>
                    {link}
                </Typography>
            )}
        </MenuItem>
    )
}

export default NotiMenuItem
