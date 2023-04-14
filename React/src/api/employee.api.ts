import { Employee } from '../types/model/Employee'
import { makeServiceCall, PayloadProps } from './utils.api';

export const getAllEmployees = () => {
  return makeServiceCall('/users/all', 'GET', {})
}

export const getFilteredEmployees = (payload: PayloadProps) => {
  return makeServiceCall('/users/filter', 'GET', { ...payload });
};

export const getEmployeeById = (id: string) => {
  return makeServiceCall(`/users/${id}`, 'GET', {})
}

export const getEmployeeDetails = (id: string) => {
  return makeServiceCall(`/users/${id}`, 'GET', {})
}

export const updateEmployee = (data: Employee) => {
  return makeServiceCall(`/users/${data.id}`, 'PUT', { body: data })
}

export const postEmployee = (data: Employee) => {
  return makeServiceCall(`/users`, 'POST', { body: data })
}

export const deleteEmployee = (id: string | number) => {
  return makeServiceCall(`/users/${id}`, 'DELETE', {})
}

export const getAboutMeInfo = () => {
  return makeServiceCall(`/aboutme`, 'GET', {})
}