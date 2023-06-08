export type DemandAdHoc = {
    id: number
    description: string
    createdAt: Date
    createdBy: number
    orderStage: number
    listOfToolsPlannedNumber: Array<number>
    listOfElementsPlannedNumber: Array<number>
}

export type DemandAdHocFilterDto = {
    id: number
    description: string
    createdAt: Date
    createdByName: string
    orderStageName: string
}
