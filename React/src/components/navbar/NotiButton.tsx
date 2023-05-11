import { Box, IconButton } from '@mui/material'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import NotificationsIcon from '@mui/icons-material/Notifications'
import { Badge } from '@mui/material'
import NotiMenu from './NotiMenu'
import ClickAwayListener from '@mui/base/ClickAwayListener'
import { UserInfo } from './NavActions'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getNotifications } from '../../api/notification.api'

type NotiButtonProps = {
    userInfo: UserInfo
}

const NotiButton = (props: NotiButtonProps) => {
    const { userInfo } = props

    const navigate = useNavigate()
    const [open, setOpen] = useState(false)

    const handleClick = () => {
        if (!open) setOpen(true)
        else setOpen(false)
    }

    const handleClose = () => {
        setOpen(false)
    }

    const notificationQuery = useQuery<Array<Notification>, AxiosError>(['notifications'], async () =>
        getNotifications(),
    )

    const notificationCount = notificationQuery.data ? Number(notificationQuery.data.length) : 0

    return (
        <>
            <ClickAwayListener onClickAway={handleClose}>
                <Box>
                    <IconButton
                        id={'navBtn-notifications'}
                        color="inherit"
                        onClick={() => handleClick()}
                        title="Powiadomienia"
                        sx={{ ml: '10px', width: 40, height: 40, border: '2px solid white' }}
                    >
                        <Badge badgeContent={notificationCount} color="error" sx={{ p: '3px' }}>
                            <NotificationsIcon />
                        </Badge>
                    </IconButton>

                    <NotiMenu onClose={handleClose} open={open} />
                </Box>
            </ClickAwayListener>
        </>
    )
}

export default NotiButton
