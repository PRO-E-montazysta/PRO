export type Order = {
    id: number
    name: string
    typeOfStatus: string
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
}

export const OrderStatus = {
    CREATED: 'Utworzony',
    PLANNING: 'Planowanie',
    TO_ACCEPT: 'Do zaakceptowania',
    ACCEPTED: 'Zaakceptowane',
    IN_PROGRESS: 'W trakcie',
    FINISHED: 'Zakończono',
}

export const OrderPriority = {
    NORMAL: 'NORMALNY',
    IMPORTANT: 'WAŻNY',
    IMMEDIATE: 'NATYCHMIASTOWY',
}
