import { makeServiceCall } from './utils';

type LogInPayloadType = {
  username: string;
  password: string;
};

export const logIn = (data: LogInPayloadType) => {
  console.log({ data });
  return makeServiceCall('/gettoken', 'POST', { body: data });
};
