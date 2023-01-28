import axios, { Method } from 'axios';

interface PayloadProps {
  body?: undefined | Record<string, unknown> | unknown;
  queryStringParams?: undefined | Record<string, unknown>;
}

export const makeServiceCall = async (url: string, httpMethod: Method, payload: PayloadProps) => {
  console.log({ payload });
  const response = await axios({
    method: httpMethod,
    url: url,
    data: payload?.body || {},
    params: payload?.queryStringParams || {},
    headers: {
      'Content-Type': 'application/json',
    },
  });
  return response.data;
};
