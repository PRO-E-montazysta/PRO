import { makeServiceCall } from "./utils.api";

export const getAllCompanies = () => {
    return makeServiceCall('/companies/all', 'GET', {});
}