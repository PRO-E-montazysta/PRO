import { Box, Menu, MenuItem } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import { PageProps } from '../../utils/pageList'

import { theme } from '../../themes/baseTheme'

type NavMenuParams = {
    open: boolean
    allowedChilds: Array<PageProps>
    onClose: () => void
}

const NavMenu = (params: NavMenuParams) => {
    const { open, allowedChilds, onClose } = params

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
                    boxShadow: '0px 0px 2px 2px ' + theme.palette.primary.dark,
                    overflow: 'hidden',
                }}
            >
                {allowedChilds.map((child, index) => {
                    return (
                        <MenuItem
                            sx={{ p: '0 20px', position: 'relative', height: '50px' }}
                            onClick={() => handleMenuItemClick(child)}
                            key={index}
                        >
                            {child.name}
                        </MenuItem>
                    )
                })}
            </Box>
        </>
    )
}

export default NavMenu
