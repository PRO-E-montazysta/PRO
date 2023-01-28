import { makeServiceCall } from './utils';

type LogInPayloadType = {
  username: string;
  password: string;
};

export const logIn = (data: LogInPayloadType) => {
  console.log({ data });
  return makeServiceCall('http://localhost:8080/api/v1/gettoken', 'POST', { body: data });
};
