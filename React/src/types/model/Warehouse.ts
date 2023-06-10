export type Warehouse = {
    id: number
    name: string
    description: string
    openingHours: string
    companyId: number
    locationId: number
    elementInWarehouses: Array<number>
    tools: Array<number>
    deleted: boolean
}

export type WarehouseFilterDto = {
    id: number
    name: string
    description: string
    openingHours: string
    fullAddress: string
}
