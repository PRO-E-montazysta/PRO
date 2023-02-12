import { Outlet } from "react-router-dom"
import Header from "../components/Headers/Header"
import MockView from "../components/MockView/MockView"
import Employees from "../components/Employees/Employees"
import AccountList from "../components/Admins/AccountList"
import CompanyList from "../components/CloudAdmins/CompanyList"
import HomePageWarehousemen from "../components/Warehousemen/HomePage"
import HomePageWarehouseManager from "../components/WarehouseManagers/HomePage"
import HomePageExpert from "../components/Experts/HomePage"
import HomePageManager from "../components/Managers/HomePage"
import HomePageSalesRepresentative from "../components/SalesRepresentatives/HomePage"
import HomePageForeman from "../components/Foremen/HomePage"
import HomePageFitter from "../components/Fitters/HomePage"
import WarehouseList from "../components/WarehouseList/WarehouseList"
import DeliveryReceipt from "../components/DeliveryReceipt/DeliveryReceipt"
import Demand from "../components/Demand/Demand"
import Issues from "../components/Issues/Issues"
import Orders from "../components/Orders/Orders"
import RetrieveReturn from "../components/Foremen/RetrieveReturn"
import Schedules from "../components/Schedules/Schedules"
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
                        component: <AccountList />
                    },
                    {
                        inNav: true,
                        name: 'Lista Firm',
                        path: '/company-list',
                        allowedRoles: [Role["*"]], // cloud-admin 
                        component: <CompanyList />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-warehouseman',
                        allowedRoles: [Role["*"]], // warehouseman / magazynier
                        component: <HomePageWarehousemen />
                    },
                    {
                        inNav: true,
                        name: 'Lista Magazynów',
                        path: '/warehouse-list',
                        allowedRoles: [Role["*"]], // magazynier, specjalist, handlowiec, menager, kierownik magazynu, montazysta, brygadzista
                        component: <WarehouseList />
                    },
                    {
                        inNav: true,
                        name: 'Wydanie / Przyjęcie',
                        path: '/delivery-receipt',
                        allowedRoles: [Role["*"]], //  magazynier, kierownik magazynu
                        component: <DeliveryReceipt />
                    },
                    {
                        inNav: true,
                        name: 'Zapotrzebowania',
                        path: '/demand',
                        allowedRoles: [Role["*"]], // magazynier, kierownik magazynu
                        component: <Demand />
                    },
                    {
                        inNav: true,
                        name: 'Usterki',
                        path: '/issues',
                        allowedRoles: [Role["*"]], //  / magazynier, manager, brygadzista
                        component: <Issues />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-fitter',
                        allowedRoles: [Role["*"]], //  / montażysta
                        component: <HomePageFitter />
                    },
                    {
                        inNav: true,
                        name: 'Zlecenia',
                        path: '/orders',
                        allowedRoles: [Role["*"]], //  / montażysta, specjalista, manager, handlowiec, brygadzista
                        component: <Orders />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-warehouse-manager',
                        allowedRoles: [Role["*"]], //  / kierownik magazynu
                        component: <HomePageWarehouseManager />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-brigade-leader',
                        allowedRoles: [Role["*"]], //  / brygadzista
                        component: <HomePageForeman />
                    },
                    {
                        inNav: true,
                        name: 'Pobranie / Zdanie',
                        path: '/home-retrieve-return',
                        allowedRoles: [Role["*"]], //  / brygadzista
                        component: <RetrieveReturn />
                    },
                    {
                        inNav: true,
                        name: 'Harmonogram',
                        path: '/home-schedule',
                        allowedRoles: [Role["*"]], //  / brygadzista, manager
                        component: <Schedules />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-manager',
                        allowedRoles: [Role["*"]], //  / manager
                        component: <HomePageManager />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-expert',
                        allowedRoles: [Role["*"]], //  / specjalista
                        component: <HomePageExpert />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-SalesRepresentative',
                        allowedRoles: [Role["*"]], //  / handlowiec
                        component: <HomePageSalesRepresentative />
                    },
                ]
        }
    ]


// export const getAbsolutePath = () {

// }