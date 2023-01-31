import React from 'react';
import { CssBaseline } from '@mui/material';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from './components/Headers/Header';
import Employees from './components/Employees/Employees';
import MockView from './components/MockView/MockView';
import LoginPage from './Pages/LoginPage';
import useToken from './Hooks/useToken';
import PrivateRoutes from './components/PrivateRoutes/PrivateRoutes';

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

function App() {
  const { token, setToken } = useToken();

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        <Routes>
          <Route element={<PrivateRoutes />}>
            <Route path="/" element={<MockView />} />
            <Route path="/else" element={<MockView />} />
            <Route path="/employees" element={<Employees />} />
          </Route>
          <Route path="/login" element={<LoginPage />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
