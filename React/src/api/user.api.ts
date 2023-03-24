import { AppUser } from '../types/model/AppUser'
import { makeServiceCall, PayloadProps } from './utils.api';

export const getAllUsers = () => {
  return makeServiceCall('/users/all', 'GET', {})
}

export const getFilteredUsers  = (payload: PayloadProps) => {
  return makeServiceCall('/users/filter', 'GET', { ...payload });
};

export const getUserById  = (id:string) =>{
  return makeServiceCall(`/users/${id}`, 'GET',{})
}

export const getUserDetails = (id: string) => {
  return makeServiceCall(`/users/${id}`, 'GET', {})
}
  
  export const updateUser = (data: AppUser) => {
  return makeServiceCall(`/users/${data.id}`, 'PUT', { body: data })
  }
  
  export const postUser = (data: AppUser) => {
  return makeServiceCall(`/users`, 'POST', { body: data })
  }
  
  export const deleteUser = (id: string | number) => {
  return makeServiceCall(`/users/${id}`, 'DELETE', {})
  }
  