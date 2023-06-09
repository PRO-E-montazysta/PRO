export type Unavailability = {
    id: number
    typeOfUnavailability: string
    description: string
    unavailableFrom: Date
    unavailableTo: Date
    assignedToId: number
    assignedById: number

    apartmentNumber: string
    assignedByFirstName: string
    assignedByLastName: string
    assignedFittersNumber: number
    assignedToFirstName: string
    assignedToLastName: string
    city: string
    companyId: number
    companyName: string
    locationId: number
    orderId: number
    orderStageId: number
    orderStageName: string
    plannedFittersNumber: number
    propertyNumber: string
    street: string
    zipCode: string
}
export type UnavailabilityFilter = {
    id: number
    typeOfUnavailability: string
    unavailableFrom: Date
    unavailableTo: Date
    assignedTo: string
}

export const TypeOfUnavailability = {
    BUSY: 'Zajęty',
    HOLIDAY: 'Urlop wypoczynkowy',
    SICK_LEAVE: 'Urlop chorobowy',
    BEREAVEMENT_LEAVE: 'Urlop okolicznościowy',
    OTHER: 'Inny',
}
