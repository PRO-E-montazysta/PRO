export type Tool = {
    id: number,
    name: string,
    createdAt: Date,
    code: string,
    toolReleases:Array<number>
    warehouseId: number,
    toolEvents:Array<number>
    toolTypeId: number
}