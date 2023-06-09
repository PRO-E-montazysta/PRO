export type OrderStageSimpleToolReleases = Array<{
    deleted: boolean
    demandAdHocId: number
    id: number
    orderStageId: number
    releaseTime: Date
    releasedById: string | null
    returnTime: Date
    toolId: string
}>

export type OrderStageSimpleElementRelease = Array<{
          id: number,
          releaseTime: Date,
          releasedQuantity: number,
          returnedQuantity: number,
          returnTime: null,
          releasedById: number,
          elementId: string,
          demandAdHocId: number,
          orderStageId: number,
          deleted: false
}>

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
    plannedFittersNumber: string
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
    simpleToolReleases: OrderStageSimpleToolReleases
    simpleElementReturnReleases: OrderStageSimpleElementRelease
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

export type UpdateOrderStage = {
    orderId: string
    orderStageId: string | null
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

export type UpdateOrderStage2 = {
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

export const OrderStageStatus = {
    PLANNING: 'PLANOWANIE',
    ADDING_FITTERS: 'DODAWANIE MONTAŻYSTÓW',
    PICK_UP: 'WYDAWANIE',
    REALESED: 'WYDANO',
    ON_WORK: 'W TRAKCIE',
    RETURN: 'ZWRACANIE',
    RETURNED: 'ZWRÓCONO',
    FINISHED: 'ZAKOŃCZONO',
}
