import React from 'react';
import { AppBar, Box, Button, IconButton, ImageList, ImageListItem, Toolbar } from '@mui/material';
import { styled } from '@mui/material/styles';
import firstPicture from '../../assets/img/firstPicture.png';
import secondPicture from '../../assets/img/secondPicture.png';
import logo from '../../assets/img/logo.png';
import NavMenuItem from '../NavMenuItem/NavMenuItem';
import { useNavigate } from 'react-router-dom';
import { pageList } from '../../utils/pageList';
import NavMenuButton from '../NavMenuItem/NavMenuButton';
import { removeToken } from '../../utils/token';

const CustomizedToolbar = styled(Toolbar)(({ theme }) => ({
  '@media (min-width: 600px)': {
    minHeight: 32,
  },
}));

const itemData = [
  {
    img: firstPicture,
    title: 'pic1',
  },
  {
    img: secondPicture,
    title: 'pic2',
  },
];

const Header = () => {
  const navigation = useNavigate();
  const handleLogout = () => {
    removeToken();
    navigation('/login');
  };

  const rootPage = pageList.find(p => p.path == '/');
  const rootPageChildrens = rootPage?.children;
  return (
    <AppBar position="static">
      <Box sx={{ flexGrow: 1, backgroundColor: '#1A1C26' }}>
        <ImageList
          gap={0}
          cols={2}
          sx={{
            margin: 0,
            '@media (min-width: 600px)': {
              padding: 0,
              marginTop: 1,
              color: 'red',
            },
          }}
        >
          {itemData.map((item) => (
            <ImageListItem key={item.img}>
              <img src={`${item.img}`} srcSet={`${item.img}`} alt={item.title} loading="lazy" />
            </ImageListItem>
          ))}
        </ImageList>
        <CustomizedToolbar>
          <Box component="img" sx={{ mr: 5 }} alt="Your logo." src={logo}></Box>
          {
            rootPageChildrens ? rootPageChildrens.map(page => {
              return <NavMenuButton {...page} />
            })
              :
              null
          }
          <IconButton sx={{marginLeft: 'auto'}} color="inherit" onClick={handleLogout}>
            Log out
          </IconButton>
          <Box></Box>
        </CustomizedToolbar>
      </Box>
    </AppBar>
  );
};

export default Header;
