export type OrderStage = {
    id?: number
    name: string
    status: string
    price: number
    order: 0
    plannedEndDate: Date
    startDate: Date
    endDate: Date
    plannedDurationTime: Date //jak chcemy to podawać?
    plannedFittersNumber: number
    minimumImagesNumber: 0
    fitters: Array<number>
    foremanId: number
    comments?: Array<string>
    toolReleases: Array<number>
    elementReturnReleases: Array<number>
    orderId: number
    attachments: Array<number>
    notifications: Array<number>
    tools: Array<number>
    elements: Array<number>
    demandAdHocs: Array<string>
}

export type CreateOrderStage = {
    orderId: number
    name: string
    status: string
    price: number
    order: number
    plannedDurationTime: number
    plannedFittersNumber: number
    minimumImagesNumber: number
    foremanId: number
    tools: Array<number>
    elements: Array<number>
    attachments: Array<number>
}
