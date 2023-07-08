import axios, { AxiosError, Method } from 'axios'
import { getToken, logout } from '../utils/token'

export type PayloadProps = {
    body?: undefined | Record<string, unknown> | unknown
    queryParams?: undefined | Record<string, unknown>
}

export const getBaseUrl = (): string => {
    let url: string
    switch (window.REACT_APP_ENVIRONMENT) {
        case 'production':
            url = 'https://emontazysta.pl/api/v1'
            break
        case 'development':
            url = 'https://dev.emontazysta.pl/api/v1'
            break
        default:
            url = 'http://localhost:8080/api/v1'
    }
    return url
}

export const makeServiceCall = async (url: string, httpMethod: Method, payload: PayloadProps, isFormData?: boolean) => {
    const { body, queryParams } = payload
    const token = getToken()

    let headers = {
        'Content-Type': isFormData ? 'multipart/form-data' : 'application/json',
        'Access-Control-Allow-Origin': '*',
        Authorization: '',
    }
    if (token) headers.Authorization = 'Bearer ' + token

    try {
        const response = await axios({
            method: httpMethod,
            baseURL: getBaseUrl(),
            url: url,
            data: body,
            params: queryParams,
            headers: headers,
        })

        return response.data
    } catch (error: any) {
        console.warn(error)
        if (error.response && error.response.status == 401) logout()
        else throw error
    }
}
