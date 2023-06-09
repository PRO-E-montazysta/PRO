import React, { useEffect, useState } from 'react'
import { Avatar, Box, Drawer, IconButton, List } from '@mui/material'
import { logout } from '../../utils/token'
import LogoutIcon from '@mui/icons-material/Logout'
import { theme } from '../../themes/baseTheme'
import NotiButton from '../navbar/NotiButton'
import useBreakpoints from '../../hooks/useBreakpoints'
import CloseIcon from '@mui/icons-material/Close'
import NavMenuItem from './NavMenuItem'
import { useQuery } from 'react-query'
import { getAboutMeInfo } from '../../api/employee.api'
import { AxiosError } from 'axios'

export type UserInfo = {
    name: string
    company: string
    photoSrc: string
    userId: number
}

type NavActionsProps = {}
//TODO
//ERROR
//CHANGE THIS LATER

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
        userId: 0,
    })

    useEffect(() => {
        if (aboutMeQuery.data) {
            setUserInfo({
                name: aboutMeQuery.data.firstName + ' ' + aboutMeQuery.data.lastName,
                company: aboutMeQuery.data.companyName,
                photoSrc: aboutMeQuery.data.profilePhotoUrl != 'null' ? aboutMeQuery.data.profilePhotoUrl : '',
                userId: aboutMeQuery.data.userId,
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
        <Box sx={{ mr: '10px', textAlign: 'right', pl: '10px', whiteSpace: 'nowrap' }}>
            <Box sx={{ color: theme.palette.primary.light, fontSize: '12px' }}>{userInfo.name}</Box>
            <Box>{userInfo.company}</Box>
        </Box>
    )

    return (
        <Box sx={{ justifySelf: 'end', ml: 'auto' }}>
            {appSize.isDesktop || appSize.isNotebook ? (
                <Box display={'flex'}>
                    {userInfoComponent}
                    {avatar}
                    <NotiButton />
                    <IconButton
                        id={'navBtn-logout'}
                        color="inherit"
                        onClick={handleLogout}
                        title="Wyloguj"
                        sx={{ ml: '10px', width: 40, height: 40, border: '2px solid white' }}
                    >
                        <LogoutIcon />
                    </IconButton>
                </Box>
            ) : (
                <Box>
                    <IconButton
                        id={`navBtn-hamburger-showMore`}
                        color="inherit"
                        onClick={() => setDrawerOpen(true)}
                        title="Więcej"
                        sx={{ p: 0 }}
                    >
                        {avatar}
                    </IconButton>
                    <Drawer anchor={'right'} open={drawerOpen} onClose={() => setDrawerOpen(false)}>
                        <Box
                            sx={{ backgroundColor: theme.palette.primary.dark, color: 'white', p: '20px' }}
                            width={'250px'}
                            height={'100vh'}
                        >
                            <IconButton
                                id={`navBtn-hamburger-close`}
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
                                    id={`navBtn-hamburger-navItem`}
                                    sx={{ pl: '5px' }}
                                    text="Powiadomienia"
                                    onItemClick={() => {
                                        console.log('Not implemented yet')
                                    }}
                                />
                                <NavMenuItem
                                    id={`navBtn-hamburger-logout`}
                                    sx={{ pl: '5px' }}
                                    text="Wyloguj się"
                                    onItemClick={handleLogout}
                                />
                            </List>
                        </Box>
                    </Drawer>
                </Box>
            )}
        </Box>
    )
}

export default NavActions
