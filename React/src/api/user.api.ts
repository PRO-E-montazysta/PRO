import { makeServiceCall, PayloadProps } from './utils.api';

export const getAllUsers2 = () => {
  return makeServiceCall('/users/all', 'GET', {});
};

export const getAllUsers = (payload: PayloadProps) => {
  return makeServiceCall('/users/filter', 'GET', { ...payload });
};

export const getUser = (id:string) =>{
  return makeServiceCall(`/users/${id}`, 'GET',{})
}