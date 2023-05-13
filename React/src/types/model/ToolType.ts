export type ToolType = {
    id: number
    name: string
    inServiceCount: number
    criticalNumber: number
    availableCount: number
    attachments: Array<number>
    orderStages: Array<number>
    tools: Array<number>
    deleted: boolean
}
