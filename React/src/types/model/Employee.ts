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
