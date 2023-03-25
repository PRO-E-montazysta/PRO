import { Employee } from '../types/model/Employee'
import { makeServiceCall, PayloadProps } from './utils.api';

export const getAllEmployees = () => {
  return makeServiceCall('/employees/all', 'GET', {})
}

export const getFilteredEmployees  = (payload: PayloadProps) => {
  return makeServiceCall('/employees/filter', 'GET', { ...payload });
};

export const getEmployeeById  = (id:string) =>{
  return makeServiceCall(`/employees/${id}`, 'GET',{})
}

export const getEmployeeDetails = (id: string) => {
  return makeServiceCall(`/employees/${id}`, 'GET', {})
}
  
  export const updateEmployee = (data: Employee) => {
  return makeServiceCall(`/employees/${data.id}`, 'PUT', { body: data })
  }
  
  export const postEmployee = (data: Employee) => {
  return makeServiceCall(`/employees`, 'POST', { body: data })
  }
  
  export const deleteEmployee = (id: string | number) => {
  return makeServiceCall(`/employees/${id}`, 'DELETE', {})
  }
  