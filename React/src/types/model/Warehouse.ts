export type Warehouse = {
    id: number,
    name: string,
    description: string,
    openingHours: string,
    companyId: number,
    locationId: number,
    elementInWarehouses: Array<number>,
    tools: Array<number>
}

export type WarehouseFilterDto = {
    id: number,
    name: string,
    description: string,
    openingHours: string,
    fullAddress: string
}