import React, { useState } from 'react'
import { AppBar, Avatar, Badge, Box, Button, IconButton, ImageList, ImageListItem, Toolbar } from '@mui/material'
import { styled } from '@mui/material/styles'
import firstPicture from '../../assets/img/firstPicture.png'
import secondPicture from '../../assets/img/secondPicture.png'
import logo from '../../assets/img/logo.png'
import { useNavigate } from 'react-router-dom'
import { pageList } from '../../utils/pageList'
import NavMenuButton from '../navbar/NavButton'
import { logout, removeToken } from '../../utils/token'
import { v4 as uuidv4 } from 'uuid'

import LogoutIcon from '@mui/icons-material/Logout'

import { theme } from '../../themes/baseTheme'
import NotiButton from '../navbar/NotiButton'

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

const Header = () => {
    const navigate = useNavigate()
    const handleLogout = () => {
        // removeToken()
        // navigate('/login')
        logout()
    }

    const [userInfo, setUserInfo] = useState<UserInfo>({
        name: 'Imię Nazwisko',
        company: 'Firma',
        photoSrc: '',
        notifications: [],
    })

    const rootPage = pageList.find((p) => p.path === '/')
    const rootPageChildrens = rootPage?.children

    return (
        <AppBar position="static">
            <Box sx={{ backgroundColor: '#1A1C26' }}>
                <CustomizedToolbar>
                    <Box
                        component="img"
                        alt="Logo"
                        title="Strona główna"
                        src={logo}
                        sx={{ cursor: 'pointer', width: 40, height: 40 }}
                        onClick={() => navigate('/')}
                    ></Box>
                    {rootPageChildrens
                        ? rootPageChildrens.map((page, index) => {
                              return <NavMenuButton {...page} key={index} />
                          })
                        : null}

                    <Box sx={{ ml: 'auto', mr: '10px', textAlign: 'right' }}>
                        <Box sx={{ color: theme.palette.primary.light, fontSize: '12px' }}>{userInfo.name}</Box>
                        <Box>{userInfo.company}</Box>
                    </Box>
                    <Avatar sx={{ width: 40, height: 40 }} alt={userInfo.name} src={userInfo.photoSrc} />
                    <NotiButton userInfo={userInfo} />
                    <IconButton
                        color="inherit"
                        onClick={handleLogout}
                        title="Wyloguj"
                        sx={{ ml: '10px', width: 40, height: 40, border: '1px solid white' }}
                    >
                        <LogoutIcon />
                    </IconButton>
                </CustomizedToolbar>
            </Box>
        </AppBar>
    )
}

export default Header
