import { makeServiceCall, PayloadProps } from "./utils.api";

export const getFilteredTools = (payload: PayloadProps) => {
    return makeServiceCall('/tools/filter', 'GET', { ...payload });
}

export const getToolDetails = (id?: string) => {
    return makeServiceCall(`/tools/${id}`, 'GET', {});
}

export const getToolsFromWarehouse = (warehouseId: string, payload: PayloadProps) => {
    return makeServiceCall(`/tools/tools-from-warehouse/${warehouseId}`, 'GET', { ...payload });
}