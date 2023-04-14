import React, { useState } from 'react'
import {
    AppBar,
    Avatar,
    Badge,
    Box,
    Button,
    Drawer,
    IconButton,
    ImageList,
    ImageListItem,
    List,
    Toolbar,
} from '@mui/material'
import { styled } from '@mui/material/styles'
import firstPicture from '../../assets/img/firstPicture.png'
import secondPicture from '../../assets/img/secondPicture.png'
import logo from '../../assets/img/logo.png'
import { NavLink, useNavigate } from 'react-router-dom'
import { pageList } from '../../utils/pageList'
import NavMenuButton from '../navbar/NavButton'
import { logout, removeToken } from '../../utils/token'
import { v4 as uuidv4 } from 'uuid'

import LogoutIcon from '@mui/icons-material/Logout'

import { theme } from '../../themes/baseTheme'
import NotiButton from '../navbar/NotiButton'
import MenuIcon from '@mui/icons-material/Menu'
import NavBox from '../navbar/NavBox'

const CustomizedToolbar = styled(Toolbar)(({ theme }) => ({
    '@media (min-width: 600px)': {
        minHeight: '60px',
    },
}))

export type UserInfo = {
    name: string
    company: string
    photoSrc: string
    notifications: Array<Notification>
}

export type Notification = {
    url: string
    dateTime: Date
    text: string
}

const HeaderHamburger = () => {
    const navigate = useNavigate()
    const handleLogout = () => {
        logout()
    }
    const [navbarOpen, setNavbarOpen] = useState(false)

    const [userInfo, setUserInfo] = useState<UserInfo>({
        name: 'Imię Nazwisko',
        company: 'Firma',
        photoSrc: '',
        notifications: [],
    })


    return (
        <AppBar position="static" sx={{ backgroundColor: '#1A1C26' }}>
            <Toolbar sx={{ minHeight: '60px' }}>
                <IconButton
                    size="large"
                    edge="start"
                    color="inherit"
                    aria-label="menu"
                    sx={{ mr: 2 }}
                    onClick={() => setNavbarOpen(true)}
                >
                    <MenuIcon />
                </IconButton>
                <Box
                    component="img"
                    alt="Logo"
                    title="Strona główna"
                    src={logo}
                    sx={{ cursor: 'pointer', width: 40, height: 40 }}
                    onClick={() => navigate('/')}
                ></Box>
                <NavBox />
                <Box sx={{ mr: '10px', textAlign: 'right', pl: '10px' }}>
                    <Box sx={{ color: theme.palette.primary.light, fontSize: '12px', whiteSpace: 'nowrap' }}>
                        {userInfo.name}
                    </Box>
                    <Box>{userInfo.company}</Box>
                </Box>
                <Avatar
                    sx={{ width: 40, height: 40, bgcolor: theme.palette.primary.light }}
                    alt={userInfo.name}
                    src={userInfo.photoSrc}
                />
                <NotiButton userInfo={userInfo} />
                <IconButton
                    color="inherit"
                    onClick={handleLogout}
                    title="Wyloguj"
                    sx={{ ml: '10px', width: 40, height: 40, border: '1px solid white' }}
                >
                    <LogoutIcon />
                </IconButton>
            </Toolbar>
        </AppBar>
    )
}

export default HeaderHamburger
