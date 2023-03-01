import { makeServiceCall } from "./utils.api";




export const getAllUsers = () => {
    return makeServiceCall('/users/all', 'GET', {});
}