
export type Order = {
    id: number,
    name: string,
    typeOfStatus: OrderStatus,
    plannedStart: Date,
    plannedEnd: Date,
    createdAt: Date,
    editedAt: Date,
    typeOfPriority: OrderPriority
}


export enum OrderStatus {
    PLANNED = "PLANOWANIE",
    IN_PROGRESS = "W TRAKCIE",
    FINISHED = "ZAKOŃCZONE"
}

export enum OrderPriority {
    NORMAL = "NORMALNY",
    IMPORTANT = "WAŻNY",
    IMMEDIATE = "NATYCHMIASTOWY"
}