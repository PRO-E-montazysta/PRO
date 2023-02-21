
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
    PLANNED = "PLANNED",
    IN_PROGRESS = "IN_PROGRESS",
    FINISHED = "FINISHED"
}

export enum OrderPriority {
    NORMAL = "NORMAL",
    IMPORTANT = "IMPORTANT",
    IMMEDIATE = "IMMEDIATE"
}