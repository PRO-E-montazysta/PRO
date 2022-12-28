package com.emontazysta.enums;

public enum TypeOfUnavailability {
    TYPE1("TYPE1"),
    TYPE2("TYPE2");



    private String name;

    TypeOfUnavailability(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
