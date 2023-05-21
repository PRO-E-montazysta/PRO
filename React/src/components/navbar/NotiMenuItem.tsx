import { MenuItem, Typography } from '@mui/material'
import { theme } from '../../themes/baseTheme'

type NotiMenuItemProps = {
    onItemClick: () => void
    text: string
    subtext?: string
    id: string
}

const NotiMenuItem = (props: NotiMenuItemProps) => {
    const { onItemClick, text, subtext, id } = props
    let height = subtext ? '60px' : '40px'
    return (
        <MenuItem
            id={id}
            sx={{
                p: '10px 20px',
                position: 'relative',
                height: { height },
                fontSize: '14px',
                display: 'block',
                justifyContent: 'space-evenly',
            }}
            onClick={onItemClick}
        >
            {text}
            {subtext ? (
                <Typography sx={{ color: theme.palette.primary.light, fontSize: '13px' }}>{subtext}</Typography>
            ) : null}
        </MenuItem>
    )
}

export default NotiMenuItem
