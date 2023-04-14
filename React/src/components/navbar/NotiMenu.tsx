import { Box, Menu, MenuItem } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import { PageProps } from '../../utils/pageList'

import { theme } from '../../themes/baseTheme'
import NotiMenuItem from './NotiMenuItem'

type NotiMenuParams = {
    open: boolean
    notifications: Array<any>
    onClose: () => void
}

const NotiMenu = (params: NotiMenuParams) => {
    const { open, notifications, onClose } = params

    const navigate = useNavigate()

    const handleMenuItemClick = (noti: any) => {
        navigate(noti.url)
        onClose()
    }

    const handleLoadMoreNotifications = () => {
        console.log('handleLoadMoreNotifications not implemented yet')
    }
    return (
        <>
            <Box
                sx={{
                    transitionDuration: '.5s',
                    height: open ? (notifications.length + 1) * 60 + 'px' : 0,
                    position: 'absolute',
                    backgroundColor: theme.palette.primary.main,
                    borderRadius: '5px',
                    boxShadow: '0px 0px 2px 2px ' + theme.palette.primary.dark,
                    overflow: 'hidden',
                    mt: '10px',
                    right: '10px',
                }}
            >
                {notifications.map((noti, index) => {
                    return <NotiMenuItem onItemClick={() => handleMenuItemClick(noti)} text={noti.text} key={index} />
                })}
                <NotiMenuItem
                    onItemClick={handleLoadMoreNotifications}
                    text={notifications.length > 0 ? '' : 'Brak nowych powiadomień'}
                    link={'Pokaż starsze powiadomienia'}
                />
            </Box>
        </>
    )
}

export default NotiMenu
