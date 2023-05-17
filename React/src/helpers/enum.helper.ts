import { SelectMenuItemProps } from '../components/form/types'
import { CompanyStatus } from '../types/model/Company'
import { TypeOfUnit } from '../types/model/Element'
import { EventStatus, EventType } from '../types/model/Event'
import { EmployeeStatus, UserRole } from '../types/model/Employee'
import { OrderPriority, OrderStatus } from '../types/model/Order'
import { TypeOfUnavailability } from '../types/model/Unavailability'
import { isAuthorized } from '../utils/authorize'
import { Role } from '../types/roleEnum'
import {
    NotificationType_FITTER,
    NotificationType_FOREMAN,
    NotificationType_MANAGER,
    NotificationType_OTHERS,
    NotificationType_SPECIALIST,
    NotificationType_WAREHOUSEMANAGER,
} from '../types/model/Notification'

export const statusName = (key: string) => {
    return Object.values(OrderStatus)[Object.keys(OrderStatus).indexOf(key)]
}

export const priorityName = (key: string) => {
    return Object.values(OrderPriority)[Object.keys(OrderPriority).indexOf(key)]
}

export const companyStatusName = (key: string) => {
    return Object.values(CompanyStatus)[Object.keys(CompanyStatus).indexOf(key)]
}

export const eventTypeName = (key: string) => {
    return Object.values(EventType)[Object.keys(EventType).indexOf(key)]
}

export const eventStatusName = (key: string) => {
    return Object.values(EventStatus)[Object.keys(EventStatus).indexOf(key)]
}

export const typeOfUnitName = (key: string) => {
    return Object.values(TypeOfUnit)[Object.keys(TypeOfUnit).indexOf(key)]
}

export const typeOfUnavailabilityName = (key: string) => {
    return Object.values(TypeOfUnavailability)[Object.keys(TypeOfUnavailability).indexOf(key)]
}

export const roleName = (key: string) => {
    return Object.values(UserRole)[Object.keys(UserRole).indexOf(key)]
}

export const priorityOptions = () => {
    return Object.entries(OrderPriority).map((s): SelectMenuItemProps => {
        return {
            key: s[0],
            value: s[1],
        }
    })
}

export const statusOptions = () => {
    return Object.entries(OrderStatus).map((s): SelectMenuItemProps => {
        return {
            key: s[0],
            value: s[1],
        }
    })
}

export const companyStatusOptions = () => {
    return Object.entries(CompanyStatus).map((s): SelectMenuItemProps => {
        return {
            key: s[0],
            value: s[1],
        }
    })
}

export const eventTypeOptions = () => {
    return Object.entries(EventType).map((s): SelectMenuItemProps => {
        return {
            key: s[0],
            value: s[1],
        }
    })
}

export const employeeStatusOptions = () => {
    return Object.entries(EmployeeStatus).map((s): SelectMenuItemProps => {
        return {
            key: s[0],
            value: s[1],
        }
    })
}

export const eventStatusOptions = () => {
    return Object.entries(EventStatus).map((s): SelectMenuItemProps => {
        return {
            key: s[0],
            value: s[1],
        }
    })
}

export const userRoleOptions = () => {
    return Object.entries(UserRole).map((s): SelectMenuItemProps => {
        return {
            key: s[0],
            value: s[1],
        }
    })
}

export const typeOfUnitOptions = () => {
    return Object.entries(TypeOfUnit).map((s): SelectMenuItemProps => {
        return {
            key: s[0],
            value: s[1],
        }
    })
}

export const typeOfUnavailabilityOptions = () => {
    return Object.entries(TypeOfUnavailability).map((s): SelectMenuItemProps => {
        return {
            key: s[0],
            value: s[1],
        }
    })
}

export const notificationTypeOptions = () => {
    if (isAuthorized([Role.SPECIALIST])) {
        return Object.entries(NotificationType_SPECIALIST).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1],
            }
        })
    } else if (isAuthorized([Role.MANAGER])) {
        return Object.entries(NotificationType_MANAGER).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1],
            }
        })
    } else if (isAuthorized([Role.WAREHOUSE_MANAGER])) {
        return Object.entries(NotificationType_WAREHOUSEMANAGER).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1],
            }
        })
    } else if (isAuthorized([Role.FOREMAN])) {
        return Object.entries(NotificationType_FOREMAN).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1],
            }
        })
    } else if (isAuthorized([Role.FITTER])) {
        return Object.entries(NotificationType_FITTER).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1],
            }
        })
    } else {
        return Object.entries(NotificationType_OTHERS).map((s): SelectMenuItemProps => {
            return {
                key: s[0],
                value: s[1],
            }
        })
    }
}
