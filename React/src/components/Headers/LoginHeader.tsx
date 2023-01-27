import React from 'react';
import logo from '../../assets/img/loginLogo.png';
import { styled } from '@mui/material/styles';
import { AppBar, Box } from '@mui/material';

// const CustomizedAppBar = styled(AppBar)(() => ({
//   backgroundColor: '#000000',
//   top: 51,
//   margin: 0,
//   padding: 0,
//   height: 333,
//   border: 0,
// }));

const LoginHeader = () => {
  return (
    <AppBar position="relative" sx={{ backgroundColor: '#000000', marginTop: '51px' }}>
      <Box component="img" alt="login image" src={logo} sx={{ width: '100%', height: '333px' }}></Box>
    </AppBar>
  );
};

export default LoginHeader;
