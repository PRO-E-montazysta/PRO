import { Box, Menu, MenuItem } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import { PageProps } from '../../utils/pageList'

import { theme } from '../../themes/baseTheme'
import NavMenuItem from './NavMenuItem'
import { useEffect, useRef, useState } from 'react'

type NavMenuParams = {
    open: boolean
    allowedChilds: Array<PageProps>
    onClose: () => void
}

const NavMenu = (params: NavMenuParams) => {
    const { open, allowedChilds, onClose } = params

    const [shadow, setShadow] = useState<boolean>()

    useEffect(() => {
        if (open) setShadow(open)
        else
            setTimeout(() => {
                setShadow(open)
            }, 500)
    }, [open])

    const navigate = useNavigate()
    const handleMenuItemClick = (page: PageProps) => {
        navigate(page.path)
        onClose()
    }

    return (
        <>
            <Box
                sx={{
                    transitionDuration: '.5s',
                    height: open ? allowedChilds.length * 50 + 'px' : 0,
                    position: 'absolute',
                    backgroundColor: theme.palette.primary.main,
                    borderRadius: '5px',
                    boxShadow: shadow ? '0px 0px 2px 2px ' + theme.palette.primary.dark : '',
                    overflow: 'hidden',
                }}
            >
                {allowedChilds.map((child, index) => {
                    return <NavMenuItem onItemClick={() => handleMenuItemClick(child)} text={child.name} key={index} />
                })}
            </Box>
        </>
    )
}

export default NavMenu
