import { PayloadProps, makeServiceCall } from './utils.api'

export const getAllMyNotifications = (payload: PayloadProps) => {
    return makeServiceCall('/notifications/my-all', 'GET', { ...payload })
}

export const getNotifications = () => {
    return makeServiceCall('/notifications', 'GET', {})
}

export const updateNotification = (id: number) => {
    return makeServiceCall(`/notifications/${id}`, 'PUT', {})
}
