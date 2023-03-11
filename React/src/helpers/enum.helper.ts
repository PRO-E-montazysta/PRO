import { SelectMenuItemProps } from '../components/base/Multiselect'
import { CompanyStatus } from '../types/model/Company'
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
