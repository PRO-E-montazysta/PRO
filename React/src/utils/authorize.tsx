import { Link, Navigate, Outlet, useLocation } from 'react-router-dom'
import Error from '../components/error/Error'
import { Role } from '../types/roleEnum'
import { pageList, PageProps } from './pageList'
import { getRolesFromToken, getToken, isExpire } from './token'

export const isAuthorized = (allowedRoles: Array<Role>) => {
    if (allowedRoles.indexOf(Role['*']) >= 0) return true
    if (allowedRoles.indexOf(Role['NOBODY']) >= 0) return false

    const myRoles = getRolesFromToken()
    const found = allowedRoles.some((a) => myRoles.indexOf(a) >= 0)

    return found
}

export const isAuthorizedToPage = (page: PageProps) => {
    let ar = page.allowedRoles
    if (!ar) ar = getParentAllowedRoles(page)

    return isAuthorized(ar)
}

export const AutorizedRoute = (page: PageProps) => {
    const token = getToken()
    const isAuthenticate = !!token ? true : false
    const location = useLocation()

    if (location.pathname === '/login') return <Outlet />

    if (!isAuthenticate || isExpire(token)) return <Navigate to="/login" />

    return isAuthorizedToPage(page) ? (
        <Outlet />
    ) : (
        <Error code={401} message={'Nie masz uprawnień do oglądania tej strony'} />
    )
}

const getParentAllowedRoles = (page: PageProps): Array<Role> => {
    const rootPage = pageList.find((p) => p.path === '/')
    const rootPageChildrens = rootPage?.children

    const parent = rootPageChildrens?.find((p) => p.children && p.children.map((c) => c.path).indexOf(page.path) != -1)
    if (parent && parent.allowedRoles) return parent.allowedRoles
    else return []
}
