import { makeServiceCall } from './utils.api'

type LogInPayloadType = {
    username: string
    password: string
}

type forgotPasswordPayloadType = {
    username: string
}

type resetPasswordPayloadType = {
    resetPasswordToken: string
    password: string
}

export const logIn = (data: LogInPayloadType) => {
    return makeServiceCall('/gettoken', 'POST', { body: data })
}

export const forgotPassword = (data: forgotPasswordPayloadType) => {
    return makeServiceCall('/password/forgot', 'POST', { body: data })
}

export const resetPassword = (data: resetPasswordPayloadType) => {
    return makeServiceCall('/password/reset', 'POST', { body: data })
}
