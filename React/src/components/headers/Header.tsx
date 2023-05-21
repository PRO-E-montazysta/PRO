import React, { useState } from 'react'
import { AppBar, Avatar, Box, IconButton, Toolbar } from '@mui/material'
import logo from '../../assets/img/logo.png'
import { useNavigate } from 'react-router-dom'

import NavBox from '../navbar/NavBox'
import Hamburger from '../navbar/Hamburger'
import useBreakpoints from '../../hooks/useBreakpoints'
import NavActions from '../navbar/NavActions'

const Header = () => {
    const appSize = useBreakpoints()
    const navigate = useNavigate()

    return (
        <AppBar position="static" sx={{ backgroundColor: '#1A1C26' }}>
            <Toolbar
                sx={{
                    minHeight: '60px',
                    display: appSize.isMobile || appSize.isTablet ? 'grid' : 'flex',
                    gridTemplateColumns: '45% 10% 45%',
                    p: appSize.isMobile || appSize.isTablet ? '0 10px !important' : 'unset',
                }}
            >
                {appSize.isMobile || appSize.isTablet ? <Hamburger /> : null}
                <Box
                    id={`navBtn-goHHome`}
                    component="img"
                    alt="Logo"
                    title="Strona główna"
                    src={logo}
                    sx={{ cursor: 'pointer', width: 40, height: 40, justifySelf: 'center' }}
                    onClick={() => navigate('/')}
                ></Box>
                {appSize.isDesktop || appSize.isNotebook ? <NavBox /> : null}
                <NavActions />
            </Toolbar>
        </AppBar>
    )
}

export default Header
