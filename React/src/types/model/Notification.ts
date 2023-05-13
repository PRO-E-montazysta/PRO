export type Notification = {
    id: number
    notificationType: string
    content: string
    subContent: string
    createdAt: string
    readAt: number
    createdById: number
    deleted: boolean

    orderStageId: number
    orderId: number
    toolEventId: number
    elementEventId: number
}
