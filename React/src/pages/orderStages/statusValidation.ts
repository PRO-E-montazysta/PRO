import { Order } from '../../types/model/Order'
import { OrderStage } from '../../types/model/OrderStage'
import { Role } from '../../types/roleEnum'
import { isAuthorized } from '../../utils/authorize'

type ValidateNextStatusResult = {
    isValid: boolean
    message: string
}

export const validateNextOrderStageStatus = (orderStageData?: OrderStage): ValidateNextStatusResult => {
    if (!orderStageData)
        return {
            isValid: false,
            message: 'Brak danych',
        }
    switch (orderStageData.status) {
        case 'ADDING_FITTERS':
            if (orderStageData.fitters.length == 0)
                return {
                    isValid: false,
                    message: 'Przypisz montażystów do etapu',
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

export const canChangeToNextStatus = (stageData?: OrderStage, orderData?: Order) => {
    if (!stageData || !orderData) return false
    const orderStageStatus = stageData.status
    return (
        (orderStageStatus == 'PLANNING' &&
            isAuthorized([Role.SPECIALIST]) &&
            orderData.status != 'PLANNING' &&
            orderData.status != 'TO_ACCEPT') ||
        (orderStageStatus == 'ADDING_FITTERS' && isAuthorized([Role.FOREMAN])) ||
        (orderStageStatus == 'PICK_UP' && isAuthorized([Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER])) ||
        (orderStageStatus == 'REALESED' && isAuthorized([Role.FOREMAN])) ||
        (orderStageStatus == 'ON_WORK' && isAuthorized([Role.FOREMAN])) ||
        (orderStageStatus == 'RETURN' && isAuthorized([Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER])) ||
        (orderStageStatus == 'RETURNED' && isAuthorized([Role.FOREMAN]))
    )
}

export const canChangeToPreviousStatus = (stageData?: OrderStage) => {
    if (!stageData) return false
    const orderStageStatus = stageData.status
    return (
        //(orderStageStatus == 'ADDING_FITTERS' && isAuthorized([Role.FOREMAN])) ||
        (orderStageStatus == 'PICK_UP' && isAuthorized([Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER])) ||
        (orderStageStatus == 'REALESED' && isAuthorized([Role.FOREMAN])) ||
        (orderStageStatus == 'ON_WORK' && isAuthorized([Role.FOREMAN])) ||
        (orderStageStatus == 'RETURN' && isAuthorized([Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER])) ||
        (orderStageStatus == 'RETURNED' && isAuthorized([Role.FOREMAN])) ||
        (orderStageStatus == 'FINISHED ' && isAuthorized([Role.FOREMAN]))
    )
}
