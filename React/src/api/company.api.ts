import { Company } from "../types/model/Company";
import { makeServiceCall, PayloadProps } from "./utils.api";

export const getAllCompanies = () => {
    return makeServiceCall('/companies/all', 'GET', {});
}

export const getFilteredCompanies = (payload: PayloadProps) => {
    return makeServiceCall('/companies/filter', 'GET', { ...payload });
}

export const getCompanyDetails = (id: string) => {
    return makeServiceCall(`/companies/${id}`, 'GET', {});
}

export const updateCompany = (data: Company) => {
    return makeServiceCall(`/companies/${data.id}`, 'PUT', { body: data });
}


export const postCompany = (data: Company) => {
    return makeServiceCall(`/companies`, 'POST', { body: data });
}

export const deleteCompany = (id: string | number) => {
    return makeServiceCall(`/companies/${id}`, 'DELETE', {});
}