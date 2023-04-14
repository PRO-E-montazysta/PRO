import React, { useState } from 'react'
import { Box, Drawer, IconButton, List } from '@mui/material'
import { pageList } from '../../utils/pageList'
import NavMenuButton from '../navbar/NavButton'

import { theme } from '../../themes/baseTheme'
import MenuIcon from '@mui/icons-material/Menu'
import { isAuthorized } from '../../utils/authorize'
import HamburgerButton from './HamburgerButton'
import CloseIcon from '@mui/icons-material/Close'
const Hamburger = () => {
    const [navbarOpen, setNavbarOpen] = useState(true)

    const rootPage = pageList.find((p) => p.path === '/')
    const availablePages = rootPage?.children?.filter((c) => c.inNav && isAuthorized(c))

    return (
        <Box>
            <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="menu"
                title="menu"
                sx={{ mr: 2 }}
                onClick={() => setNavbarOpen(true)}
            >
                <MenuIcon />
            </IconButton>
            <Drawer anchor={'left'} open={navbarOpen} onClose={() => setNavbarOpen(false)}>
                <Box
                    sx={{ backgroundColor: theme.palette.primary.dark, color: 'white' }}
                    width={'250px'}
                    height={'100vh'}
                    // role="presentation"
                    // onClick={() => setNavbarOpen(false)}
                    // onKeyDown={() => setNavbarOpen(false)}
                >
                    <List>
                        <IconButton
                            aria-label="close"
                            title="close menu"
                            size="large"
                            edge="start"
                            color="inherit"
                            sx={{ mr: 2, ml: 'auto' }}
                            onClick={() => setNavbarOpen(false)}
                        >
                            <CloseIcon />
                        </IconButton>

                        {availablePages
                            ? availablePages.map((page, index) => {
                                  return (
                                      <HamburgerButton page={page} close={() => setNavbarOpen(false)} key={index} />
                                  )
                              })
                            : null}
                    </List>
                </Box>
            </Drawer>
        </Box>
    )
}

export default Hamburger
