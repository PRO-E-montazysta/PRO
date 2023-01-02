package com.emontazysta;

public enum Role {
    CLOUD_ADMIN("CLOUD_ADMIN"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    FITTER("FITTER"),
    FOREMAN("FOREMAN"),
    SPECIALIST("SPECIALIST"),
    WAREHAUSE_MAN("WAREHAUSE_MAN"),
    WAREHOUSE_MANAGER("WAREHOUSE_MANAGER");

    private final String  nazwa;

    Role(String nazwa) {
        this.nazwa = nazwa;
    }

   public String getValue() {return nazwa;}
}
