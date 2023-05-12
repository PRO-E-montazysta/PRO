import { makeServiceCall } from './utils.api'

export const getNotifications = () => {
    return makeServiceCall('/notifications', 'GET', {})
}

export const updateNotification = (id: number) => {
    return makeServiceCall(`/notifications/${id}`, 'PUT', {})
}
