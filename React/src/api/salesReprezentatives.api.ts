import { makeServiceCall } from "./utils.api";




export const getAllSalesReprezentatives = () => {
    return makeServiceCall('/sales-representatives/all', 'GET', {});
}