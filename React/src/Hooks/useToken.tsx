import React, { useState } from 'react';

const useToken = () => {
  const getToken = (): string | undefined => {
    const tokenString = localStorage.getItem('token');
    if (!!tokenString) {
      const userToken = tokenString && JSON.parse(tokenString);
      console.log({ userToken });
      return userToken.token;
    }
    return;
  };
  
  const [token, setToken] = useState(getToken());

  const saveToken = (userToken: any) => {
    localStorage.setItem('token', JSON.parse(userToken));
    setToken(userToken.token);
  };


  return { token, setToken: saveToken };
};

export default useToken;
