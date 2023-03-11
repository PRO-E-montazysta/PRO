import { makeServiceCall } from "./utils.api";




export const getAllSpecialists = () => {
    return makeServiceCall('/specialists/all', 'GET', {});
}