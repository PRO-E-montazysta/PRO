//Tutaj powinniśmy wrzucać Typy danych, jakie dostajemy z Backendu. Tutaj np. dla pracowników

export type Employee = {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  username: string;
  status: string;
  roles: string[];
  phone: string;
  unavailbilityDescription: string;
  pesel: string;
};

export const EmployeeStatus = {
  AVAILABLE: "AKTYWNY",
  NIEAKTYWNY: "NIEAKTYWNY",
}

export const UserRole = {
  MANAGER: "MANAGER",
  SALES_REPRESENTATIVE: "SALES_REPRESENTATIVE",
  SPECIALIST: " SPECIALIST",
  WAREHOUSE_MAN: "WAREHOUSE_MAN",
  WAREHOUSE_MANAGER: "WAREHOUSE_MANAGER",
  FITTER: "FITTER",
  FOREMAN: "FOREMAN"
}