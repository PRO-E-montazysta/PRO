import { makeServiceCall } from './utils.api';

type LogInPayloadType = {
  username: string;
  password: string;
};

export const logIn = (data: LogInPayloadType) => {
  return makeServiceCall('/gettoken', 'POST', { body: data });
};
