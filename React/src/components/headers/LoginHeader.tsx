import React from 'react';
import logo from '../../assets/img/loginLogo.png';
import { AppBar, Box } from '@mui/material';



const LoginHeader = () => {
  return (
    <AppBar position="relative" sx={{ backgroundColor: '#000000', marginTop: '51px' }}>
      <Box component="img" alt="login image" src={logo} sx={{ width: '100%', height: '333px' }}></Box>
    </AppBar>
  );
};

export default LoginHeader;
