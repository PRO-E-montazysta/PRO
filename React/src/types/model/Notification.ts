export type Notification = {
    id: number
    notificationType: string
    content: string
    subContent: string
    createdAt: string
    readAt: number
    createdById: number
    deleted: boolean

    orderStageId: number
    orderId: number
    toolEventId: number
    elementEventId: number
}

export const NotificationType_SPECIALIST = {
    UNAVAILABILITY_ASSIGNMENT: 'Przypisanie nieobecności',
    ORDER_CREATED: 'Utworzono nowe zlecenie',
    AD_HOC_CREATED: 'Zgłoszenie zapotrzebowania AD-HOC',
}

export const NotificationType_MANAGER = {
    UNAVAILABILITY_ASSIGNMENT: 'Przypisanie nieobecności',
    ACCEPT_ORDER: 'Zlecenie oczekuje na zaakceptowanie',
    AD_HOC_CREATED: 'Zgłoszenie zapotrzebowania AD-HOC',
    TOOL_EVENT: 'Zgłoszenie usterki narzędzia',
    ELEMENT_EVENT: 'Zgłoszenie usterki elementu',
}

export const NotificationType_WAREHOUSEMANAGER = {
    UNAVAILABILITY_ASSIGNMENT: 'Przypisanie nieobecności',
    AD_HOC_CREATED: 'Zgłoszenie zapotrzebowania AD-HOC',
    TOOL_EVENT: 'Zgłoszenie usterki narzędzia',
    ELEMENT_EVENT: 'Zgłoszenie usterki elementu',
}

export const NotificationType_FOREMAN = {
    UNAVAILABILITY_ASSIGNMENT: 'Przypisanie nieobecności',
    FOREMAN_ASSIGNMENT: 'Przypisanie do zlecenia',
}

export const NotificationType_FITTER = {
    UNAVAILABILITY_ASSIGNMENT: 'Przypisanie nieobecności',
    FITTER_ASSIGNMENT: 'Przypisnie do etapu',
}

export const NotificationType_OTHERS = {
    UNAVAILABILITY_ASSIGNMENT: 'Przypisanie nieobecności',
}
