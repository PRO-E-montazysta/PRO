import React from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import useToken from '../../Hooks/useToken';
import Header from '../Headers/Header';

const PrivateRoutes = () => {
  const { token } = useToken();
  const isAuthorized = !!token ? true : false;
  return isAuthorized ? <><Header /><Outlet /></> : <Navigate to="/login" />;
};

export default PrivateRoutes;
