import { Outlet } from 'react-router-dom'
import Header from '../components/headers/Header'
import AccountList from '../pages/AccountList'
import CompanyList from '../pages/CompanyList'
import HomePageWarehousemen from '../pages/WarehousemenHomePage'
import HomePageWarehouseManager from '../pages/WarehouseManagersHomePage'
import Homepagespecialist from '../pages/SpecialistsHomePage'
import HomePageManager from '../pages/ManagersHomePage'
import HomepagesalesRepresentative from '../pages/SalesRepresentativesHomePage'
import HomePageForeman from '../pages/ForemenHomePage'
import HomePageFitter from '../pages/FittersHomePage'
import DeliveryReceipt from '../pages/DeliveryReceipt'
import Demand from '../pages/Demand'
import Issues from '../components/issues/Issues'

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

export const pageList: Array<PageProps> = [
    {
        inNav: false,
        name: 'Login',
        path: '/login',
        allowedRoles: [Role['*']],
        component: <LoginPage />,
    },
    {
        inNav: false,
        name: '',
        path: '/',
        allowedRoles: [Role['*']],
        component: <Root />,
        children: [
            {
                inNav: true,
                name: 'Lista Kont',
                path: '/account-list',
                allowedRoles: [Role.ADMIN],
                component: <AccountList />,
            },
            {
                inNav: true,
                name: 'Lista Firm',
                path: '/company-list',
                allowedRoles: [Role.CLOUD_ADMIN],
                component: <CompanyList />,
            },
            {
                inNav: true,
                name: 'Strona główna',
                path: '/home-warehouseman',
                allowedRoles: [Role.WAREHOUSE_MAN],
                component: <HomePageWarehousemen />,
            },
            {
                inNav: true,
                name: 'Wydanie / Przyjęcie',
                path: '/delivery-receipt',
                allowedRoles: [Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER],
                component: <DeliveryReceipt />,
            },
            {
                inNav: true,
                name: 'Zapotrzebowania',
                path: '/demand',
                allowedRoles: [Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER],
                component: <Demand />,
            },
            {
                inNav: true,
                name: 'Usterki',
                path: '/issues',
                allowedRoles: [Role.WAREHOUSE_MAN, Role.MANAGER, Role.FOREMAN],
                component: <Issues />,
            },
            {
                inNav: true,
                name: 'Strona główna',
                path: '/home-fitter',
                allowedRoles: [Role.FITTER],
                component: <HomePageFitter />,
            },
            {
                inNav: true,
                name: 'Typy narzędzi',
                path: '/tooltypes',
                allowedRoles: [Role.WAREHOUSE_MANAGER, Role['*']],
                component: <ToolTypes />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista typów narzędzi',
                        path: '/tooltypes'
                    },
                    {
                        inNav: true,
                        name: 'Dodaj typ narzędzi',
                        path: '/tooltypes/new'
                    }
                ]
            },
            {
                inNav: false,
                name: '',
                path: '/tooltypes/:id',
                allowedRoles: [Role.WAREHOUSE_MANAGER, Role['*']],
                component: <ToolTypeDetails />,
            },
            {
                inNav: true,
                name: 'Zlecenia',
                path: '/orders',
                allowedRoles: [Role.FITTER, Role.SPECIALIST, Role.MANAGER, Role.SALES_REPRESENTATIVE, Role.FOREMAN, Role["*"]],
                component: <Orders />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista zleceń',
                        path: '/orders'
                    },
                    {
                        inNav: true,
                        name: 'Dodaj zlecenie',
                        path: '/orders/new'
                    }
                ]
            },
            {
                inNav: false,
                name: '',
                path: '/orders/:id',
                allowedRoles: [
                    Role.FITTER,
                    Role.SPECIALIST,
                    Role.MANAGER,
                    Role.SALES_REPRESENTATIVE,
                    Role.FOREMAN,
                    Role['*'],
                ],
                component: <OrderDetails />,
            },
            {
                inNav: true,
                name: 'Magazyny',
                path: '/warehouses',
                allowedRoles: [
                    Role.MANAGER,
                    Role.SALES_REPRESENTATIVE,
                    Role.SPECIALIST,
                    Role.WAREHOUSE_MAN,
                    Role.WAREHOUSE_MANAGER,
                    Role.FITTER,
                    Role.FOREMAN,
                    Role['*'],
                ],
                component: <Warehouses />,
                children: [
                    {
                        inNav: true,
                        name: 'Lista magazynów',
                        path: '/warehouses'
                    },
                    {
                        inNav: true,
                        name: 'Dodaj magazyn',
                        path: '/warehouses/new'
                    }
                ]
            },
            {
                inNav: false,
                name: '',
                path: '/warehouses/:id',
                allowedRoles: [
                    Role.MANAGER,
                    Role.SALES_REPRESENTATIVE,
                    Role.SPECIALIST,
                    Role.WAREHOUSE_MAN,
                    Role.WAREHOUSE_MANAGER,
                    Role.FITTER,
                    Role.FOREMAN,
                    Role['*'],
                ],
                component: <WarehouseDetails />,
            },
            {
                inNav: true,
                name: 'Strona główna',
                path: '/home-warehouse-manager',
                allowedRoles: [Role.WAREHOUSE_MANAGER],
                component: <HomePageWarehouseManager />,
            },
            {
                inNav: true,
                name: 'Strona główna',
                path: '/home-brigade-leader',
                allowedRoles: [Role.FOREMAN],
                component: <HomePageForeman />,
            },
            {
                inNav: true,
                name: 'Pobranie / Zdanie',
                path: '/home-retrieve-return',
                allowedRoles: [Role.FOREMAN],
                component: <RetrieveReturn />,
            },
            {
                inNav: true,
                name: 'Harmonogram',
                path: '/home-schedule',
                allowedRoles: [Role.FOREMAN, Role.MANAGER],
                component: <Schedules />,
            },
            {
                inNav: true,
                name: 'Strona główna',
                path: '/home-manager',
                allowedRoles: [Role.MANAGER],
                component: <HomePageManager />,
            },
            {
                inNav: true,
                name: 'Strona główna',
                path: '/home-Specialist',
                allowedRoles: [Role.SPECIALIST],
                component: <Homepagespecialist />,
            },
            {
                inNav: true,
                name: 'Strona główna',
                path: '/home-SalesRepresentative',
                allowedRoles: [Role.SALES_REPRESENTATIVE],
                component: <HomepagesalesRepresentative />,
            },
        ],
    },
    {
        inNav: false,
        name: 'Error',
        path: '*',
        allowedRoles: [Role['*']],
        component: (
            <Error code={404} message={'Nie znaleziono strony'} description={'Zgubiłeś się ? Wróć na stronę główną!'} />
        ),
    },
]
