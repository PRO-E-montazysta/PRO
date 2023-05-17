import { Box } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import { theme } from '../../themes/baseTheme'
import NotiMenuItem from './NotiMenuItem'
import { AxiosError } from 'axios'
import { useQuery, useQueryClient } from 'react-query'
import { getNotifications, updateNotification } from '../../api/notification.api'
import { Notification } from '../../types/model/Notification'

type NotiMenuParams = {
    open: boolean
    onClose: () => void
}

const NotiMenu = (params: NotiMenuParams) => {
    const queryClient = useQueryClient()
    const notificationQuery = useQuery<Array<Notification>, AxiosError>(['notifications'], async () =>
        getNotifications(),
    )
    const notificationCount = Number(notificationQuery.data?.length)
    const { open, onClose } = params

    const navigate = useNavigate()

    const handleMenuItemClick = async (url: string, id: number) => {
        await updateNotification(id)
        navigate(url)
        onClose()
        queryClient.invalidateQueries('notifications')
    }

    return (
        <>
            <Box
                sx={{
                    transitionDuration: '.5s',
                    height: open ? (notificationCount > 0 ? notificationCount * 60 + 'px' : '60px') : 0,
                    position: 'absolute',
                    backgroundColor: theme.palette.primary.main,
                    borderRadius: '5px',
                    boxShadow: '0px 0px 2px 2px ' + theme.palette.primary.dark,
                    overflow: 'hidden',
                    mt: '10px',
                    right: '10px',
                    zIndex: 100,
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
                            case 'TOOL_EVENT':
                                url += 'toolevent/' + row.toolEventId
                                break
                            case 'ELEMENT_EVENT':
                                url += 'elementevent/' + row.elementEventId
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
                            onItemClick={() => navigate('/notifications')}
                            text="Brak powiadomień"
                            subtext="Przejdź do historii"
                        />
                    </>
                ) : null}
            </Box>
        </>
    )
}

export default NotiMenu
