import { makeServiceCall, PayloadProps } from "./utils.api";

export const getFilteredToolTypes = (payload: PayloadProps) => {
    return makeServiceCall('/tooltype/filter', 'GET', { ...payload });
}

export const getToolTypeDetails = (id?: string) => {
    return makeServiceCall(`/tooltype/${id}`, 'GET', {});
}