export type OrderStage = {
    id?: number
    name: string
    status: string
    price: string
    order: 0
    plannedStartDate: string
    plannedEndDate: string
    startDate: Date
    endDate: Date
    plannedDurationTime: Date //jak chcemy to podawać?
    plannedFittersNumber:  string
    minimumImagesNumber: string
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
    listOfToolsPlannedNumber: Array<{
        numberOfTools: number
        toolTypeId: string
    }>
    listOfElementsPlannedNumber: Array<{
        numberOfElements: number
        elementId: string
    }>
}

export type CreateOrderStage = {
    orderId: string
    name: string
    status: string | null
    price: string
    plannedStartDate: string
    plannedEndDate: string
    plannedFittersNumber: string
    minimumImagesNumber: string
    listOfToolsPlannedNumber: Array<{
        numberOfTools: number
        toolTypeId: string
    }>
    listOfElementsPlannedNumber: Array<{
        numberOfElements: number
        elementId: string
    }>
    attachments: Array<number>
}
