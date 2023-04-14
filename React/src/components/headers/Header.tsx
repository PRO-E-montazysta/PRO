import React, { useState } from 'react'
import { AppBar, Avatar, Box, IconButton, Toolbar } from '@mui/material'
import { styled } from '@mui/material/styles'
import logo from '../../assets/img/logo.png'
import { useNavigate } from 'react-router-dom'
import { logout } from '../../utils/token'
import LogoutIcon from '@mui/icons-material/Logout'

import { theme } from '../../themes/baseTheme'
import NotiButton from '../navbar/NotiButton'
import NavBox from '../navbar/NavBox'

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

const Header = () => {
    const navigate = useNavigate()
    const handleLogout = () => {
        logout()
    }

    const [userInfo, setUserInfo] = useState<UserInfo>({
        name: 'Imię Nazwisko',
        company: 'Firma',
        photoSrc: '',
        notifications: [],
    })

    return (
        <AppBar position="static" sx={{ backgroundColor: '#1A1C26' }}>
            <Toolbar sx={{ minHeight: '60px' }}>
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

export default Header
