import { Box, IconButton, MenuItem } from '@mui/material'
import { useEffect, useState } from 'react'
import { isAuthorizedToPage } from '../../utils/authorize'
import { PageProps } from '../../utils/pageList'

import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown'
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp'
import { useNavigate } from 'react-router-dom'

type HamburgerButtonProps = {
    page: PageProps
    close: () => void
}

const HamburgerButton = (props: HamburgerButtonProps) => {
    const { page, close } = props
    const [availableChilds, setAvailableChilds] = useState<Array<PageProps>>([])
    const [menuOpen, setMenuOpen] = useState(false)
    const navigate = useNavigate()

    useEffect(() => {
        if (page.children) setAvailableChilds(page.children.filter((c) => c.inNav && isAuthorizedToPage(c)))
    }, [page.children])

    const onItemClick = () => {
        if (availableChilds.length == 0) {
            navigate(page.path)
            close()
        } else {
            setMenuOpen(!menuOpen)
        }
    }

    const onSubitemClick = (page: PageProps) => {
        navigate(page.path)
        setMenuOpen(false)
        close()
    }

    return (
        <Box>
            <MenuItem
                id={`hamburgerBtn-${page.path}`}
                sx={{ p: '0 20px', position: 'relative', height: '50px' }}
                onClick={onItemClick}
            >
                {page.name}

                {availableChilds && availableChilds.length > 0 && (
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        title="menu"
                        sx={{ ml: 'auto', p: 0 }}
                    >
                        {menuOpen ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                )}
            </MenuItem>
            {menuOpen &&
                availableChilds &&
                availableChilds.map((c, i) => {
                    return (
                        <MenuItem
                            key={i}
                            id={`hamburgerBtn-${page.path}-opt-${c.path}`}
                            sx={{ p: '0 20px', position: 'relative', height: '50px', ml: '20px' }}
                            onClick={() => onSubitemClick(c)}
                        >
                            {c.name}
                        </MenuItem>
                    )
                })}
        </Box>
    )
}

export default HamburgerButton
