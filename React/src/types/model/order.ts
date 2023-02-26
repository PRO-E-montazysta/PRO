
export type Order = {
    id: number,
    name: string,
    typeOfStatus: string,
    plannedStart: Date,
    plannedEnd: Date,
    createdAt: Date,
    editedAt: Date,
    typeOfPriority: string
    orderStages: Array<number>
}


export const OrderStatus = {
    PLANNED: "PLANOWANIE",
    IN_PROGRESS: "W TRAKCIE",
    FINISHED: "ZAKOŃCZONE"
}

export const OrderPriority = {
    NORMAL: "NORMALNY",
    IMPORTANT: "WAŻNY",
    IMMEDIATE: "NATYCHMIASTOWY"
}