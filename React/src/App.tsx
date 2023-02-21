import React, { useEffect } from 'react';
import { CssBaseline } from '@mui/material';
import { BrowserRouter as Router, Routes, Route, createBrowserRouter, RouteObject, RouterProvider, Outlet } from 'react-router-dom';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { pageList, PageProps } from './utils/pageList';
import { AutorizedRoute, isAuthorized } from './utils/authorize';
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from 'react-query/devtools'

import { theme } from './themes/baseTheme';

const browserRouterMapper = (pages: Array<PageProps>) => {
  const result: Array<RouteObject> =
    pages.map(page => {
      return {
        element: <AutorizedRoute allowedRoles={page.allowedRoles} />,
        children: [
          {
            path: page.path,
            element: page.component,
            children: page.children ? browserRouterMapper(page.children) : undefined
          }
        ]
      }
    })
  return result;
}

const router = createBrowserRouter(browserRouterMapper(pageList));
const queryClient = new QueryClient();

function App() {

  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <RouterProvider router={router} />
      </ThemeProvider>
      <ReactQueryDevtools initialIsOpen/>
    </QueryClientProvider>
  );
}




export default App;

