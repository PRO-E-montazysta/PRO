export enum TypeOfAttachment {
    OTHER = 'INNY',
    MANUAL = 'Instrukcja',
    FAULT_PHOTO = 'Zdjęcie usterki',
    DESIGN = 'Projekt',
    ACCEPTANCE_PROTOCOL = 'Protokół odbioru',
    PROFILE_PICTURE = 'Zdjęcie profilowe',
    ORDER_STAGE_PHOTO = 'Zdjęcie etapu',
}

export type Attachment = {
    commentId: number
    createdAt: string
    deleted: boolean
    description: string
    elementEventId: number
    elementId: number
    employeeId: number
    fileName: string
    id: number
    name: string
    orderId: number
    orderStageId: number
    toolEventId: number
    toolTypeId: number
    typeOfAttachment: string
    url: string
    uniqueName: string
}
