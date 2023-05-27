export type ElementInWarehouse = {
    id: number
    inWarehouseCount: number
    inUnitCount: number
    rack: string
    shelf: string
    elementId: number
    warehouseId: number
}

export type ElementInWarehouseFilterDto = {
    id: number
    inWarehouseCount: number
    inUnitCount: number
    rack: string
    shelf: string
    element: string
    warehouse: string
}
