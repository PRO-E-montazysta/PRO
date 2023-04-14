import { Button } from '@mui/material'
import { CSSProperties, MouseEvent, useLayoutEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { isAuthorized } from '../../utils/authorize'
import { PageProps } from '../../utils/pageList'

import NavMenu from './NavMenu'
import MoreVertIcon from '@mui/icons-material/MoreVert'

type MorePagesButtonProps = {
    pages: PageProps[]
}

const MorePagesButton = (props: MorePagesButtonProps) => {
    const { pages } = props
    const navigate = useNavigate()

    const [open, setOpen] = useState(false)
    const [allowedPages, setAllowedPages] = useState<Array<PageProps>>([])

    useLayoutEffect(() => {
        if (!!pages) setAllowedPages(pages.filter((child) => child.inNav && isAuthorized(child)))
    }, [])

    const handleMouseEnter = () => {
        if (allowedPages && allowedPages.length > 0) setOpen(true)
    }

    const handleClose = () => {
        setOpen(false)
    }

    return (
        <>
            <div onMouseOver={handleMouseEnter} onMouseLeave={handleClose} style={{ zIndex: open ? 1000 : 5 }}>
                <Button sx={{ cursor: 'pointer', whiteSpace: 'nowrap', width: '30px' , minWidth: 'auto' }} >
                    <MoreVertIcon />
                </Button>
                {/* <NavMenu allowedChilds={allowedChilds} onClose={handleClose} open={open} /> */}
            </div>
        </>
    )
}

export default MorePagesButton
