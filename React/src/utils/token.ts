import React, { useEffect, useState } from 'react'
import jwt_decode from 'jwt-decode'

export const getToken = (): string | undefined => {
    const tokenString = localStorage.getItem('token')
    if (!!tokenString) {
        return tokenString
    } else {
        return undefined
    }
}

export const setToken = (userToken: any) => {
    localStorage.setItem('token', userToken.token)
}

export const removeToken = () => {
    localStorage.removeItem('token')
}

export type DecodedTokenType = {
    exp: number
    iat: number
    iss: string
    scope: string
    sub: string
}

export const getRolesFromToken = () => {
    const token = getToken()
    if (!token) return []
    const decodedToken: DecodedTokenType = jwt_decode(token)
    const decodedUserRoles = decodedToken.scope.split(' ')

    return decodedUserRoles
}

export const isExpire = (token?: string) => {
    if (!token) {
        return true
    }
    const decodedToken: DecodedTokenType = jwt_decode(token)
    const { exp } = decodedToken

    if (Date.now() >= exp * 1000) {
        return true
    }
    return false
}

export const checkToken = () => {
    if (isExpire(getToken())) logout()
}

export const logout = () => {
    console.warn('logout')
    removeToken()
    if (window.location.pathname != '/login') window.location.href = '/login'
}
