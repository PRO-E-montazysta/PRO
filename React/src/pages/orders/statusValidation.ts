import { Order } from '../../types/model/Order'
import { OrderStage } from '../../types/model/OrderStage'
import { Role } from '../../types/roleEnum'
import { isAuthorized } from '../../utils/authorize'

type ValidateNextStatusResult = {
    isValid: boolean
    message: string
}

export const validateNextOrderStatus = (
    orderData?: Order,
    orderStagesData?: OrderStage[],
): ValidateNextStatusResult => {
    if (!orderData || !orderStagesData)
        return {
            isValid: false,
            message: 'Brak danych',
        }
    switch (orderData.status) {
        case 'PLANNING':
            if (orderData.orderStages.length == 0)
                return {
                    isValid: false,
                    message: 'Dodaj etapy do zlecenia',
                }
            break
        case 'TO_ACCEPT':
            if (!orderData.foremanId)
                return {
                    isValid: false,
                    message: 'Dodaj brygadzistę do zlecenia',
                }
            break
        case 'IN_PROGRESS':
            const stagesInProgress = orderStagesData.filter((s) => s.status != 'FINISHED')
            if (stagesInProgress.length > 0)
                return {
                    isValid: false,
                    message: 'Zlecenie ma jeszcze trwające etapy',
                }
            break
        default:
            return {
                isValid: true,
                message: '',
            }
    }
    return {
        isValid: true,
        message: '',
    }
}

export const canChangeToNextStatus = (orderData?: Order) => {
    if (!orderData) return false
    return (
        (orderData.status == 'CREATED' && isAuthorized([Role.SALES_REPRESENTATIVE])) ||
        (orderData.status == 'PLANNING' && isAuthorized([Role.SPECIALIST])) ||
        (orderData.status == 'TO_ACCEPT' && isAuthorized([Role.MANAGER])) ||
        (orderData.status == 'ACCEPTED' && isAuthorized([Role.FOREMAN])) ||
        (orderData.status == 'IN_PROGRESS' && isAuthorized([Role.FOREMAN]))
    )
}

export const canChangeToPreviousStatus = (orderData?: Order) => {
    if (!orderData) return false
    return (
        (orderData.status == 'PLANNING' && isAuthorized([Role.SPECIALIST])) ||
        (orderData.status == 'TO_ACCEPT' && isAuthorized([Role.MANAGER])) ||
        (orderData.status == 'ACCEPTED' && isAuthorized([Role.FOREMAN])) ||
        (orderData.status == 'IN_PROGRESS' && isAuthorized([Role.FOREMAN])) ||
        (orderData.status == 'FINISHED' && isAuthorized([Role.FOREMAN]))
    )
}
