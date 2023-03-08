import { makeServiceCall } from "./utils.api";




export const getAllClients = () => {
    return makeServiceCall('/clients/all', 'GET', {});
}