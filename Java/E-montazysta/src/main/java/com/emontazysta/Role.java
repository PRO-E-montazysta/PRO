package com.emontazysta;

public enum Role {
    CLOUD_ADMIN("CLOUD_ADMIN"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER");

    private String nazwa;

    Role(String nazwa) {
        this.nazwa = nazwa;
    }

   public String getValue() {return nazwa;}
}
