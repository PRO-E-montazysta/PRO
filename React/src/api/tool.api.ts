import { makeServiceCall, PayloadProps } from "./utils.api";

export const getFilteredTools = (payload: PayloadProps) => {
    return makeServiceCall('/tools/all', 'GET', { ...payload });
}

export const getToolDetails = (id?: string) => {
    return makeServiceCall(`/tools/${id}`, 'GET', {});
}

export const getToolsFromWarehouse = (warehouseId?: string) => {
    return makeServiceCall(`/tools/all/?warehouseId=${warehouseId}`, 'GET', {});
}