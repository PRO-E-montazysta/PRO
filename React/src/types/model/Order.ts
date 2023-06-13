export type Order = {
    id: number
    name: string
    status: string
    plannedStart: Date
    plannedEnd: Date
    createdAt: Date
    editedAt: Date
    typeOfPriority: string
    companyId: number
    managerId: number
    foremanId: number
    specialistId: number
    salesRepresentativeId: number
    locationId: number
    clientId: number
    orderStages: Array<number>
    attachments: Array<number>
    deleted: boolean
}

export const OrderStatus = {
    CREATED: 'UTWORZONY',
    PLANNING: 'PLANOWANIE',
    TO_ACCEPT: 'DO AKCEPTACJI',
    ACCEPTED: 'ZAAKCEPTOWANE',
    IN_PROGRESS: 'W TRAKCIE',
    FINISHED: 'ZAKOŃCZONO',
}

export const OrderPriority = {
    NORMAL: 'NORMALNY',
    IMPORTANT: 'WAŻNY',
    IMMEDIATE: 'NATYCHMIASTOWY',
}
