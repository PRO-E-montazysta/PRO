import { Box } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import { theme } from '../../themes/baseTheme'
import NotiMenuItem from './NotiMenuItem'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getNotifications, updateNotification } from '../../api/notification.api'
import { Notification } from '../../types/model/Notification'

type NotiMenuParams = {
    open: boolean
    onClose: () => void
}

const NotiMenu = (params: NotiMenuParams) => {
    const notificationQuery = useQuery<Array<Notification>, AxiosError>(['notification'], async () =>
        getNotifications(),
    )
    const notificationCount = Number(notificationQuery.data?.length)
    const { open, onClose } = params

    const navigate = useNavigate()

    const handleMenuItemClick = (url: string, id: number) => {
        updateNotification(id)
        navigate(url)
        onClose()
    }

    return (
        <>
            <Box
                sx={{
                    transitionDuration: '.5s',
                    height: open ? (notificationCount > 0 ? notificationCount * 60 + 'px' : '40px') : 0,
                    position: 'absolute',
                    backgroundColor: theme.palette.primary.main,
                    borderRadius: '5px',
                    boxShadow: '0px 0px 2px 2px ' + theme.palette.primary.dark,
                    overflow: 'hidden',
                    mt: '10px',
                    right: '10px',
                }}
            >
                {notificationQuery.data &&
                    notificationQuery.data.map((row) => {
                        let url = '/'

                        switch (row.notificationType) {
                            case 'ORDER_CREATED':
                            case 'ACCEPT_ORDER':
                            case 'FOREMAN_ASSIGNMENT':
                                url += 'orders/' + row.orderId
                                break
                        }

                        return (
                            <NotiMenuItem
                                id={`navBtn-notification-${row.id}`}
                                onItemClick={() => handleMenuItemClick(url, row.id)}
                                text={row.content}
                                subtext={row.subContent}
                                key={row.id}
                            />
                        )
                    })}
                {notificationCount == 0 ? (
                    <>
                        <NotiMenuItem
                            id="navBtn-notifications-noNotifications"
                            onItemClick={() => {}}
                            text="Brak powiadomień"
                        />
                    </>
                ) : null}
            </Box>
        </>
    )
}

export default NotiMenu
