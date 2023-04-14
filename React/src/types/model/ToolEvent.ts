export type ToolEvent = {
    id: number
    eventDate: Date
    movingDate: Date
    completionDate: Date
    description: string
    status: string
    createdById: number
    acceptedById: number
    toolId: number
    attachments: Array<number>
}
