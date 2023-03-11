import axios, { Method } from 'axios';
import { getToken } from '../utils/token';

export type PayloadProps = {
  body?: undefined | Record<string, unknown> | unknown;
  queryParams?: undefined | Record<string, unknown>;
}


export const getBaseUrl = (): string => {
  let url: string;
  switch (process.env.NODE_ENV) {
    case 'production':
      url = 'https://dev.emontazysta.pl/api/v1';
      break;
    case 'development':
      url = 'http://localhost:8080/api/v1';
      break;
    default:
      url = 'https://dev.emontazysta.pl/api/v1';
  }
  return url;
}


export const makeServiceCall = async (url: string, httpMethod: Method, payload: PayloadProps) => {
  const { body, queryParams } = payload
  const token = getToken();

  let headers = {
    'Content-Type': 'application/json',
    'Authorization': ''
  };
  if (token)
    headers.Authorization = 'Bearer ' + token;
  const response = await axios({
    method: httpMethod,
    baseURL: getBaseUrl(),
    url: url,
    data: body,
    params: queryParams,
    headers: headers
  });
  return response.data;
};

