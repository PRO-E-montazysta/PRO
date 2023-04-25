import { Button } from '@mui/material'
import { CSSProperties, MouseEvent, useLayoutEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { isAuthorizedToPage } from '../../utils/authorize'
import { PageProps } from '../../utils/pageList'

import NavMenu from './NavMenu'

const NavMenuButton = (props: PageProps) => {
    const { allowedRoles, inNav, name, path, children } = props

    const navigate = useNavigate()

    const [open, setOpen] = useState(false)
    const [allowedChilds, setAllowedChilds] = useState<Array<PageProps>>([])

    useLayoutEffect(() => {
        if (!!children) setAllowedChilds(children.filter((child) => child.inNav && isAuthorizedToPage(child)))
    }, [])

    const handleClick = () => {
        if (allowedChilds && allowedChilds.length > 0) setOpen(true)
        else navigate(path)
    }

    const handleMouseEnter = () => {
        if (allowedChilds && allowedChilds.length > 0) setOpen(true)
    }

    const handleClose = () => {
        setOpen(false)
    }

    return (
        <>
            <div
                id={path}
                onMouseOver={handleMouseEnter}
                onMouseLeave={handleClose}
                style={{ zIndex: open ? 1000 : 5 }}
            >
                <Button sx={{ m: '0 5px', cursor: 'pointer', whiteSpace: 'nowrap' }} onClick={handleClick}>
                    {name}
                </Button>
                <NavMenu allowedChilds={allowedChilds} onClose={handleClose} open={open} />
            </div>
        </>
    )
}

export default NavMenuButton
