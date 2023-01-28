import React, { useState } from 'react';

const useToken = () => {
  const getToken = (): string | undefined => {
    const tokenString = localStorage.getItem('token');
    if (!!tokenString) {
      return tokenString;
    }
    return undefined;
  };

  const [token, setToken] = useState(getToken());

  const saveToken = (userToken: any) => {
    localStorage.setItem('token', userToken.token);
    setToken(userToken.token);
  };

  const removeToken = () => {
    localStorage.removeItem('token');
  };

  return { token, setToken: saveToken, removeToken };
};

export default useToken;
