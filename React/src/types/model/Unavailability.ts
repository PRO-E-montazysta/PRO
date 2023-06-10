export type Unavailability = {
    id: number
    typeOfUnavailability: string
    description: string
    unavailableFrom: Date
    unavailableTo: Date
    assignedToId: number
    assignedById: number
    deleted: boolean
}
export type UnavailabilityFilter = {
    id: number
    typeOfUnavailability: string
    unavailableFrom: Date
    unavailableTo: Date
    assignedTo: string
}

export const TypeOfUnavailability = {
    HOLIDAY: 'Urlop wypoczynkowy',
    SICK_LEAVE: 'Urlop chorobowy',
    BEREAVEMENT_LEAVE: 'Urlop okolicznościowy',
    OTHER: 'Inny',
}
