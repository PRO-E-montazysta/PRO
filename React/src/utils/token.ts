import React, { useState } from 'react';
import jwt_decode from 'jwt-decode';
import { Role } from '../types/roleEnum';



export const getToken = (): string | undefined => {
  const tokenString = localStorage.getItem('token');
  if (!!tokenString) {
    return tokenString;
  }
  return undefined;
};

export const setToken = (userToken: any) => {
  localStorage.setItem('token', userToken.token);
};

export const removeToken = () => {
  localStorage.removeItem('token');
};


export type DecodedTokenType = {
  exp: number;
  iat: number;
  iss: string;
  scope: string;
  sub: string;
};

export const getRolesFromToken = () => {
  const token = getToken();
  if (!token) return [];
  const decodedToken: DecodedTokenType = jwt_decode(token);
  const decodedUserRoles = decodedToken.scope.split(' ');;
  return decodedUserRoles;
};
