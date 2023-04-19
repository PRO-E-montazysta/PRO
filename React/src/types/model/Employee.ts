export type Employee = {
    id: string
    firstName: string
    lastName: string
    email: string
    password: string
    username: string
    status: string
    roles: string[]
    phone: string
    unavailbilityDescription: string
    pesel: string
}

export const EmployeeStatus = {
    AVAILABLE: 'AKTYWNY',
    NIEAKTYWNY: 'NIEAKTYWNY',
}

export const UserRole = {
    ADMIN: 'Administrator',
    MANAGER: 'Manager',
    SALES_REPRESENTATIVE: 'Handlowiec',
    SPECIALIST: ' Specjalista',
    WAREHOUSE_MAN: 'Magazynier',
    WAREHOUSE_MANAGER: 'Kierownik magazynu',
    FITTER: 'Montażysta',
    FOREMAN: 'Brygadzista',
}
