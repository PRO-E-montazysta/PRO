import { MenuItem } from '@mui/material'

type NotiMenuItemProps = {
    onItemClick: () => void
    text: string
    id: string
}

const NotiMenuItem = (props: NotiMenuItemProps) => {
    const { onItemClick, text, id } = props
    return (
        <MenuItem
            id={id}
            sx={{
                p: '10px 20px',
                position: 'relative',
                height: '40px',
                fontSize: '14px',
                display: 'block',
                justifyContent: 'space-evenly',
            }}
            onClick={onItemClick}
        >
            {text}
        </MenuItem>
    )
}

export default NotiMenuItem
