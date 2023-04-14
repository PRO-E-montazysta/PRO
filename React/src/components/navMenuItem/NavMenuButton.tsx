import { Button, Menu, MenuItem } from '@mui/material'
import { useEffect, useLayoutEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { isAuthorized } from '../../utils/authorize'
import { PageProps } from '../../utils/pageList'

import { v4 as uuidv4 } from 'uuid'

const NavMenuButton = (props: PageProps) => {
    const { allowedRoles, inNav, name, path, children } = props

    const navigate = useNavigate()
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null)
    const open = Boolean(anchorEl)
    const [allowedChilds, setAllowedChilds] = useState<Array<PageProps>>([])

    useLayoutEffect(() => {
        if (!!children) setAllowedChilds(children.filter((child) => child.inNav && isAuthorized(child)))
    }, [])

    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        if (allowedChilds && allowedChilds.length > 0) setAnchorEl(event.currentTarget)
        else navigate(path)
    }
    const handleClose = () => {
        setAnchorEl(null)
    }

    const handleMenuItemClick = (page: PageProps) => {
        navigate(page.path)
        handleClose()
    }

    if (!inNav || !isAuthorized(props)) return null

    return (
        <>
            <Button
                style={{ minWidth: 'fit-content' }}
                aria-controls={open ? 'basic-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={open ? 'true' : undefined}
                onClick={handleClick}
            >
                {name}
            </Button>
            <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                MenuListProps={{
                    'aria-labelledby': 'basic-button',
                }}
            >
                {allowedChilds.map((child) => {
                    return (
                        <MenuItem onClick={() => handleMenuItemClick(child)} key={uuidv4()}>
                            {child.name}
                        </MenuItem>
                    )
                })}
            </Menu>
        </>
    )
}

export default NavMenuButton
