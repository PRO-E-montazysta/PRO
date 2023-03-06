import { makeServiceCall } from "./utils.api";




export const getAllManagers = () => {
    return makeServiceCall('/managers/all', 'GET', {});
}