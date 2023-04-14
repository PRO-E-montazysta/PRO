import React, { useEffect, useState } from 'react'
import { AppBar, Avatar, Box, Drawer, IconButton, List, Toolbar } from '@mui/material'
import { logout } from '../../utils/token'
import LogoutIcon from '@mui/icons-material/Logout'

import { theme } from '../../themes/baseTheme'
import NotiButton from '../navbar/NotiButton'
import useBreakpoints from '../../hooks/useBreakpoints'

import CloseIcon from '@mui/icons-material/Close'
import NavMenuItem from './NavMenuItem'
import { useQuery } from 'react-query'
import { getAboutMeInfo } from '../../api/user.api'
import { AxiosError } from 'axios'

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

type NavActionsProps = {}

const NavActions = (props: NavActionsProps) => {
    const [drawerOpen, setDrawerOpen] = useState(false)
    const appSize = useBreakpoints()
    const handleLogout = () => {
        logout()
    }
    const aboutMeQuery = useQuery<any, AxiosError>(['about-me'], async () => getAboutMeInfo())

    const [userInfo, setUserInfo] = useState<UserInfo>({
        name: 'Imię Nazwisko',
        company: 'Firma',
        photoSrc: '',
        notifications: [],
    })

    useEffect(() => {
        if(aboutMeQuery.data){
            setUserInfo({
                name: aboutMeQuery.data.firstName + " " +  aboutMeQuery.data.lastName,
                company:  aboutMeQuery.data.companyName,
                notifications: [],
                photoSrc: aboutMeQuery.data.profilePhotoUrl
            })
        }
    }, [aboutMeQuery.status])

    const avatar = (
        <Avatar
            sx={{ width: 40, height: 40, bgcolor: theme.palette.primary.light, p: 0 }}
            alt={userInfo.name}
            src={userInfo.photoSrc}
        />
    )

    const userInfoComponent = (
        <Box sx={{ mr: '10px', textAlign: 'right', pl: '10px' }}>
            <Box sx={{ color: theme.palette.primary.light, fontSize: '12px', whiteSpace: 'nowrap' }}>
                {userInfo.name}
            </Box>
            <Box>{userInfo.company}</Box>
        </Box>
    )

    return (
        <Box sx={{ justifySelf: 'end' }}>
            {appSize.isDesktop || appSize.isNotebook ? (
                <Box display={'flex'}>
                    {userInfoComponent}
                    {avatar}
                    <NotiButton userInfo={userInfo} />
                    <IconButton
                        color="inherit"
                        onClick={handleLogout}
                        title="Wyloguj"
                        sx={{ ml: '10px', width: 40, height: 40, border: '1px solid white' }}
                    >
                        <LogoutIcon />
                    </IconButton>
                </Box>
            ) : (
                <Box>
                    <IconButton color="inherit" onClick={() => setDrawerOpen(true)} title="Więcej" sx={{ p: 0 }}>
                        {avatar}
                    </IconButton>
                    <Drawer anchor={'right'} open={drawerOpen} onClose={() => setDrawerOpen(false)}>
                        <Box
                            sx={{ backgroundColor: theme.palette.primary.dark, color: 'white', p: '20px' }}
                            width={'250px'}
                            height={'100vh'}
                        >
                            <IconButton
                                aria-label="close"
                                title="close menu"
                                size="large"
                                edge="start"
                                color="inherit"
                                sx={{ mr: 'auto', position: 'absolute', p: 0, m: 0 }}
                                onClick={() => setDrawerOpen(false)}
                            >
                                <CloseIcon />
                            </IconButton>
                            {userInfoComponent}
                            <List>
                                <NavMenuItem
                                    sx={{ pl: '5px' }}
                                    text="Powiadomienia"
                                    onItemClick={() => {
                                        console.log('Not implemented yet')
                                    }}
                                />
                                <NavMenuItem sx={{ pl: '5px' }} text="Wyloguj się" onItemClick={handleLogout} />
                            </List>
                        </Box>
                    </Drawer>
                </Box>
            )}
        </Box>
    )
}

export default NavActions
