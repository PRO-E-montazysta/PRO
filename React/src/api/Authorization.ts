import { makeServiceCall } from './utils';

type LogInPayloadType = {
  email: string;
  password: string;
};

export const logIn = (payload: LogInPayloadType) => {
  return makeServiceCall('localhost:8080/api/v1/gettoken', 'POST', { body: payload });
};
