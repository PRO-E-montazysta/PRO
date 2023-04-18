import { MenuItem, SxProps } from '@mui/material'
import { CSSProperties } from '@mui/styled-engine'
import { ReactNode } from 'react'

type NavMenuItemProps = {
    onItemClick: () => void
    text?: string
    child?: ReactNode
    sx?: SxProps
}

const NavMenuItem = (props: NavMenuItemProps) => {
    const { onItemClick, text, sx, child } = props
    return (
        <MenuItem sx={{ p: '0 20px', position: 'relative', height: '50px', ...sx }} onClick={onItemClick}>
            {child ? child : null}
            {text ? text : ''}
        </MenuItem>
    )
}

export default NavMenuItem
