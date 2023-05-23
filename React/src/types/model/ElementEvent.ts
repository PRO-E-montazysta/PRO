export type ElementEvent = {
    id: number
    eventDate: Date
    movingDate: Date
    completionDate: Date
    description: string
    status: string
    quantity: number
    createdById: number
    acceptedById: number
    elementId: number
    attachments: Array<number>
    deleted: boolean
}
