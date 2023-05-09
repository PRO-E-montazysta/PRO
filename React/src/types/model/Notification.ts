export type Notification = {
    id: number
    notificationType: string
    content: string
    createdAt: string
    readAt: number
    createdById: number

    orderStageId: number
    orderId: number
}
