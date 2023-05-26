export type ToolType = {
    id: number,
    name: string,
    inServiceCount: number,
    criticalNumber: number,
    availableCount: number,
    attachments: Array<number>,
    orderStages: Array<number>,
    tools: Array<number>
}

export type PlannedToolType = {
    id: number,
    numberOfTools: number ,
    toolType: {
      id: number,
      name: string,
      inServiceCount: number,
      criticalNumber: number,
      availableCount: number,
      attachments: [
        number
      ],
      tools: [
        number
      ],
      companyId: number,
      listOfToolsPlannedNumber: [
        number
      ]
    },
    orderStageId: number,
    demandAdHocId: number
  }