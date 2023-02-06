import React, { useEffect } from 'react';
import { CssBaseline } from '@mui/material';
import { BrowserRouter as Router, Routes, Route, createBrowserRouter, RouteObject, RouterProvider, Outlet } from 'react-router-dom';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { pageList, PageProps } from './utils/pageList';
import { AutorizedRoute, isAuthorized } from './utils/authorize';

const theme = createTheme({
  palette: {
    primary: {
      main: '#282A3A',
    },
    secondary: {
      main: '#1A1C26',
      contrastText: '#FFFFFF',
    },
  },
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        body: { backgroundColor: '#282A3A' },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          color: '#FFFFFF',
        },
      },
    },
  },
});





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


function App() {

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <RouterProvider router={router} />
    </ThemeProvider>
  );
}




export default App;

