import { Outlet } from "react-router-dom"
import Header from "../components/Headers/Header"
import MockView from "../components/MockView/MockView"
import Employees from "../components/Employees/Employees"
import LoginPage from "../Pages/LoginPage"
import Unauthorized from "../Pages/Unauthorized"
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
                        name: 'Lista Kont',
                        path: '/account-list',
                        allowedRoles: [Role["*"]], // admin 
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Lista Firm',
                        path: '/company-list',
                        allowedRoles: [Role["*"]], // cloud-admin 
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-warehouseman',
                        allowedRoles: [Role["*"]], // warehouseman / magazynier
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Lista Magazynów',
                        path: '/warehouse-list',
                        allowedRoles: [Role["*"]], // assembler / magazynier, specjalist, handlowiec, menager, kierownik magazynu, montazysta, brygadzista
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Wydanie / Przyjęcie',
                        path: '/delivery-receipt',
                        allowedRoles: [Role["*"]], // assembler / magazynier, kierownik magazynu
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Zapotrzebowania',
                        path: '/demand',
                        allowedRoles: [Role["*"]], // assembler / magazynier, kierownik magazynu
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Usterki',
                        path: '/issues',
                        allowedRoles: [Role["*"]], // assembler / magazynier, manager, brygadzista
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-assembler',
                        allowedRoles: [Role["*"]], // assembler / montażysta
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Zlecenia',
                        path: '/orders',
                        allowedRoles: [Role["*"]], // assembler / montażysta, specjalista, manager, handlowiec, brygadzista
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-warehouse-manager',
                        allowedRoles: [Role["*"]], // assembler / kierownik magazynu
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-brigade-leader',
                        allowedRoles: [Role["*"]], // assembler / brygadzista
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Pobranie / Zdanie',
                        path: '/home-retrieve-return',
                        allowedRoles: [Role["*"]], // assembler / brygadzista
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Harmonogram',
                        path: '/home-schedule',
                        allowedRoles: [Role["*"]], // assembler / brygadzista, manager
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-manager',
                        allowedRoles: [Role["*"]], // assembler / manager
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-espert',
                        allowedRoles: [Role["*"]], // assembler / specjalista
                        component: <Employees />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-merchant',
                        allowedRoles: [Role["*"]], // assembler / handlowiec
                        component: <Employees />
                    },
                ]
        }
    ]


// export const getAbsolutePath = () {

// }