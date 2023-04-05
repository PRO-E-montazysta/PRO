import React, { useEffect, useState } from 'react'
import jwt_decode from 'jwt-decode'
import { Navigate } from 'react-router-dom'

export const getToken = (): string | undefined => {
    const tokenString = localStorage.getItem('token')
    if (!!tokenString) {
        checkTokenExpiration(tokenString)
        return tokenString
    }
    logout()
    return undefined
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

const checkTokenExpiration = (token: string) => {
    if (!token) {
        logout()
        return
    }
    const decodedToken: DecodedTokenType = jwt_decode(token)
    const { exp } = decodedToken

    if (Date.now() >= exp * 1000) {
        logout()
    }
}

const logout = () => {
    console.warn('Token wygasł')
    removeToken()
}
