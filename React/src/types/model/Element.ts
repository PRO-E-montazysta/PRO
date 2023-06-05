export type Element = {
    id: number
    name: string
    code: string
    typeOfUnit: string
    quantityInUnit: number
}

export const TypeOfUnit = {
    KILOGRAM: 'Kilogram',
    GRAM: 'Gram',
    LITER: 'Litr',
    MILILITER: 'Mililitr',
    PIECE: 'Sztuka',
    MKW: 'Metr kwadratowy',
    MSZ: 'Metr sześcienny',
}

export type PlannedElements = {
    id: number
    numberOfElements: number
    element: {
        id: number
        name: string
        code: string
        typeOfUnit: string
        quantityInUnit: number
        elementReturnReleases: [number]
        elementInWarehouses: [number]
        elementEvents: [number]
        attachmentId: number
        listOfElementsPlannedNumber: [number]
    }
    orderStageId: number
    demandAdHocId: number
}
