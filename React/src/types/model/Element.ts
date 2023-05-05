export type Element = {
    id: number,
    name: string,
    code: string,
    typeOfUnit: string,
    quantityInUnit: number
}

export const TypeOfUnit = {
    KILOGRAM: "Kilogram",
    LITER: "Litr",
    PIECE: "Sztuka"
}

export type PlannedElements = {
    id: 0,
    numberOfElements: 1,
    element: {
      id: 0,
      name: string, 
      code: string,
      typeOfUnit: "KILOGRAM",
      quantityInUnit: 0,
      elementReturnReleases: [
        0
      ],
      elementInWarehouses: [
        0
      ],
      elementEvents: [
        0
      ],
      attachmentId: 0,
      listOfElementsPlannedNumber: [
        0
      ]
    },
    orderStageId: 0,
    demandAdHocId: 0
  }