import { Outlet } from "react-router-dom"
import Header from "../components/headers/Header"
import AccountList from "../pages/AccountList"
import CompanyList from "../pages/CompanyList"
import HomePageWarehousemen from "../pages/WarehousemenHomePage"
import HomePageWarehouseManager from "../pages/WarehouseManagersHomePage"
import Homepagespecialist from "../pages/SpecialistsHomePage"
import HomePageManager from "../pages/ManagersHomePage"
import HomepagesalesRepresentative from "../pages/SalesRepresentativesHomePage"
import WarehousemenHomePage from "../pages/ForemenHomePage"
import HomePageFitter from "../pages/FittersHomePage"
import DeliveryReceipt from "../pages/DeliveryReceipt"
import Demand from "../pages/Demand"
import Issues from "../components/issues/Issues"

import RetrieveReturn from '../pages/RetrieveReturn'
import Schedules from '../pages/Schedules'
import LoginPage from '../pages/LoginPage'
import { Role } from '../types/roleEnum'
import Error from '../components/error/Error'
import Orders from '../pages/orders'
import OrderDetails from '../pages/orders/OrderDetails'

import ToolTypes from '../pages/toolTypes'
import ToolTypeDetails from '../pages/toolTypes/ToolTypeDetails'
import WarehouseDetails from '../pages/warehouses/WarehouseDetails'
import Warehouses from '../pages/warehouses'

export type PageProps = {
    name: string
    path: string
    allowedRoles?: Array<Role>
    children?: Array<PageProps>
    component?: JSX.Element
    inNav: boolean
}

const Root = () => {
    return (
        <>
            <Header />
            <Outlet />
        </>
    )
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
                        allowedRoles: [Role.ADMIN],
                        component: <AccountList />
                    },
                    {
                        inNav: true,
                        name: 'Lista Firm',
                        path: '/company-list',
                        allowedRoles: [Role.CLOUD_ADMIN],
                        component: <CompanyList />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-warehouseman',
                        allowedRoles: [Role.WAREHOUSEMAN],
                        component: <HomePageWarehousemen />
                    },
                    {
                        inNav: true,
                        name: 'Wydanie / Przyjęcie',
                        path: '/delivery-receipt',
                        allowedRoles: [Role.WAREHOUSEMAN, Role.WAREHOUSE_MANAGER],
                        component: <DeliveryReceipt />
                    },
                    {
                        inNav: true,
                        name: 'Zapotrzebowania',
                        path: '/demand',
                        allowedRoles: [Role.WAREHOUSEMAN, Role.WAREHOUSE_MANAGER],
                        component: <Demand />
                    },
                    {
                        inNav: true,
                        name: 'Usterki',
                        path: '/issues',
                        allowedRoles: [Role.WAREHOUSEMAN, Role.MANAGER, Role.BRIGADE_LEADER],
                        component: <Issues />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-fitter',
                        allowedRoles: [Role.ASSEMBLER],
                        component: <HomePageFitter />
                    },
                    {
                        inNav: true,
                        name: 'Zlecenia',
                        path: '/orders',
                        allowedRoles: [Role.ASSEMBLER, Role.EXPERT, Role.MANAGER, Role.MERCHANT, Role.BRIGADE_LEADER, Role["*"]],
                        component: <Orders />
                    },
                    {
                        inNav: false,
                        name: '',
                        path: '/orders/:id',
                        allowedRoles: [Role.ASSEMBLER, Role.EXPERT, Role.MANAGER, Role.MERCHANT, Role.BRIGADE_LEADER, Role["*"]],
                        component: <OrderDetails />
                    },
                    {
                        inNav: true,
                        name: 'Magazyny',
                        path: '/warehouses',
                        allowedRoles: [Role.MANAGER, Role.MERCHANT, Role.EXPERT, Role.WAREHOUSEMAN, Role.WAREHOUSE_MANAGER, Role.ASSEMBLER, Role.BRIGADE_LEADER],
                        component: <Warehouses />
                    },
                    {
                        inNav: false,
                        name: '',
                        path: '/warehouses/:id',
                        allowedRoles: [Role.MANAGER, Role.MERCHANT, Role.EXPERT, Role.WAREHOUSEMAN, Role.WAREHOUSE_MANAGER, Role.ASSEMBLER, Role.BRIGADE_LEADER],
                        component: <WarehouseDetails />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-warehouse-manager',
                        allowedRoles: [Role.WAREHOUSE_MANAGER],
                        component: <HomePageWarehouseManager />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-brigade-leader',
                        allowedRoles: [Role.BRIGADE_LEADER],
                        component: <WarehousemenHomePage />
                    },
                    {
                        inNav: true,
                        name: 'Pobranie / Zdanie',
                        path: '/home-retrieve-return',
                        allowedRoles: [Role.BRIGADE_LEADER],
                        component: <RetrieveReturn />
                    },
                    {
                        inNav: true,
                        name: 'Harmonogram',
                        path: '/home-schedule',
                        allowedRoles: [Role.BRIGADE_LEADER, Role.MANAGER],
                        component: <Schedules />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-manager',
                        allowedRoles: [Role.MANAGER],
                        component: <HomePageManager />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-Specialist',
                        allowedRoles: [Role.EXPERT],
                        component: <Homepagespecialist />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-SalesRepresentative',
                        allowedRoles: [Role.MERCHANT],
                        component: <HomepagesalesRepresentative />
                    },
                ]
        },
        {
            inNav: false,
            name: 'Error',
            path: '*',
            allowedRoles: [Role["*"]],
            component: <Error code={404} message={'Nie znaleziono strony'} description={'Zgubiłeś się ? Wróć na stronę główną!'} />
        },
    ];
