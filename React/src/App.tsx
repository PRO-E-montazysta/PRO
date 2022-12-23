import React from 'react';
import { CssBaseline } from '@mui/material';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Header from './components/Header/Header';
import Employees from './components/Employees/Employees';
import MockView from './components/MockView/MockView';

const theme = createTheme({
  components: {
    MuiCssBaseline: {
      styleOverrides: {
        body: { backgroundColor: '#282A3A' },
      },
    },
  },
});
function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        <Header />
        <Routes>
          <Route path="/else" element={<MockView />} />
          <Route path="/else" element={<MockView />} />
          <Route path="/else" element={<MockView />} />
          <Route path="/employees" element={<Employees />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
