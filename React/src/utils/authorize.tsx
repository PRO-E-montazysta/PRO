import { Link, Navigate, Outlet, useLocation } from "react-router-dom";
import Unauthorized from "../pages/Unauthorized";
import { Role } from "../types/roleEnum";
import { getRolesFromToken, getToken } from "./token";


export const isAuthorized = (allowedRoles: Array<Role>) => {
    if (allowedRoles.indexOf(Role["*"]) >= 0) return true;

    const myRoles = getRolesFromToken();
    const found = allowedRoles.some(a => myRoles.indexOf(a) >= 0);

    return found;
}

type AutorizedRouteParams = {
    allowedRoles: Array<Role>
}

export const AutorizedRoute = (params: AutorizedRouteParams) => {
    const { allowedRoles } = params
    const token = getToken();
    const isAuthenticate = !!token ? true : false;
    const location = useLocation();

    if (location.pathname === '/login') return <Outlet />;

    if (!isAuthenticate) return <Navigate to='/login' />

    return isAuthorized(allowedRoles)
        ? <Outlet />
        : <Unauthorized />
}