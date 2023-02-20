import { Outlet } from "react-router-dom"
import Header from "../components/Headers/Header"
import AccountList from "../Pages/AccountList"
import CompanyList from "../Pages/CompanyList"
import HomePageWarehousemen from "../Pages/WarehousemenHomePage"
import HomePageWarehouseManager from "../Pages/WarehouseManagersHomePage"
import HomePageSpecialist from "../Pages/SpecialistsHomePage"
import HomePageManager from "../Pages/ManagersHomePage"
import HomePageSalesRepresentative from "../Pages/SalesRepresentativesHomePage"
import HomePageForeman from "../Pages/ForemenHomePage"
import HomePageFitter from "../Pages/FittersHomePage"
import WarehouseList from "../Pages/WarehouseList"
import DeliveryReceipt from "../Pages/DeliveryReceipt"
import Demand from "../Pages/Demand"
import Issues from "../components/Issues/Issues"
import Orders from "../Pages/Orders"
import RetrieveReturn from "../Pages/RetrieveReturn"
import Schedules from "../Pages/Schedules"
import LoginPage from "../Pages/LoginPage"
import { Role } from "../types/roleEnum"
import Error from "../components/Error/Error"

export type PageProps = {
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
                        allowedRoles: [Role.WAREHOUSE_MAN],
                        component: <HomePageWarehousemen />
                    },
                    {
                        inNav: true,
                        name: 'Lista Magazynów',
                        path: '/warehouse-list',
                        allowedRoles: [Role.WAREHOUSE_MAN, Role.SPECIALIST, Role.FITTER, Role.MANAGER, Role.WAREHOUSE_MANAGER, Role.FOREMAN, Role.SALES_REPRESENTATIVE],
                        component: <WarehouseList />
                    },
                    {
                        inNav: true,
                        name: 'Wydanie / Przyjęcie',
                        path: '/delivery-receipt',
                        allowedRoles: [Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER],
                        component: <DeliveryReceipt />
                    },
                    {
                        inNav: true,
                        name: 'Zapotrzebowania',
                        path: '/demand',
                        allowedRoles: [Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER],
                        component: <Demand />
                    },
                    {
                        inNav: true,
                        name: 'Usterki',
                        path: '/issues',
                        allowedRoles: [Role.WAREHOUSE_MAN, Role.MANAGER, Role.FOREMAN],
                        component: <Issues />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-fitter',
                        allowedRoles: [Role.FITTER],
                        component: <HomePageFitter />
                    },
                    {
                        inNav: true,
                        name: 'Zlecenia',
                        path: '/orders',
                        allowedRoles: [Role.FITTER, Role.SPECIALIST, Role.MANAGER, Role.SALES_REPRESENTATIVE, Role.FOREMAN],
                        component: <Orders />
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
                        allowedRoles: [Role.FOREMAN],
                        component: <HomePageForeman />
                    },
                    {
                        inNav: true,
                        name: 'Pobranie / Zdanie',
                        path: '/home-retrieve-return',
                        allowedRoles: [Role.FOREMAN],
                        component: <RetrieveReturn />
                    },
                    {
                        inNav: true,
                        name: 'Harmonogram',
                        path: '/home-schedule',
                        allowedRoles: [Role.FOREMAN, Role.MANAGER],
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
                        allowedRoles: [Role.SPECIALIST],
                        component: <HomePageSpecialist />
                    },
                    {
                        inNav: true,
                        name: 'Strona główna',
                        path: '/home-SalesRepresentative',
                        allowedRoles: [Role.SALES_REPRESENTATIVE],
                        component: <HomePageSalesRepresentative />
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
    ]

