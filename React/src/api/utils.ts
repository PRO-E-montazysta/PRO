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
      'Content-Type': 'application/json'
    },
  });
  return response.data;
};

