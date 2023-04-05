import React from 'react'
import { AppBar, Box, Button, IconButton, ImageList, ImageListItem, Toolbar } from '@mui/material'
import { styled } from '@mui/material/styles'
import firstPicture from '../../assets/img/firstPicture.png'
import secondPicture from '../../assets/img/secondPicture.png'
import logo from '../../assets/img/logo.png'
import { useNavigate } from 'react-router-dom'
import { pageList } from '../../utils/pageList'
import NavMenuButton from '../navMenuItem/NavButton'
import { removeToken } from '../../utils/token'
import { v4 as uuidv4 } from 'uuid'

import LogoutIcon from '@mui/icons-material/Logout'

const CustomizedToolbar = styled(Toolbar)(({ theme }) => ({
    '@media (min-width: 600px)': {
        minHeight: '60px',
    },
}))

const Header = () => {
    const navigate = useNavigate()
    const handleLogout = () => {
        removeToken()
        navigate('/login')
    }

    const rootPage = pageList.find((p) => p.path === '/')
    const rootPageChildrens = rootPage?.children

    return (
        <AppBar position="static">
            <Box sx={{ backgroundColor: '#1A1C26' }}>
                <CustomizedToolbar>
                    <Box component="img" alt="Your logo." src={logo} sx={{cursor: 'pointer'}} onClick={() => navigate('/')}></Box>
                    {rootPageChildrens
                        ? rootPageChildrens.map((page, index) => {
                              return <NavMenuButton {...page} key={index} />
                          })
                        : null}
                    <IconButton sx={{ marginLeft: 'auto' }} color="inherit" onClick={handleLogout}>
                        <LogoutIcon />
                    </IconButton>
                </CustomizedToolbar>
            </Box>
        </AppBar>
    )
}

export default Header
