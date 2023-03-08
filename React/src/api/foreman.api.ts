import { makeServiceCall } from "./utils.api";




export const getAllForemans = () => {
    return makeServiceCall('/foremen/all', 'GET', {});
}