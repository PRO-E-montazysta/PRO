import { makeServiceCall } from "./utils.api";




export const getAllLocations = () => {
    return makeServiceCall('/locations/all', 'GET', {});
}