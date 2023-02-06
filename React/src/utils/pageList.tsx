import { Outlet } from "react-router-dom"
import Header from "../components/Headers/Header"
import MockView from "../components/MockView/MockView"
import Employees from "../pages/Employees"
import LoginPage from "../pages/LoginPage"
import Unauthorized from "../pages/Unauthorized"
import { Role } from "../types/roleEnum"

export interface PageProps {
    name: string
    path: string
    allowedRoles: Array<Role>
    children?: Array<PageProps>
    component?: JSX.Element
    inNav: boolean
}

const Root = () => {
    return <>
        <Header />
        <Outlet />
    </>
}


export const pageList: Array<PageProps> =
    [
        {
            inNav: false,
            name: 'Login',
            path: '/login',
            allowedRoles: [Role["*"]],
            component: <LoginPage />
        },
        {
            inNav: false,
            name: 'Nieautoryzowany dostęp',
            path: '/unauthorized',
            allowedRoles: [Role["*"]],
            component: <Unauthorized />
        },
        {
            inNav: false,
            name: '',
            path: '/',
            allowedRoles: [Role["*"]],
            component: <Root />,
            children:
                [
                    {
                        inNav: true,
                        name: 'Mock',
                        path: '/mock',
                        allowedRoles: [Role["*"]],
                        component: <MockView />
                    },
                    {
                        inNav: true,
                        name: 'Pracownicy',
                        path: '/employees',
                        allowedRoles: [Role["*"]],
                        component: <Employees />
                    },

                    {
                        inNav: true,
                        name: 'Zlecenia',
                        path: '/zlecenia',
                        allowedRoles: [Role.a, Role.b, Role.CLOUD_ADMIN],
                        children: [
                            {
                                inNav: true,
                                name: 'Moje',
                                path: '/zlecenia/moje',
                                allowedRoles: [Role.a],
                                component: <div>moje zlecenia</div>
                            },
                            {
                                inNav: true,
                                name: 'Twoje',
                                path: '/zlecenia/twoje',
                                allowedRoles: [Role.b],
                                component: <div>twje zlecenia</div>
                            },
                            {
                                inNav: true,
                                name: 'Nasze',
                                path: '/zlecenia/nasze',
                                allowedRoles: [Role.a, Role.b],
                                component: <div>Nasze zlecenia</div>
                            },
                            {
                                inNav: true,
                                name: 'Wszystkich',
                                path: '/zlecenia/wszystkich',
                                allowedRoles: [Role["*"]],
                                component: <div>Wszystkich zlecenia</div>
                            },
                            {
                                inNav: true,
                                name: 'Nasze',
                                path: '/zlecenia/niczyje',
                                allowedRoles: [],
                                component: <div>Niczyje zlecenia</div>
                            },
                            {
                                inNav: true,
                                name: 'Admin',
                                path: '/zlecenia/admin',
                                allowedRoles: [Role.ADMIN],
                                component: <div>Admina zlecenia</div>
                            },
                            {
                                inNav: true,
                                name: 'Cloud admin',
                                path: '/zlecenia/cloudadmin',
                                allowedRoles: [Role.CLOUD_ADMIN],
                                component: <div>Admina chmury zlecenia</div>
                            },
                        ]
                    }
                ]
        }
    ]



// export const getAbsolutePath = () {

// }