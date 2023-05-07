package com.emontazysta.enums;

public enum NotificationType {

    ORDER_CREATED("Zostało utworzone nowe zlecenie"),
    STAGE_ADD("Dodano nowy etap do zlecenia"),
    STAGE_MODIFIED("Zmodyfikowano jeden z etapów zlecenia"),
    FITTER_ASSIGNMENT("Zostałeś przypisany do etapu zlecenia"),
    FOREMAN_ASSIGNMENT("Zostałeś przypisany do zlecenia"),
    AD_HOC_NOTIFICATION("Złożono zapotrzebowanie ad-hoc");

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
