//Tutaj powinniśmy wrzucać Typy danych, jakie dostajemy z Backendu. Tutaj np. dla pracowników

export type Employee = {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  status: string;
  roles: string[];
  phone: string;
  unavailbilityDescription:string;
};

export const EmployeeStatus = {
  AKTYWNY: "AKTYWNY",
  NIEAKTYWNY: "NIEAKTYWNY",
}

export const RoleStatus = {
  MANAGER: "MANAGER",
  SALES_REPRESENTATIVE: "SALES_REPRESENTATIVE",
  SPECIALIST: " SPECIALIST",
  WAREHOUSE_MAN: "WAREHOUSE_MAN",
  WAREHOUSE_MANAGER: "WAREHOUSE_MANAGER",
  FITTER: "FITTER",
  FOREMAN: "FOREMAN"
}