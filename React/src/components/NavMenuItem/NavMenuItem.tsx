import { Button, Menu, MenuItem } from '@mui/material';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

type NavMenuItemTypes = {
  name: string;
  items: { subName: string; link: string }[];
  flexGrowStyle?: number | 0;
};
const NavMenuItem = ({ name, items, flexGrowStyle }: NavMenuItemTypes) => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const navigation = useNavigate();
  const handleMenuClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event?.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
    navigation(items[0].link, { replace: true });
  };

  return (
    <div style={{ flexGrow: flexGrowStyle, marginRight: 10 }}>
      <Button onClick={handleMenuClick} sx={{ color: 'white' }}>
        {name}
      </Button>
      <Menu anchorEl={anchorEl} open={open} onClose={handleClose}>
        {items.map((item) => {
          return (
            <MenuItem onClick={handleClose}>
              <div>{item.subName}</div>
            </MenuItem>
          );
        })}
      </Menu>
    </div>
  );
};

export default NavMenuItem;
