
import { SelectMenuItemProps } from '../components/form/types'
import { CompanyStatus } from '../types/model/Company'
import { TypeOfUnit } from '../types/model/Element'
import { EventStatus, EventType } from '../types/model/Event'
import { OrderPriority, OrderStatus } from '../types/model/Order'

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

export const eventStatusOptions = () => {
    return Object.entries(EventStatus).map((s): SelectMenuItemProps => {
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
