import React from 'react'
import { CssBaseline } from '@mui/material'
import { createBrowserRouter, RouteObject, RouterProvider } from 'react-router-dom'
import { ThemeProvider } from '@mui/material/styles'
import { pageList, PageProps } from './utils/pageList'
import { AutorizedRoute } from './utils/authorize'
import { QueryClient, QueryClientProvider } from 'react-query'
import { ReactQueryDevtools } from 'react-query/devtools'

import { theme } from './themes/baseTheme'
import './app.less'

const browserRouterMapper = (pages: Array<PageProps>): Array<RouteObject> => {
    return pages.map((page) => {
        if (page.component)
            return {
                element: <AutorizedRoute {...page} />,
                children: [
                    {
                        path: page.path,
                        element: page.component,
                        children: page.children ? browserRouterMapper(page.children) : undefined,
                    },
                ],
            }
        else return {}
    })
}

const router = createBrowserRouter(browserRouterMapper(pageList))
const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            refetchOnWindowFocus: true,
            refetchOnReconnect: true,
            refetchOnMount: true,
        },
    },
})

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <ThemeProvider theme={theme}>
                <CssBaseline />
                <RouterProvider router={router} />
            </ThemeProvider>
            <ReactQueryDevtools initialIsOpen />
        </QueryClientProvider>
    )
}

export default App
