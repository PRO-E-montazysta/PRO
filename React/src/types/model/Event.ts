export type Event = {
    id: number
    eventDate: Date
    movingDate: Date
    completionDate: Date
    description: string
    status: string
    createdById: number
    acceptedById: number
    itemName: string
    eventType: string
    deleted: boolean
}

export const EventType = {
    T: 'Narzędzia',
    E: 'Elementy',
}

export const EventStatus = {
    CREATED: 'Utworzony',
    IN_PROGRESS: 'W realizacji',
    REPAIRED: 'Naprawiony',
    ELIMINATED: 'Zlikwidowano',
    MISSING: 'Zagubiony',
    OTHER: 'Inny',
}
