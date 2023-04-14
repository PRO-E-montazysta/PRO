import { Box, Button, IconButton } from '@mui/material'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import NotificationsIcon from '@mui/icons-material/Notifications'
import { Badge } from '@mui/material'
import NotiMenu from './NotiMenu'
import ClickAwayListener from '@mui/base/ClickAwayListener'
import { UserInfo } from './NavActions'

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

    return (
        <>
            <ClickAwayListener onClickAway={handleClose}>
                <Box>
                    <IconButton
                        color="inherit"
                        onClick={() => handleClick()}
                        title="Powiadomienia"
                        sx={{ ml: '10px', width: 40, height: 40, border: '2px solid white' }}
                    >
                        <Badge badgeContent={userInfo.notifications.length} color="error" sx={{ p: '3px' }}>
                            <NotificationsIcon />
                        </Badge>
                    </IconButton>

                    <NotiMenu notifications={userInfo.notifications} onClose={handleClose} open={open} />
                </Box>
            </ClickAwayListener>
        </>
    )
}

export default NotiButton
