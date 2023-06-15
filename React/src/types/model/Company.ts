export type Company = {
    id: number
    companyName: string
    createdAt: Date
    status: string
    statusReason: string
    warehouses?: Array<number>
    orders?: Array<number>
    clients?: Array<number>
    employments?: Array<number>
    deleted: boolean
}

export const CompanyStatus = {
    ACTIVE: 'Aktywna',
    DISABLED: 'Nieaktywna',
    SUSPENDED: 'Zawieszona',
}
