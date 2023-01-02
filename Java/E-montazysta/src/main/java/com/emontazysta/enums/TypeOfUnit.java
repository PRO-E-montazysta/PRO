package com.emontazysta.enums;

public enum TypeOfUnit {
    KILOGRAM("KILOGRAM"),
    LITER("LITER");

    private String name;

    TypeOfUnit(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
