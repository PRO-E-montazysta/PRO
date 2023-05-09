package com.emontazysta.enums;

public enum NotificationType {

    ORDER_CREATED("Utworzono nowe zlecenie"), //Powiadamia specjalistów, że handlowiec utworzył nowe zlecenie
    ACCEPT_ORDER("Zlecenie oczekuje na zaakceptowanie"), //Powiadamia menagera, że specjalista wypełnił zlecenie i należy przypisać brygadziste
    FOREMAN_ASSIGNMENT("Zostałeś przypisany do zlecenia"), //Powiadamia brygadzistę, że menager przypisał go do zlecenia
    FITTER_ASSIGNMENT("Zostałeś przypisany do etapu zlecenia"), //Powiadamia montażyste, że brygadzsista przypisał go do etapu
    AD_HOC_CREATED("Utworzono zapotrzebowanie ad-hoc");

    private String message;

    NotificationType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
