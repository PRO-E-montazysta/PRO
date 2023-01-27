import React from 'react';
import { CssBaseline } from '@mui/material';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from './components/Headers/Header';
import Employees from './components/Employees/Employees';
import MockView from './components/MockView/MockView';
import LoginPage from './Pages/LoginPage';

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

//use effect sprawdzający token, jeśli nie ma return to LoginPage, jesli jest to do '/'
//token prawdopodobnie powienien być sprawdzany z localstorage

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <LoginPage />
      {/* <Router>
        <Header />
        <Routes>
          <Route path="/else" element={<MockView />} />
          <Route path="/else" element={<MockView />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/employees" element={<Employees />} />
        </Routes>
      </Router> */}
    </ThemeProvider>
  );
}

export default App;
