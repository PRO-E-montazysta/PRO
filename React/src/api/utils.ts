import axios, { Method } from 'axios';
import { getToken } from '../utils/token';

interface PayloadProps {
  body?: undefined | Record<string, unknown> | unknown;
  queryStringParams?: undefined | Record<string, unknown>;
}


const getBaseUrl = (): string => {
  let url: string;
  switch (process.env.NODE_ENV) {
    case 'production':
      url = 'http://backend/api/v1';
      break;
    case 'development':
      url = 'http://localhost:8080/api/v1';
      break;
    default:
      url = 'http://backend/api/v1';
  }
  return url;
}


export const makeServiceCall: any = async (url: string, httpMethod: Method, payload: PayloadProps) => {
  console.log({ payload });
  const response = await axios({
    method: httpMethod,
    baseURL: getBaseUrl(),
    url: url,
    data: payload?.body || {},
    params: payload?.queryStringParams || {},
    headers: {
      'Content-Type': 'application/json',
      // 'bearer-key': useToken().token
    },
  });
  return response.data;
};



export const useRequest = (url: string, httpMethod: Method, payload: PayloadProps) => {
  // return 
  const { body, queryStringParams } = payload;
  console.log({ payload });
  const token = getToken();
  const response = axios({
    method: httpMethod,
    baseURL: getBaseUrl(),
    url: url,
    data: body,
    params: queryStringParams,
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + token
    },
  });
  return response;
}


