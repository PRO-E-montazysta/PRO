import { makeServiceCall, PayloadProps } from './utils.api'

export const getAllOrderSages = () => {
    return makeServiceCall('/order-stages/filter', 'GET', {})
}
