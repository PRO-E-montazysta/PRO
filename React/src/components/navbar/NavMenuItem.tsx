import { MenuItem } from '@mui/material'

type NavMenuItemProps = {
    onItemClick: () => void
    text: string
}

const NavMenuItem = (props: NavMenuItemProps) => {
    const { onItemClick, text } = props
    return (
        <MenuItem sx={{ p: '0 20px', position: 'relative', height: '50px' }} onClick={onItemClick}>
            {text}
        </MenuItem>
    )
}

export default NavMenuItem
